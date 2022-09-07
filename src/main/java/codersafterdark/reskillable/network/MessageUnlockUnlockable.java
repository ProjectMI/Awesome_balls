//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.UnlockUnlockableEvent;
import net.minecraftforge.common.MinecraftForge;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import java.util.Objects;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageUnlockUnlockable implements IMessage, IMessageHandler<MessageUnlockUnlockable, IMessage>
{
    private ResourceLocation skill;
    private ResourceLocation unlockable;
    
    public MessageUnlockUnlockable() {
    }
    
    public MessageUnlockUnlockable(final ResourceLocation skill, final ResourceLocation unlockable) {
        this.skill = skill;
        this.unlockable = unlockable;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.skill = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        this.unlockable = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.skill.toString());
        ByteBufUtils.writeUTF8String(buf, this.unlockable.toString());
    }
    
    public IMessage onMessage(final MessageUnlockUnlockable message, final MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> this.handleMessage(message, ctx));
        return null;
    }
    
    public IMessage handleMessage(final MessageUnlockUnlockable message, final MessageContext context) {
        final EntityPlayer player = (EntityPlayer)context.getServerHandler().player;
        final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(message.skill);
        final Unlockable unlockable = Objects.requireNonNull((Unlockable)ReskillableRegistries.UNLOCKABLES.getValue(message.unlockable));
        final PlayerData data = PlayerDataHandler.get(player);
        final PlayerSkillInfo info = data.getSkillInfo(skill);
        if (!info.isUnlocked(unlockable) && info.getSkillPoints() >= unlockable.getCost() && data.matchStats(unlockable.getRequirements()) && !MinecraftForge.EVENT_BUS.post((Event)new UnlockUnlockableEvent.Pre(player, unlockable))) {
            info.unlock(unlockable, player);
            data.saveAndSync();
            MinecraftForge.EVENT_BUS.post((Event)new UnlockUnlockableEvent.Post(player, unlockable));
        }
        return null;
    }
}
