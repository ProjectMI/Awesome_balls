//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.commands;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import java.util.Collection;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.toast.ToastHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraftforge.common.MinecraftForge;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.command.CommandException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.Nonnull;
import net.minecraft.command.CommandBase;

public class CmdResetAll extends CommandBase
{
    @Nonnull
    public String getName() {
        return "resetall";
    }
    
    @Nonnull
    public String getUsage(@Nonnull final ICommandSender sender) {
        return "reskillable.command.resetall.usage";
    }
    
    public void execute(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("reskillable.command.invalid.missing.player", new Object[0]);
        }
        final EntityPlayerMP player = getPlayer(server, sender, args[0]);
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)player);
        final Collection<PlayerSkillInfo> allSkils = data.getAllSkillInfo();
        final StringBuilder failedSkills = new StringBuilder();
        for (final PlayerSkillInfo skillInfo : allSkils) {
            final int oldLevel = skillInfo.getLevel();
            if (!MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Pre((EntityPlayer)player, skillInfo.skill, 1, oldLevel))) {
                skillInfo.setLevel(1);
                skillInfo.respec();
                MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Post((EntityPlayer)player, skillInfo.skill, 1, oldLevel));
                ToastHelper.sendSkillToast(player, skillInfo.skill, 1);
            }
            else {
                failedSkills.append(skillInfo.skill.getName()).append(", ");
            }
        }
        data.saveAndSync();
        if (failedSkills.length() == 0) {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.success.resetall", new Object[] { player.getDisplayName() }));
        }
        else {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.fail.resetall", new Object[] { failedSkills.substring(0, failedSkills.length() - 2), player.getDisplayName() }));
        }
    }
    
    @Nonnull
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos targetPos) {
        if (args.length == 0) {
            return Arrays.asList(server.getPlayerList().getOnlinePlayerNames());
        }
        if (args.length == 1) {
            final String partialName = args[0];
            return Arrays.stream(server.getPlayerList().getOnlinePlayerNames()).filter(name -> name.startsWith(partialName)).collect((Collector<? super String, ?, List<String>>)Collectors.toList());
        }
        return new ArrayList<String>();
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
