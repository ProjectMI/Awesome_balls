//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.button;

import net.minecraft.client.gui.GuiButton;

public class GuiBestButton extends GuiButton implements IToolTipProvider
{
    public GuiBestButton(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
    }
    
    public ToolTip getToolTip(final int mouseX, final int mouseY) {
        return null;
    }
    
    public boolean isToolTipVisible() {
        return this.visible;
    }
    
    public boolean isMouseOver(final int mouseX, final int mouseY) {
        return this.isMouseOver(mouseX, mouseY);
    }
}
