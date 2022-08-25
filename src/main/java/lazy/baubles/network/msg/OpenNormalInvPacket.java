package lazy.baubles.network.msg;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class OpenNormalInvPacket {
    public OpenNormalInvPacket(FriendlyByteBuf buf) {
    }

    public OpenNormalInvPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer playerEntity = ((NetworkEvent.Context)ctx.get()).getSender();
            if (playerEntity != null) {
                playerEntity.f_36096_.m_6877_(playerEntity);
                playerEntity.f_36096_ = playerEntity.f_36095_;
            }

        });
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
