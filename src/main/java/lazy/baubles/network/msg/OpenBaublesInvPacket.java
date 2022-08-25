package lazy.baubles.network.msg;

import java.util.function.Supplier;
import lazy.baubles.client.util.GuiProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

public class OpenBaublesInvPacket {
    public OpenBaublesInvPacket(FriendlyByteBuf buf) {
    }

    public OpenBaublesInvPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer playerEntity = ((NetworkEvent.Context)ctx.get()).getSender();
            if (playerEntity != null) {
                playerEntity.m_6915_();
                NetworkHooks.openGui(playerEntity, new GuiProvider());
            }

        });
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
