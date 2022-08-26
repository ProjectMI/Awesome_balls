package ru.zonasumraka.maturinextensions.command;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.server.permission.PermissionAPI;
import ru.zonasumraka.maturinextensions.MaturinExtensions;

public class CommandMEReload implements ICommand {

	@Override
	public int compareTo(ICommand arg0) {
		return this.getName().compareTo(arg0.getName());
	}

	@Override
	public String getName() {
		return "mereload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.mereload.usage";
	}

	@Override
	public List<String> getAliases() {
		return Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		long start = System.currentTimeMillis();
		Class<ConfigManager> clazz = ConfigManager.class;
		File configFile = new File(Loader.instance().getConfigDir(), MaturinExtensions.MODID + ".cfg");
		try {
			Field field = clazz.getDeclaredField("CONFIGS");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Configuration> CONFIGS = (Map<String, Configuration>) field.get(null);
			CONFIGS.remove(configFile.getAbsolutePath());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponentTranslation("command.mereload.error"));
			return;
		}
		ConfigManager.sync(MaturinExtensions.MODID, Type.INSTANCE);
		long end = System.currentTimeMillis() - start;
		sender.sendMessage(new TextComponentTranslation("command.mereload.done", end));
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if (!(sender instanceof EntityPlayer))
			return true;
		return PermissionAPI.hasPermission((EntityPlayer) sender, "maturinextensions.command.reload");
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
