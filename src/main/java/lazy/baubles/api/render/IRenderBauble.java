package lazy.baubles.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public interface IRenderBauble extends IBauble {
    void onPlayerBaubleRender(PoseStack var1, Player var2, IRenderBauble.RenderType var3, float var4);

    public static enum RenderType {
        BODY,
        HEAD;

        // $FF: synthetic method
        private static IRenderBauble.RenderType[] $values() {
            return new IRenderBauble.RenderType[]{BODY, HEAD};
        }
    }

    public static final class Helper {
        public static void rotateIfSneaking(PoseStack stack, Player player) {
            if (player.m_6047_()) {
                applySneakingRotation(stack);
            }

        }

        public static void applySneakingRotation(PoseStack stack) {
            stack.m_85837_(0.0D, 0.20000000298023224D, 0.0D);
            stack.m_85845_(Vector3f.f_122223_.m_122240_(28.647888F));
        }

        public static void translateToHeadLevel(PoseStack stack, Player player) {
            stack.m_85837_(0.0D, (double)(-player.m_20192_()), 0.0D);
            if (player.m_6047_()) {
                stack.m_85837_((double)(0.25F * Mth.m_14031_(player.f_20885_ * 3.1415927F / 180.0F)), (double)(0.25F * Mth.m_14089_(player.f_20885_ * 3.1415927F / 180.0F)), 0.0D);
            }

        }

        public static void translateToFace(PoseStack stack) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
            stack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
            stack.m_85837_(0.0D, -4.349999904632568D, -1.2699999809265137D);
        }

        public static void defaultTransforms(PoseStack stack) {
            stack.m_85837_(0.0D, 3.0D, 1.0D);
            stack.m_85841_(0.55F, 0.55F, 0.55F);
        }

        public static void translateToChest(PoseStack stack) {
            stack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
            stack.m_85837_(0.0D, -3.200000047683716D, -0.8500000238418579D);
        }
    }
}