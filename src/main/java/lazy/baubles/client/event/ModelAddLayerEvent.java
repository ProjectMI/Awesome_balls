package lazy.baubles.client.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import lazy.baubles.client.renderer.BaublesRenderLayer;
import com.google.common.base.Preconditions;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "baubles", value = { Dist.CLIENT })
public class ModelAddLayerEvent
{
    @SubscribeEvent
    public static void onAddLayer(final EntityRenderersEvent.AddLayers event) {
        final PlayerRenderer defaultModel = (PlayerRenderer)event.getSkin("default");
        final PlayerRenderer slimModel = (PlayerRenderer)event.getSkin("slim");
        Preconditions.checkNotNull((Object)defaultModel);
        Preconditions.checkNotNull((Object)slimModel);
        defaultModel.m_115326_((RenderLayer)new BaublesRenderLayer<Object, Object>((net.minecraft.client.renderer.entity.RenderLayerParent<?, ?>)defaultModel));
        slimModel.m_115326_((RenderLayer)new BaublesRenderLayer<Object, Object>((net.minecraft.client.renderer.entity.RenderLayerParent<?, ?>)slimModel));
    }
}
