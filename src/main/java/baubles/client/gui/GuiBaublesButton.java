//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.renderer.GlStateManager;
import baubles.common.network.PacketOpenNormalInventory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.PacketHandler;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiButton;

public class GuiBaublesButton extends GuiButton
{
    private final GuiContainer parentGui;
    
    public GuiBaublesButton(final int buttonId, final GuiContainer parentGui, final int x, final int y, final int width, final int height, final String buttonText) {
        super(buttonId, x, parentGui.getGuiTop() + y, width, height, buttonText);
        this.parentGui = parentGui;
    }
    
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        final boolean pressed = super.mousePressed(mc, mouseX - this.parentGui.getGuiLeft(), mouseY);
        if (pressed) {
            if (this.parentGui instanceof GuiInventory) {
                PacketHandler.INSTANCE.sendToServer((IMessage)new PacketOpenBaublesInventory());
            }
            else {
                ((GuiPlayerExpanded)this.parentGui).displayNormalInventory();
                PacketHandler.INSTANCE.sendToServer((IMessage)new PacketOpenNormalInventory());
            }
        }
        return pressed;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.visible) {
            final int x = this.x + this.parentGui.getGuiLeft();
            final FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(GuiPlayerExpanded.background);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = (mouseX >= x && mouseY >= this.y && mouseX < x + this.width && mouseY < this.y + this.height);
            final int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.0f, 200.0f);
            if (k == 1) {
                this.drawTexturedModalRect(x, this.y, 200, 48, 10, 10);
            }
            else {
                this.drawTexturedModalRect(x, this.y, 210, 48, 10, 10);
                this.drawCenteredString(fontrenderer, I18n.format(this.displayString, new Object[0]), x + 5, this.y + this.height, 16777215);
            }
            GlStateManager.popMatrix();
            this.mouseDragged(mc, mouseX, mouseY);
        }
    }
}
