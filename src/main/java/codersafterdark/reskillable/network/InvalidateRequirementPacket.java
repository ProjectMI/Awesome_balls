//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import codersafterdark.reskillable.api.requirement.Requirement;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class InvalidateRequirementPacket implements IMessage, IMessageHandler<InvalidateRequirementPacket, IMessage>
{
    private Class<? extends Requirement>[] cacheTypes;
    private UUID uuid;
    
    public InvalidateRequirementPacket() {
    }
    
    public InvalidateRequirementPacket(final UUID uuid, final Class<? extends Requirement>... cacheTypes) {
        this.uuid = uuid;
        this.cacheTypes = cacheTypes;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.uuid = new UUID(buf.readLong(), buf.readLong());
        this.cacheTypes = (Class<? extends Requirement>[])new Class[buf.readInt()];
        for (int i = 0; i < this.cacheTypes.length; ++i) {
            try {
                final Class<?> rClass = Class.forName(ByteBufUtils.readUTF8String(buf));
                if (Requirement.class.isAssignableFrom(rClass)) {
                    this.cacheTypes[i] = (Class<? extends Requirement>)rClass;
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());
        buf.writeInt(this.cacheTypes.length);
        for (final Class<? extends Requirement> rClass : this.cacheTypes) {
            ByteBufUtils.writeUTF8String(buf, rClass.getName());
        }
    }
    
    public IMessage onMessage(final InvalidateRequirementPacket message, final MessageContext ctx) {
        if (ctx.side.isServer()) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> this.handleMessage(message, ctx));
        }
        else {
            Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message, ctx));
        }
        return null;
    }
    
    public IMessage handleMessage(final InvalidateRequirementPacket message, final MessageContext ctx) {
        if (message.cacheTypes.length == 0) {
            RequirementCache.getCache(message.uuid, ctx.side.isClient()).forceClear();
        }
        else {
            RequirementCache.invalidateCacheNoPacket(message.uuid, ctx.side.isClient(), message.cacheTypes);
        }
        return null;
    }
}
