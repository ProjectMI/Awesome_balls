//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.commands;

import java.util.ArrayList;
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
import codersafterdark.reskillable.api.toast.ToastHelper;
import codersafterdark.reskillable.api.event.UnlockUnlockableEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.Event;
import codersafterdark.reskillable.api.event.LockUnlockableEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.command.CommandException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.Nonnull;
import net.minecraft.command.CommandBase;

public class CmdToggleTrait extends CommandBase
{
    @Nonnull
    public String getName() {
        return "toggletrait";
    }
    
    @Nonnull
    public String getUsage(@Nonnull final ICommandSender sender) {
        return "reskillable.command.toggletrait.usage";
    }
    
    public void execute(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("reskillable.command.invalid.missing.playertrait", new Object[0]);
        }
        if (args.length == 1) {
            throw new CommandException("reskillable.command.invalid.missing.trait", new Object[0]);
        }
        final EntityPlayerMP player = getPlayer(server, sender, args[0]);
        args[1] = args[1].replaceAll(":", ".");
        final String[] parts = args[1].split("\\.");
        final ResourceLocation traitName = (parts.length > 1) ? new ResourceLocation(parts[0], args[1].substring(parts[0].length() + 1)) : new ResourceLocation(args[1]);
        if (!ReskillableRegistries.UNLOCKABLES.containsKey(traitName)) {
            throw new CommandException("reskillable.command.invalid.trait", new Object[] { traitName });
        }
        final Unlockable trait = (Unlockable)ReskillableRegistries.UNLOCKABLES.getValue(traitName);
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)player);
        final PlayerSkillInfo skillInfo = data.getSkillInfo(trait.getParentSkill());
        if (skillInfo.isUnlocked(trait)) {
            if (!MinecraftForge.EVENT_BUS.post((Event)new LockUnlockableEvent.Pre((EntityPlayer)player, trait))) {
                skillInfo.lock(trait, (EntityPlayer)player);
                data.saveAndSync();
                MinecraftForge.EVENT_BUS.post((Event)new LockUnlockableEvent.Post((EntityPlayer)player, trait));
                sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.success.locktrait", new Object[] { traitName, player.getDisplayName() }));
            }
            else {
                sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.fail.locktrait", new Object[] { traitName, player.getDisplayName() }));
            }
        }
        else if (!MinecraftForge.EVENT_BUS.post((Event)new UnlockUnlockableEvent.Pre((EntityPlayer)player, trait))) {
            skillInfo.unlock(trait, (EntityPlayer)player);
            data.saveAndSync();
            MinecraftForge.EVENT_BUS.post((Event)new UnlockUnlockableEvent.Post((EntityPlayer)player, trait));
            ToastHelper.sendUnlockableToast(player, trait);
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.success.unlocktrait", new Object[] { traitName, player.getDisplayName() }));
        }
        else {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("reskillable.command.fail.unlocktrait", new Object[] { traitName, player.getDisplayName() }));
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
            return ReskillableRegistries.UNLOCKABLES.getValuesCollection().stream().map((Function<? super Object, ?>)Unlockable::getKey).filter(traitName -> traitName.startsWith(partial)).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        return new ArrayList<String>();
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
