package ru.zonasumraka.maturinextensions.command;

import java.util.Collections;
import java.util.List;

import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.permission.PermissionAPI;
import ru.zonasumraka.maturinextensions.capability.MECapabilities;

public class CommandChangeProgression implements ICommand {

	@Override
	public int compareTo(ICommand arg0) {
		return this.getName().compareTo(arg0.getName());
	}

	@Override
	public String getName() {
		return "setskill";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.setskill.usage";
	}

	@Override
	public List<String> getAliases() {
		return Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 3 && args.length != 2)
			throw new WrongUsageException(getUsage(sender));
		EntityPlayer player = CommandBase.getEntity(server, sender, args[0], EntityPlayer.class);
		ResourceLocation res = new ResourceLocation(args[1]);
		if (ReskillableRegistries.SKILLS.getValue(res) == null)
			throw new CommandException("command.setskill.noskill", res.toString());
		if (args.length == 2) {
			sender.sendMessage(new TextComponentString(String.valueOf(
					player.getCapability(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, null).getStat(res))));
		} else {
			try {
				MECapabilities.MECapabilityWrapper.putStat(player, res, Double.parseDouble(args[2]));
				sender.sendMessage(new TextComponentTranslation("command.setskill.success", args[1], args[2]));
			} catch(NumberFormatException e) {
				throw new CommandException("command.setskill.notnumber", args[2]);
			}
		}

	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if (!(sender instanceof EntityPlayer))
			return true;
		return PermissionAPI.hasPermission((EntityPlayer) sender, "maturinextensions.command.setskill");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

}
