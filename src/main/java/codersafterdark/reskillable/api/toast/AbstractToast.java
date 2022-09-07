//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.toast;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.toasts.IToast;

@SideOnly(Side.CLIENT)
public abstract class AbstractToast implements IToast
{
    private final String title;
    private String description;
    private long firstDrawTime;
    private boolean drawn;
    protected int x;
    protected int y;
    protected long displayTime;
    
    public AbstractToast(final String title, final String description) {
        this.x = 8;
        this.y = 8;
        this.displayTime = 5000L;
        this.title = title;
        this.description = description;
    }
    
    @Nonnull
    public IToast.Visibility draw(@Nonnull final GuiToast guiToast, final long l) {
        if (!this.drawn) {
            this.drawn = true;
            this.firstDrawTime = l;
        }
        final Minecraft mc = guiToast.getMinecraft();
        mc.getTextureManager().bindTexture(AbstractToast.TEXTURE_TOASTS);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        final boolean hasImage = this.hasImage();
        final int xDif = hasImage ? 0 : this.x;
        guiToast.drawTexturedModalRect(0, 0, 0, 32, 160, 32);
        mc.fontRenderer.drawString(this.getTitle(), 30 - xDif, 7, -11534256);
        mc.fontRenderer.drawString(this.getDescription(), 30 - xDif, 18, -16777216);
        if (hasImage) {
            this.renderImage(guiToast);
        }
        return (l - this.firstDrawTime >= this.displayTime) ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }
    
    protected void bindImage(final GuiToast guiToast, final ResourceLocation sprite) {
        guiToast.getMinecraft().renderEngine.bindTexture(sprite);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    protected abstract void renderImage(final GuiToast p0);
    
    protected abstract boolean hasImage();
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
}
