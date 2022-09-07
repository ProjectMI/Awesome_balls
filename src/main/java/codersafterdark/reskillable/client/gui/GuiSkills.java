//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui;

import org.lwjgl.input.Mouse;
import java.io.IOException;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiButton;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import codersafterdark.reskillable.client.gui.handler.KeyBindings;
import org.apache.commons.lang3.tuple.Pair;
import codersafterdark.reskillable.client.base.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import java.util.function.Consumer;
import java.util.function.Predicate;
import codersafterdark.reskillable.api.ReskillableRegistries;
import java.util.ArrayList;
import java.util.List;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class GuiSkills extends GuiScreen
{
    public static final ResourceLocation SKILLS_RES;
    private int guiWidth;
    private int guiHeight;
    private Skill hoveredSkill;
    private int offset;
    private int left;
    private int top;
    private int lastY;
    private List<Skill> enabledSkills;
    private List<Skill> skills;
    
    public GuiSkills() {
        this.enabledSkills = new ArrayList<Skill>();
        this.skills = new ArrayList<Skill>();
        ReskillableRegistries.SKILLS.getValuesCollection().stream().filter(Skill::isEnabled).forEach(this.enabledSkills::add);
    }
    
    public static void drawSkill(final int x, final int y, final Skill skill) {
        final Minecraft mc = Minecraft.getMinecraft();
        final int rank = PlayerDataHandler.get((EntityPlayer)mc.player).getSkillInfo(skill).getRank();
        if (skill.hasCustomSprites()) {
            final ResourceLocation sprite = skill.getSpriteLocation(rank);
            if (sprite != null) {
                mc.renderEngine.bindTexture(sprite);
                drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
            }
        }
        else {
            mc.renderEngine.bindTexture(skill.getSpriteLocation());
            final Pair<Integer, Integer> pair = skill.getSpriteFromRank(rank);
            RenderHelper.drawTexturedModalRect(x, y, 1.0f, (int)pair.getKey(), (int)pair.getValue(), 16, 16, 0.015625f, 0.015625f);
        }
    }
    
    public static void drawScrollButtonsTop(final int x, final int y) {
        Minecraft.getMinecraft().renderEngine.bindTexture(GuiSkills.SKILLS_RES);
        RenderHelper.drawTexturedModalRect(x, y, 1.0f, 0, 230, 80, 4);
    }
    
    public static void drawScrollButtonsBottom(final int x, final int y) {
        Minecraft.getMinecraft().renderEngine.bindTexture(GuiSkills.SKILLS_RES);
        RenderHelper.drawTexturedModalRect(x, y, 1.0f, 0, 235, 80, 4);
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
        this.buttonList.clear();
        InventoryTabHandler.addTabs(this, this.buttonList);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.mc.renderEngine.bindTexture(GuiSkills.SKILLS_RES);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.left = this.width / 2 - this.guiWidth / 2;
        this.top = this.height / 2 - this.guiHeight / 2;
        this.drawTexturedModalRect(this.left, this.top, 0, 0, this.guiWidth, this.guiHeight);
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)this.mc.player);
        this.hoveredSkill = null;
        this.skills = new ArrayList<Skill>();
        this.enabledSkills.stream().filter(enabledSkill -> !enabledSkill.isHidden()).forEach(this.skills::add);
        for (int index = 0, j = this.offset; j < this.skills.size() && index < 8; ++j) {
            final Skill skill = this.skills.get(j);
            final PlayerSkillInfo skillInfo = data.getSkillInfo(skill);
            final int i = index++;
            final int w = 79;
            final int h = 32;
            final int x = this.left + i % 2 * (w + 3) + 8;
            final int y = this.top + i / 2 * (h + 3) + 18;
            this.lastY = y;
            int u = 0;
            int v = this.guiHeight;
            if (mouseX >= x && mouseY >= y && mouseX < x + w && mouseY < y + h) {
                u += w;
                this.hoveredSkill = skill;
            }
            if (skillInfo.isCapped()) {
                v += h;
            }
            this.mc.renderEngine.bindTexture(GuiSkills.SKILLS_RES);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y, u, v, w, h);
            drawSkill(x + 5, y + 9, skill);
            this.mc.fontRenderer.drawString(skill.getName(), x + 26, y + 6, 16777215);
            this.mc.fontRenderer.drawString(skillInfo.getLevel() + "/" + skill.getCap(), x + 26, y + 17, 8947848);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        drawScrollButtonsTop(this.left + 49, this.top + 14);
        drawScrollButtonsBottom(this.left + 49, this.lastY + 32);
        final String skillsStr = new TextComponentTranslation("reskillable.misc.skills", new Object[0]).getUnformattedComponentText();
        this.fontRenderer.drawString(skillsStr, this.width / 2 - this.fontRenderer.getStringWidth(skillsStr) / 2, this.top + 5, 4210752);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && this.hoveredSkill != null) {
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            final GuiSkillInfo gui = new GuiSkillInfo(this.hoveredSkill);
            this.mc.displayGuiScreen((GuiScreen)gui);
        }
        if (mouseButton == 0 && mouseX >= this.left + 49 && mouseX <= this.left + 128 && mouseY >= this.top + 14) {
            if (mouseY <= this.top + 18) {
                this.scrollUp();
            }
            else if (mouseY <= this.lastY + 36) {
                this.scrollDown();
            }
        }
    }
    
    private void scrollUp() {
        this.offset = Math.max(this.offset - 2, 0);
    }
    
    private void scrollDown() {
        int off = 2;
        if (this.skills.size() % 2 == 1) {
            off = 1;
        }
        this.offset = Math.min(this.offset + 2, Math.max(this.skills.size() - 6 - off, 0));
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (Mouse.getEventDWheel() > 0) {
            this.scrollUp();
        }
        else if (Mouse.getEventDWheel() < 0) {
            this.scrollDown();
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        SKILLS_RES = new ResourceLocation("reskillable", "textures/gui/skills.png");
    }
}
