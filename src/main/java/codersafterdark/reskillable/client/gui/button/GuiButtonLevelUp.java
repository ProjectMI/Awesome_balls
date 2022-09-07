//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.button;

import codersafterdark.reskillable.client.gui.GuiSkillInfo;
import net.minecraft.client.renderer.GlStateManager;
import codersafterdark.reskillable.base.ConfigHandler;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonLevelUp extends GuiButton
{
    int cost;
    float renderTicks;
    
    public GuiButtonLevelUp(final int x, final int y) {
        super(0, x, y, 14, 14, "");
        this.cost = Integer.MAX_VALUE;
    }
    
    public void setCost(final int cost) {
        this.cost = cost;
    }
    
    public void drawButton(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float f) {
        this.enabled = (mc.player.experienceLevel >= this.cost || mc.player.isCreative());
        if (ConfigHandler.enableLevelUp && this.enabled) {
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            mc.renderEngine.bindTexture(GuiSkillInfo.SKILL_INFO_RES);
            final int x = this.x;
            final int y = this.y;
            final int u = 176;
            int v = 0;
            final int w = this.width;
            final int h = this.height;
            if (mouseX > this.x && mouseY > this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
                v += h;
            }
            else {
                final float speedModifier = 4.0f;
                GlStateManager.color(1.0f, 1.0f, 1.0f, (float)(Math.sin(mc.player.ticksExisted / speedModifier) + 1.0) / 2.0f);
            }
            this.drawTexturedModalRect(x, y, u, v, w, h);
        }
    }
}
