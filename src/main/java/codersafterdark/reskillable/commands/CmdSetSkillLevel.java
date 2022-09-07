//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.commands;

import java.util.ArrayList;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.toast.ToastHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.command.CommandException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.Nonnull;
import net.minecraft.command.CommandBase;

public class CmdSetSkillLevel extends CommandBase
{
    @Nonnull
    public String getName() {
        return "setskilllevel";
    }
    
    @Nonnull
    public String getUsage(@Nonnull final ICommandSender sender) {
        return "reskillable.command.setskilllevel.usage";
    }
    
    public void execute(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("reskillable.command.invalid.missing.playerskilllevel", new Object[0]);
        }
        if (args.length == 1) {
            throw new CommandException("reskillable.command.invalid.missing.skilllevel", new Object[0]);
        }
        if (args.length == 2) {
            throw new CommandException("reskillable.command.invalid.missing.level", new Object[0]);
        }
        final EntityPlayerMP player = getPlayer(server, sender, args[0]);
        args[1] = args[1].replaceAll(":", ".");
        final String[] parts = args[1].split("\\.");
        final ResourceLocation skillName = (parts.length > 1) ? new ResourceLocation(parts[0], args[1].substring(parts[0].length() + 1)) : new ResourceLocation(args[1]);
        if (!ReskillableRegistries.SKILLS.containsKey(skillName)) {
            throw new CommandException("reskillable.command.invalid.skill", new Object[] { skillName });
        }
        int level;
        try {
            level = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            throw new CommandException("reskillable.command.invalid.missing.level", new Object[] { args[2] });
        }
        if (level < 1) {
            throw new CommandException("reskillable.command.invalid.belowmin", new Object[] { level });
        }
        final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(skillName);
        if (level > skill.getCap()) {
            throw new CommandException("reskillable.command.invalid.pastcap", new Object[] { level, skill.getCap() });
        }
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)player);
        final PlayerSkillInfo skillInfo = data.getSkillInfo(skill);
        final int oldLevel = skillInfo.getLevel();
        if (!MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Pre((EntityPlayer)player, skill, level, oldLevel))) {
            skillInfo.setLevel(level);
            data.saveAndSync();
            MinecraftForge.EVENT_BUS.post((Event)new LevelUpEvent.Post((EntityPlayer)player, skill, level, oldLevel));
            ToastHelper.sendSkillToast(player, skill, level);
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.success.setskilllevel", new Object[] { skillName, level, player.getDisplayName() }));
        }
        else {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.fail.setskilllevel", new Object[] { skillName, level, player.getDisplayName() }));
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
        if (args.length == 2) {
            final String partial = args[1].replaceAll(":", ".");
            return ReskillableRegistries.SKILLS.getValuesCollection().stream().map((Function<? super Object, ?>)Skill::getKey).filter(skillName -> skillName.startsWith(partial)).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        if (args.length == 3) {
            args[1] = args[1].replaceAll(":", ".");
            final String[] parts = args[1].split("\\.");
            final ResourceLocation skillName2 = (parts.length > 1) ? new ResourceLocation(parts[0], args[1].substring(parts[0].length() + 1)) : new ResourceLocation(args[1]);
            if (ReskillableRegistries.SKILLS.containsKey(skillName2)) {
                final Skill skill = (Skill)ReskillableRegistries.SKILLS.getValue(skillName2);
                final String level = args[2];
                return IntStream.rangeClosed(1, skill.getCap()).mapToObj((IntFunction<?>)Integer::toString).filter(iString -> iString.startsWith(level)).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
            }
        }
        return new ArrayList<String>();
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
