//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.client.base.HUDHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageLockedItem implements IMessage, IMessageHandler<MessageLockedItem, IMessage>
{
    public static final String MSG_ITEM_LOCKED = "reskillable.misc.locked.item";
    public static final String MSG_BLOCK_BREAK_LOCKED = "reskillable.misc.locked.block_break";
    public static final String MSG_BLOCK_USE_LOCKED = "reskillable.misc.locked.block_use";
    public static final String MSG_ARMOR_EQUIP_LOCKED = "reskillable.misc.locked.armor_equip";
    public ItemStack stack;
    public String msg;
    
    public MessageLockedItem() {
    }
    
    public MessageLockedItem(final ItemStack stack, final String msg) {
        this.stack = stack;
        this.msg = msg;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.msg = ByteBufUtils.readUTF8String(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.stack);
        ByteBufUtils.writeUTF8String(buf, this.msg);
    }
    
    public IMessage onMessage(final MessageLockedItem message, final MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message, ctx));
        return null;
    }
    
    public IMessage handleMessage(final MessageLockedItem message, final MessageContext ctx) {
        HUDHandler.setLockMessage(message.stack, message.msg);
        return null;
    }
}
