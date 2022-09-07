//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.base;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHelper
{
    public static void renderTooltip(final int x, final int y, final List<String> tooltipData) {
        final int color = 1347420415;
        final int color2 = -267386864;
        renderTooltip(x, y, tooltipData, color, color2);
    }
    
    public static void renderTooltip(final int x, final int y, final List<String> tooltipData, final int color, final int color2) {
        final boolean lighting = GL11.glGetBoolean(2896);
        if (lighting) {
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        }
        if (!tooltipData.isEmpty()) {
            int var5 = 0;
            final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            for (int var6 = 0; var6 < tooltipData.size(); ++var6) {
                final int var7 = fontRenderer.getStringWidth((String)tooltipData.get(var6));
                if (var7 > var5) {
                    var5 = var7;
                }
            }
            int var6 = x + 12;
            int var7 = y - 12;
            int var8 = 8;
            if (tooltipData.size() > 1) {
                var8 += 2 + (tooltipData.size() - 1) * 10;
            }
            final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            final int right = var6 + var5 + 5;
            final int swidth = res.getScaledWidth();
            if (right > swidth) {
                var6 -= right - swidth;
            }
            final int bottom = var7 + var8 + 5;
            final int sheight = res.getScaledHeight();
            if (bottom > sheight) {
                var7 -= bottom - sheight;
            }
            final float z = 300.0f;
            drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
            drawGradientRect(var6 - 3, var7 + var8 + 3, z, var6 + var5 + 3, var7 + var8 + 4, color2, color2);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var8 + 3, color2, color2);
            drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var8 + 3, color2, color2);
            drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var8 + 3, color2, color2);
            final int var9 = (color & 0xFFFFFF) >> 1 | (color & 0xFF000000);
            drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var8 + 3 - 1, color, var9);
            drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var8 + 3 - 1, color, var9);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
            drawGradientRect(var6 - 3, var7 + var8 + 2, z, var6 + var5 + 3, var7 + var8 + 3, var9, var9);
            GlStateManager.disableDepth();
            for (int var10 = 0; var10 < tooltipData.size(); ++var10) {
                final String var11 = tooltipData.get(var10);
                fontRenderer.drawStringWithShadow(var11, (float)var6, (float)var7, -1);
                if (var10 == 0) {
                    var7 += 2;
                }
                var7 += 10;
            }
            GlStateManager.enableDepth();
        }
        if (!lighting) {
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawGradientRect(final int par1, final int par2, final float z, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = (par5 >> 24 & 0xFF) / 255.0f;
        final float var8 = (par5 >> 16 & 0xFF) / 255.0f;
        final float var9 = (par5 >> 8 & 0xFF) / 255.0f;
        final float var10 = (par5 & 0xFF) / 255.0f;
        final float var11 = (par6 >> 24 & 0xFF) / 255.0f;
        final float var12 = (par6 >> 16 & 0xFF) / 255.0f;
        final float var13 = (par6 >> 8 & 0xFF) / 255.0f;
        final float var14 = (par6 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.shadeModel(7425);
        final Tessellator var15 = Tessellator.getInstance();
        final BufferBuilder buff = var15.getBuffer();
        buff.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buff.pos((double)par3, (double)par2, (double)z).color(var8, var9, var10, var7).endVertex();
        buff.pos((double)par1, (double)par2, (double)z).color(var8, var9, var10, var7).endVertex();
        buff.pos((double)par1, (double)par4, (double)z).color(var12, var13, var14, var11).endVertex();
        buff.pos((double)par3, (double)par4, (double)z).color(var12, var13, var14, var11).endVertex();
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawTexturedModalRect(final int par1, final int par2, final float z, final int par3, final int par4, final int par5, final int par6) {
        drawTexturedModalRect(par1, par2, z, par3, par4, par5, par6, 0.00390625f, 0.00390625f);
    }
    
    public static void drawTexturedModalRect(final int par1, final int par2, final float z, final int par3, final int par4, final int par5, final int par6, final float f, final float f1) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buff = tessellator.getBuffer();
        buff.begin(7, DefaultVertexFormats.POSITION_TEX);
        buff.pos((double)par1, (double)(par2 + par6), (double)z).tex((double)(par3 * f), (double)((par4 + par6) * f1)).endVertex();
        buff.pos((double)(par1 + par5), (double)(par2 + par6), (double)z).tex((double)((par3 + par5) * f), (double)((par4 + par6) * f1)).endVertex();
        buff.pos((double)(par1 + par5), (double)par2, (double)z).tex((double)((par3 + par5) * f), (double)(par4 * f1)).endVertex();
        buff.pos((double)par1, (double)par2, (double)z).tex((double)(par3 * f), (double)(par4 * f1)).endVertex();
        tessellator.draw();
    }
}
