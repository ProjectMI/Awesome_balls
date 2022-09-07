//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.base;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.Advancement;
import java.util.Iterator;
import java.util.List;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.client.gui.GuiSkills;
import net.minecraft.client.gui.Gui;
import codersafterdark.reskillable.api.requirement.AdvancementRequirement;
import codersafterdark.reskillable.api.requirement.SkillRequirement;
import codersafterdark.reskillable.api.requirement.Requirement;
import com.google.common.collect.Lists;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HUDHandler
{
    private static ItemStack lockedItem;
    private static String lockMessage;
    private static int lockTime;
    
    public static void setLockMessage(final ItemStack item, final String message) {
        HUDHandler.lockedItem = item;
        HUDHandler.lockMessage = message;
        HUDHandler.lockTime = 40;
    }
    
    @SubscribeEvent
    public static void renderHUD(final RenderGameOverlayEvent event) {
        if (HUDHandler.lockTime > 0 && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            float transparency = 1.0f;
            if (HUDHandler.lockTime < 10) {
                transparency = Math.max(0.05f, (HUDHandler.lockTime - ClientTickHandler.partialTicks) / 10.0f);
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final ScaledResolution res = event.getResolution();
            final int width = res.getScaledWidth();
            final int height = res.getScaledHeight();
            int y = height / 2 - 80;
            if (HUDHandler.lockMessage.equals("reskillable.misc.locked.armor_equip")) {
                y -= 30;
            }
            final int transparencyInt = (int)(255.0f * transparency) << 24;
            int color = (int)(17.0f * transparency) << 24;
            final String msg = new TextComponentTranslation(HUDHandler.lockMessage, new Object[0]).getUnformattedComponentText();
            final int len = mc.fontRenderer.getStringWidth(msg);
            final PlayerData data = PlayerDataHandler.get((EntityPlayer)mc.player);
            final RequirementHolder requirements = LevelLockHandler.getSkillLock(HUDHandler.lockedItem);
            final int pad = 26;
            int xp = width / 2 - requirements.getRestrictionLength() * pad / 2;
            final List<SkillRequirement> skillRequirements = (List<SkillRequirement>)Lists.newArrayList();
            final List<AdvancementRequirement> advancementRequirements = (List<AdvancementRequirement>)Lists.newArrayList();
            for (final Requirement requirement : requirements.getRequirements()) {
                if (requirement instanceof SkillRequirement) {
                    skillRequirements.add((SkillRequirement)requirement);
                }
                else {
                    if (!(requirement instanceof AdvancementRequirement)) {
                        continue;
                    }
                    advancementRequirements.add((AdvancementRequirement)requirement);
                }
            }
            final int boxWidth = Math.max(pad * requirements.getRestrictionLength(), len) + 20;
            final int boxHeight = 52 + advancementRequirements.size() * 10;
            Gui.drawRect(width / 2 - boxWidth / 2, y - 10, width / 2 + boxWidth / 2, y + boxHeight, color);
            Gui.drawRect(width / 2 - boxWidth / 2 - 2, y - 12, width / 2 + boxWidth / 2 + 2, y + boxHeight + 2, color);
            GlStateManager.enableBlend();
            color = 16726336 + transparencyInt;
            mc.fontRenderer.drawStringWithShadow(msg, (float)(res.getScaledWidth() / 2 - len / 2), (float)y, color);
            for (final SkillRequirement skillRequirement : skillRequirements) {
                final int reqLevel = skillRequirement.getLevel();
                GlStateManager.color(1.0f, 1.0f, 1.0f, transparency);
                GuiSkills.drawSkill(xp, y + 18, skillRequirement.getSkill());
                final int level = data.getSkillInfo(skillRequirement.getSkill()).getLevel();
                color = ((level < reqLevel) ? 16726336 : 3800973) + transparencyInt;
                mc.fontRenderer.drawStringWithShadow(Integer.toString(reqLevel), (float)(xp + 8), (float)(y + 32), color);
                xp += pad;
            }
            final int textLeft = xp;
            int textY = y + 48;
            color = 16777215 + transparencyInt;
            for (final AdvancementRequirement advancementRequirement : advancementRequirements) {
                final Advancement adv = advancementRequirement.getAdvancement();
                if (adv == null) {
                    continue;
                }
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/advancements/widgets.png"));
                final DisplayInfo display = adv.getDisplay();
                final int u = display.getFrame().getIcon();
                final int v = 154;
                GlStateManager.color(1.0f, 1.0f, 1.0f, transparency);
                RenderHelper.drawTexturedModalRect(xp - 3, y + 17, 0.0f, u, v, 26, 26);
                GlStateManager.disableLighting();
                GlStateManager.enableCull();
                net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(xp + 2), (float)(y + 22), 0.0f);
                if (transparency > 0.5) {
                    mc.getRenderItem().renderItemAndEffectIntoGUI(display.getIcon(), 0, 0);
                }
                GlStateManager.popMatrix();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.disableLighting();
                mc.fontRenderer.drawStringWithShadow(adv.getDisplayText().getUnformattedText(), (float)textLeft, (float)textY, color);
                xp += pad;
                textY += 11;
            }
            GlStateManager.popMatrix();
        }
    }
    
    public static void tick() {
        if (HUDHandler.lockTime > 0) {
            --HUDHandler.lockTime;
        }
    }
}
