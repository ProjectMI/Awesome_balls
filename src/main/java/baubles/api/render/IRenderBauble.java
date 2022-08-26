//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.render;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IRenderBauble
{
    void onPlayerBaubleRender(final ItemStack p0, final EntityPlayer p1, final RenderType p2, final float p3);
    
    public static final class Helper
    {
        public static void rotateIfSneaking(final EntityPlayer player) {
            if (player.isSneaking()) {
                applySneakingRotation();
            }
        }
        
        public static void applySneakingRotation() {
            GlStateManager.translate(0.0f, 0.2f, 0.0f);
            GlStateManager.rotate(28.647888f, 1.0f, 0.0f, 0.0f);
        }
        
        public static void translateToHeadLevel(final EntityPlayer player) {
            GlStateManager.translate(0.0f, -player.getDefaultEyeHeight(), 0.0f);
            if (player.isSneaking()) {
                GlStateManager.translate(0.25f * MathHelper.sin(player.rotationPitch * 3.1415927f / 180.0f), 0.25f * MathHelper.cos(player.rotationPitch * 3.1415927f / 180.0f), 0.0f);
            }
        }
        
        public static void translateToFace() {
            GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -4.35f, -1.27f);
        }
        
        public static void defaultTransforms() {
            GlStateManager.translate(0.0, 3.0, 1.0);
            GlStateManager.scale(0.55, 0.55, 0.55);
        }
        
        public static void translateToChest() {
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(0.0f, -3.2f, -0.85f);
        }
    }
    
    public enum RenderType
    {
        BODY, 
        HEAD;
    }
}
