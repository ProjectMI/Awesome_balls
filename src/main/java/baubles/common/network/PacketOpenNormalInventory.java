//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.network;

import net.minecraft.util.IThreadListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOpenNormalInventory implements IMessage, IMessageHandler<PacketOpenNormalInventory, IMessage>
{
    public void toBytes(final ByteBuf buffer) {
    }
    
    public void fromBytes(final ByteBuf buffer) {
    }
    
    public IMessage onMessage(final PacketOpenNormalInventory message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().player.world;
        mainThread.addScheduledTask((Runnable)new Runnable() {
            @Override
            public void run() {
                ctx.getServerHandler().player.openContainer.onContainerClosed((EntityPlayer)ctx.getServerHandler().player);
                ctx.getServerHandler().player.openContainer = ctx.getServerHandler().player.inventoryContainer;
            }
        });
        return null;
    }
}
