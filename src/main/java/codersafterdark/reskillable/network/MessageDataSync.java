//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.Reskillable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageDataSync implements IMessage, IMessageHandler<MessageDataSync, IMessage>
{
    public NBTTagCompound cmp;
    
    public MessageDataSync() {
    }
    
    public MessageDataSync(final PlayerData data) {
        data.saveToNBT(this.cmp = new NBTTagCompound());
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.cmp = ByteBufUtils.readTag(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.cmp);
    }
    
    public IMessage onMessage(final MessageDataSync message, final MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message));
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleMessage(final MessageDataSync message) {
        final PlayerData data = PlayerDataHandler.get(Reskillable.proxy.getClientPlayer());
        data.loadFromNBT(message.cmp);
    }
}
