//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.renderer.GlStateManager;
import baubles.api.render.IRenderBauble;
import baubles.api.BaublesApi;
import net.minecraft.init.MobEffects;
import baubles.common.Config;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public final class BaublesRenderLayer implements LayerRenderer<EntityPlayer>
{
    public void doRenderLayer(@Nonnull final EntityPlayer player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!Config.renderBaubles || player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) {
            return;
        }
        final IBaublesItemHandler inv = BaublesApi.getBaublesHandler(player);
        this.dispatchRenders(inv, player, IRenderBauble.RenderType.BODY, partialTicks);
        final float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
        final float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        final float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(yawOffset, 0.0f, -1.0f, 0.0f);
        GlStateManager.rotate(yaw - 270.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(pitch, 0.0f, 0.0f, 1.0f);
        this.dispatchRenders(inv, player, IRenderBauble.RenderType.HEAD, partialTicks);
        GlStateManager.popMatrix();
    }
    
    private void dispatchRenders(final IBaublesItemHandler inv, final EntityPlayer player, final IRenderBauble.RenderType type, final float partialTicks) {
        for (int i = 0; i < inv.getSlots(); ++i) {
            final ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && !stack.isEmpty()) {
                final Item item = stack.getItem();
                if (item instanceof IRenderBauble) {
                    GlStateManager.pushMatrix();
                    GL11.glColor3ub((byte)(-1), (byte)(-1), (byte)(-1));
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    ((IRenderBauble)stack.getItem()).onPlayerBaubleRender(stack, player, type, partialTicks);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
}
