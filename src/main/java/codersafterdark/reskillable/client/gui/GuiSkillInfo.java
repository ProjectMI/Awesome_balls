//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui;

import java.io.IOException;
import codersafterdark.reskillable.network.MessageUnlockUnlockable;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import codersafterdark.reskillable.network.PacketHandler;
import codersafterdark.reskillable.network.MessageLevelUp;
import codersafterdark.reskillable.client.base.RenderHelper;
import java.util.ArrayList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.gui.GuiButton;
import java.util.List;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import codersafterdark.reskillable.base.ConfigHandler;
import net.minecraft.client.Minecraft;
import codersafterdark.reskillable.client.gui.handler.KeyBindings;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.client.gui.button.GuiButtonLevelUp;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class GuiSkillInfo extends GuiScreen
{
    public static final ResourceLocation SKILL_INFO_RES;
    public static final ResourceLocation SKILL_INFO_RES2;
    private final Skill skill;
    private int guiWidth;
    private int guiHeight;
    private ResourceLocation sprite;
    private GuiButtonLevelUp levelUpButton;
    private Unlockable hoveredUnlockable;
    private boolean canPurchase;
    
    public GuiSkillInfo(final Skill skill) {
        this.skill = skill;
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
        else if (keyCode == KeyBindings.openGUI.getKeyCode() || keyCode == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen != null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public void initGui() {
        this.guiWidth = 176;
        this.guiHeight = 166;
        final int left = this.width / 2 - this.guiWidth / 2;
        final int top = this.height / 2 - this.guiHeight / 2;
        this.buttonList.clear();
        if (ConfigHandler.enableLevelUp && this.skill.hasLevelButton()) {
            this.buttonList.add(this.levelUpButton = new GuiButtonLevelUp(left + 147, top + 10));
        }
        InventoryTabHandler.addTabs(this, this.buttonList);
        this.sprite = this.skill.getBackground();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        final int left = this.width / 2 - this.guiWidth / 2;
        final int top = this.height / 2 - this.guiHeight / 2;
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)this.mc.player);
        final PlayerSkillInfo skillInfo = data.getSkillInfo(this.skill);
        this.mc.renderEngine.bindTexture(this.sprite);
        GlStateManager.color(0.5f, 0.5f, 0.5f);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 8; ++j) {
                this.drawTexturedRec(left + 16 + i * 16, top + 33 + j * 16, 16, 16);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        if (ConfigHandler.enableLevelUp && this.skill.hasLevelButton()) {
            this.mc.renderEngine.bindTexture(GuiSkillInfo.SKILL_INFO_RES);
        }
        else {
            this.mc.renderEngine.bindTexture(GuiSkillInfo.SKILL_INFO_RES2);
        }
        this.drawTexturedModalRect(left, top, 0, 0, this.guiWidth, this.guiHeight);
        GuiSkills.drawSkill(left + 4, top + 9, this.skill);
        final String levelStr = String.format("%d/%d [ %s ]", skillInfo.getLevel(), this.skill.getCap(), new TextComponentTranslation("reskillable.rank." + skillInfo.getRank(), new Object[0]).getUnformattedComponentText());
        this.mc.fontRenderer.drawString(TextFormatting.BOLD + this.skill.getName(), left + 22, top + 8, 4210752);
        this.mc.fontRenderer.drawString(levelStr, left + 22, top + 18, 4210752);
        this.mc.fontRenderer.drawString(new TextComponentTranslation("reskillable.misc.skill_points", new Object[] { skillInfo.getSkillPoints() }).getUnformattedComponentText(), left + 15, top + 154, 4210752);
        final int cost = skillInfo.getLevelUpCost();
        String costStr = Integer.toString(cost);
        if (skillInfo.isCapped()) {
            costStr = new TextComponentTranslation("reskillable.misc.capped", new Object[0]).getUnformattedComponentText();
        }
        if (ConfigHandler.enableLevelUp && this.skill.hasLevelButton()) {
            this.drawCenteredString(this.mc.fontRenderer, costStr, left + 138, top + 13, 11534082);
            this.levelUpButton.setCost(cost);
        }
        this.hoveredUnlockable = null;
        this.skill.getUnlockables().forEach(u -> this.drawUnlockable(data, skillInfo, u, mouseX, mouseY));
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.hoveredUnlockable != null) {
            this.makeUnlockableTooltip(data, skillInfo, mouseX, mouseY);
        }
    }
    
    public void drawTexturedRec(final int x, final int y, final int width, final int height) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), (double)this.zLevel).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, (double)this.zLevel).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos((double)x, (double)y, (double)this.zLevel).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }
    
    private void drawUnlockable(final PlayerData data, final PlayerSkillInfo info, final Unlockable unlockable, final int mx, final int my) {
        final int x = this.width / 2 - this.guiWidth / 2 + 20 + unlockable.getX() * 28;
        final int y = this.height / 2 - this.guiHeight / 2 + 37 + unlockable.getY() * 28;
        this.mc.renderEngine.bindTexture(GuiSkillInfo.SKILL_INFO_RES);
        final boolean unlocked = info.isUnlocked(unlockable);
        int u = 0;
        int v = this.guiHeight;
        if (unlockable.hasSpikes()) {
            u += 26;
        }
        if (unlocked) {
            v += 26;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(x, y, u, v, 26, 26);
        this.mc.renderEngine.bindTexture(unlockable.getIcon());
        drawModalRectWithCustomSizedTexture(x + 5, y + 5, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
        if (mx >= x && my >= y && mx < x + 26 && my < y + 26) {
            this.canPurchase = (!unlocked && info.getSkillPoints() >= unlockable.getCost());
            this.hoveredUnlockable = unlockable;
        }
    }
    
    private void makeUnlockableTooltip(final PlayerData data, final PlayerSkillInfo info, final int mouseX, final int mouseY) {
        final List<String> tooltip = new ArrayList<String>();
        final TextFormatting tf = this.hoveredUnlockable.hasSpikes() ? TextFormatting.AQUA : TextFormatting.YELLOW;
        tooltip.add(tf + this.hoveredUnlockable.getName());
        if (isShiftKeyDown()) {
            this.addLongStringToTooltip(tooltip, this.hoveredUnlockable.getDescription(), this.guiWidth);
        }
        else {
            tooltip.add(TextFormatting.GRAY + new TextComponentTranslation("reskillable.misc.shift", new Object[0]).getUnformattedComponentText());
            tooltip.add("");
        }
        if (!info.isUnlocked(this.hoveredUnlockable)) {
            this.hoveredUnlockable.getRequirements().addRequirementsToTooltip(data, tooltip);
        }
        else {
            tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("reskillable.misc.unlocked", new Object[0]).getUnformattedComponentText());
        }
        tooltip.add(TextFormatting.GRAY + new TextComponentTranslation("reskillable.misc.skill_points", new Object[] { this.hoveredUnlockable.getCost() }).getUnformattedComponentText());
        RenderHelper.renderTooltip(mouseX, mouseY, tooltip);
    }
    
    private void addLongStringToTooltip(final List<String> tooltip, final String longStr, final int maxLen) {
        final String[] tokens = longStr.split(" ");
        String curr = TextFormatting.GRAY.toString();
        int i = 0;
        while (i < tokens.length) {
            while (this.fontRenderer.getStringWidth(curr) < maxLen && i < tokens.length) {
                curr = curr + tokens[i] + ' ';
                ++i;
            }
            tooltip.add(curr);
            curr = TextFormatting.GRAY.toString();
        }
        tooltip.add(curr);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (ConfigHandler.enableLevelUp && this.skill.hasLevelButton() && button == this.levelUpButton) {
            final MessageLevelUp message = new MessageLevelUp(this.skill.getRegistryName());
            PacketHandler.INSTANCE.sendToServer((IMessage)message);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && this.hoveredUnlockable != null && this.canPurchase) {
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            final MessageUnlockUnlockable message = new MessageUnlockUnlockable(this.skill.getRegistryName(), this.hoveredUnlockable.getRegistryName());
            PacketHandler.INSTANCE.sendToServer((IMessage)message);
        }
        else if (mouseButton == 1 || mouseButton == 3) {
            this.mc.displayGuiScreen((GuiScreen)new GuiSkills());
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        SKILL_INFO_RES = new ResourceLocation("reskillable", "textures/gui/skill_info.png");
        SKILL_INFO_RES2 = new ResourceLocation("reskillable", "textures/gui/skill_info2.png");
    }
}
