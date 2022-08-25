package lazy.baubles.client.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import lazy.baubles.network.msg.OpenBaublesInvPacket;
import lazy.baubles.network.PacketHandler;
import net.minecraft.client.Minecraft;
import lazy.baubles.Baubles;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "baubles", value = { Dist.CLIENT })
public class ClientPlayerTick
{
    @SubscribeEvent
    public static void playerTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && Baubles.KEY_BAUBLES.m_90859_() && Minecraft.m_91087_().m_91302_()) {
            PacketHandler.INSTANCE.sendToServer((Object)new OpenBaublesInvPacket());
        }
    }
}
