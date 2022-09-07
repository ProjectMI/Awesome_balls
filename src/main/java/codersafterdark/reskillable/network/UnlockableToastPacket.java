//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.api.toast.ToastHelper;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class UnlockableToastPacket implements IMessage, IMessageHandler<UnlockableToastPacket, IMessage>
{
    private ResourceLocation unlockableName;
    
    public UnlockableToastPacket() {
    }
    
    public UnlockableToastPacket(final ResourceLocation unlockableName) {
        this.unlockableName = unlockableName;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.unlockableName = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.unlockableName.toString());
    }
    
    public IMessage onMessage(final UnlockableToastPacket message, final MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message, ctx));
        return null;
    }
    
    public IMessage handleMessage(final UnlockableToastPacket message, final MessageContext ctx) {
        ToastHelper.sendUnlockableToast((Unlockable)ReskillableRegistries.UNLOCKABLES.getValue(message.unlockableName));
        return null;
    }
}
