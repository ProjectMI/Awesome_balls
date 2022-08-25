package lazy.baubles.network.msg;

import java.util.function.Supplier;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class SyncPacket {
    private final int playerId;
    private final byte slot;
    private final ItemStack bauble;

    public SyncPacket(FriendlyByteBuf buf) {
        this.playerId = buf.readInt();
        this.slot = buf.readByte();
        this.bauble = buf.m_130267_();
    }

    public SyncPacket(int playerId, byte slot, ItemStack bauble) {
        this.playerId = playerId;
        this.slot = slot;
        this.bauble = bauble;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.playerId);
        buf.writeByte(this.slot);
        buf.m_130055_(this.bauble);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ClientLevel world = Minecraft.m_91087_().f_91073_;
            if (world != null) {
                Entity p = world.m_6815_(this.playerId);
                if (p instanceof Player) {
                    p.getCapability(CapabilityBaubles.BAUBLES).ifPresent((b) -> {
                        b.setStackInSlot(this.slot, this.bauble);
                    });
                }

            }
        });
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}