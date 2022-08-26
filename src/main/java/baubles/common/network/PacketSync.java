//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.network;

import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import baubles.api.BaublesApi;
import baubles.common.Baubles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSync implements IMessage
{
    int playerId;
    byte slot;
    ItemStack bauble;
    
    public PacketSync() {
        this.slot = 0;
    }
    
    public PacketSync(final EntityPlayer p, final int slot, final ItemStack bauble) {
        this.slot = 0;
        this.slot = (byte)slot;
        this.bauble = bauble;
        this.playerId = p.getEntityId();
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.playerId);
        buffer.writeByte((int)this.slot);
        ByteBufUtils.writeItemStack(buffer, this.bauble);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.playerId = buffer.readInt();
        this.slot = buffer.readByte();
        this.bauble = ByteBufUtils.readItemStack(buffer);
    }
    
    public static class Handler implements IMessageHandler<PacketSync, IMessage>
    {
        public IMessage onMessage(final PacketSync message, final MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask((Runnable)new Runnable() {
                @Override
                public void run() {
                    final World world = Baubles.proxy.getClientWorld();
                    if (world == null) {
                        return;
                    }
                    final Entity p = world.getEntityByID(message.playerId);
                    if (p != null && p instanceof EntityPlayer) {
                        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler((EntityPlayer)p);
                        baubles.setStackInSlot((int)message.slot, message.bauble);
                    }
                }
            });
            return null;
        }
    }
}
