package lazy.baubles.client.renderer;

import com.mojang.math.Vector3f;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import lazy.baubles.api.render.IRenderBauble;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraft.world.effect.MobEffects;
import lazy.baubles.setup.ModConfigs;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.player.Player;

public class BaublesRenderLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M>
{
    public BaublesRenderLayer(final RenderLayerParent<T, M> entityRendererIn) {
        super((RenderLayerParent)entityRendererIn);
    }
    
    public void render(final PoseStack matrixStack, final MultiBufferSource iRenderTypeBuffer, final int i, final Player player, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!(boolean)ModConfigs.RENDER_BAUBLE.get() || player.m_21124_(MobEffects.f_19609_) != null) {
            return;
        }
        player.getCapability((Capability)CapabilityBaubles.BAUBLES).ifPresent(inv -> {
            this.dispatchRenders(matrixStack, inv, player, IRenderBauble.RenderType.BODY, partialTicks);
            final float yaw = player.f_20886_ + (player.f_20885_ - player.f_20886_) * partialTicks;
            final float yawOffset = player.f_20884_ + (player.f_20883_ - player.f_20884_) * partialTicks;
            final float xRot = (float)ObfuscationReflectionHelper.getPrivateValue((Class)Entity.class, (Object)player, "f_19858_");
            final float pitch = player.f_19860_ + (xRot - player.f_19860_) * partialTicks;
            matrixStack.m_85836_();
            matrixStack.m_85845_(Vector3f.f_122224_.m_122240_(yawOffset));
            matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(yaw - 270.0f));
            matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(pitch));
            this.dispatchRenders(matrixStack, inv, player, IRenderBauble.RenderType.HEAD, partialTicks);
            matrixStack.m_85849_();
        });
    }
    
    private void dispatchRenders(final PoseStack poseStack, final IBaublesItemHandler inv, final Player player, final IRenderBauble.RenderType type, final float partialTicks) {
        for (int i = 0; i < inv.getSlots(); ++i) {
            final ItemStack stack = inv.getStackInSlot(i);
            if (!stack.m_41619_()) {
                stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).ifPresent(bauble -> {
                    if (bauble instanceof IRenderBauble) {
                        final IRenderBauble rb = (IRenderBauble)bauble;
                        poseStack.m_85836_();
                        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                        rb.onPlayerBaubleRender(poseStack, player, type, partialTicks);
                        poseStack.m_85849_();
                    }
                });
            }
        }
    }
}
