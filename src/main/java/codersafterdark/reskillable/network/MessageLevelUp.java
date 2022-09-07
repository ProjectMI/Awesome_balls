//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.base.ExperienceHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraftforge.common.MinecraftForge;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageLevelUp implements IMessage, IMessageHandler<MessageLevelUp, IMessage>
{
    public ResourceLocation skillName;
    
    public MessageLevelUp() {
    }
    
    public MessageLevelUp(final ResourceLocation skillName) {
        this.skillName = skillName;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.skillName = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.skillName.toString());
    }
    
    public IMessage onMessage(final MessageLevelUp message, final MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> this.handleMessage(message, ctx));
        return null;
    }
    
    public IMessage handleMessage(final MessageLevelUp message, final MessageContext context) {
        final EntityPlayer player = (EntityPlayer)context.getServerHandler().player;
        final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(message.skillName);
        final PlayerData data = PlayerDataHandler.get(player);
        final PlayerSkillInfo info = data.getSkillInfo(skill);
        if (!info.isCapped()) {
            final int cost = info.getLevelUpCost();
            if (player.experienceLevel >= cost || player.isCreative()) {
                final int oldLevel = info.getLevel();
                if (!MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Pre(player, skill, oldLevel + 1, oldLevel))) {
                    if (!player.isCreative()) {
                        ExperienceHelper.drainPlayerXP(player, ExperienceHelper.getExperienceForLevel(cost));
                    }
                    info.levelUp();
                    data.saveAndSync();
                    MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Post(player, skill, info.getLevel(), oldLevel));
                }
            }
        }
        return null;
    }
}
