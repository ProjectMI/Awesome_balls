//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.event;

import net.minecraft.command.CommandException;
import baubles.api.BaubleType;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import baubles.api.IBauble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import baubles.api.BaublesApi;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;

public class CommandBaubles extends CommandBase
{
    private List<String> aliases;
    
    public CommandBaubles() {
        (this.aliases = new ArrayList<String>()).add("baub");
        this.aliases.add("bau");
    }
    
    public String getName() {
        return "baubles";
    }
    
    public List<String> getAliases() {
        return this.aliases;
    }
    
    public String getUsage(final ICommandSender icommandsender) {
        return "/baubles <action> [<player> [<params>]]";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public boolean isUsernameIndex(final String[] astring, final int i) {
        return i == 1;
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("§3You can also use /baub or /bau instead of /baubles.", new Object[0]));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("§3Use this to view the baubles inventory of a player.", new Object[0]));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("  /baubles view <player>", new Object[0]));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("§3Use this to clear a players baubles inventory. Default is everything or you can give a slot number", new Object[0]));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("  /baubles clear <player> [<slot>]", new Object[0]));
        }
        else if (args.length >= 2) {
            final EntityPlayerMP entityplayermp = getPlayer(server, sender, args[1]);
            if (entityplayermp == null) {
                sender.sendMessage((ITextComponent)new TextComponentTranslation("§c" + args[1] + " not found", new Object[0]));
                return;
            }
            final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler((EntityPlayer)entityplayermp);
            if (args[0].equalsIgnoreCase("view")) {
                sender.sendMessage((ITextComponent)new TextComponentTranslation("§3Showing baubles for " + entityplayermp.getName(), new Object[0]));
                for (int a = 0; a < baubles.getSlots(); ++a) {
                    final ItemStack st = baubles.getStackInSlot(a);
                    if (!st.isEmpty() && st.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
                        final IBauble bauble = (IBauble)st.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
                        final BaubleType bt = bauble.getBaubleType(st);
                        sender.sendMessage((ITextComponent)new TextComponentTranslation("§3 [Slot " + a + "] " + bt + " " + st.getDisplayName(), new Object[0]));
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("clear")) {
                if (args.length >= 3) {
                    int slot = -1;
                    try {
                        slot = Integer.parseInt(args[2]);
                    }
                    catch (Exception ex) {}
                    if (slot < 0 || slot >= baubles.getSlots()) {
                        sender.sendMessage((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
                        sender.sendMessage((ITextComponent)new TextComponentTranslation("§cUse /baubles help to get help", new Object[0]));
                    }
                    else {
                        baubles.setStackInSlot(slot, ItemStack.EMPTY);
                        sender.sendMessage((ITextComponent)new TextComponentTranslation("§3Cleared baubles slot " + slot + " for " + entityplayermp.getName(), new Object[0]));
                        entityplayermp.sendMessage((ITextComponent)new TextComponentTranslation("§4Your baubles slot " + slot + " has been cleared by admin " + sender.getName(), new Object[0]));
                    }
                }
                else {
                    for (int a = 0; a < baubles.getSlots(); ++a) {
                        baubles.setStackInSlot(a, ItemStack.EMPTY);
                    }
                    sender.sendMessage((ITextComponent)new TextComponentTranslation("§3Cleared all baubles slots for " + entityplayermp.getName(), new Object[0]));
                    entityplayermp.sendMessage((ITextComponent)new TextComponentTranslation("§4All your baubles slots have been cleared by admin " + sender.getName(), new Object[0]));
                }
            }
            else {
                sender.sendMessage((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
                sender.sendMessage((ITextComponent)new TextComponentTranslation("§cUse /baubles help to get help", new Object[0]));
            }
        }
        else {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("§cUse /baubles help to get help", new Object[0]));
        }
    }
}
