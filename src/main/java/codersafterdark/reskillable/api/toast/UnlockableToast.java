//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.toast;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UnlockableToast extends AbstractToast
{
    private final Unlockable unlockable;
    
    public UnlockableToast(final Unlockable unlockable) {
        super(unlockable.getName(), new TextComponentTranslation("reskillable.toast.unlockable_desc", new Object[0]).getUnformattedComponentText());
        this.unlockable = unlockable;
    }
    
    @Override
    protected void renderImage(final GuiToast guiToast) {
        this.bindImage(guiToast, this.unlockable.getIcon());
        Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
    }
    
    @Override
    protected boolean hasImage() {
        return true;
    }
}
