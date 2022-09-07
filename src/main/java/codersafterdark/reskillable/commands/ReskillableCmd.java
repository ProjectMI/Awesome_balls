//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.commands;

import net.minecraft.command.ICommandSender;
import javax.annotation.Nonnull;
import net.minecraft.command.ICommand;
import net.minecraftforge.server.command.CommandTreeBase;

public class ReskillableCmd extends CommandTreeBase
{
    public ReskillableCmd() {
        this.addSubcommand((ICommand)new CmdIncrementSkill());
        this.addSubcommand((ICommand)new CmdResetAll());
        this.addSubcommand((ICommand)new CmdResetSkill());
        this.addSubcommand((ICommand)new CmdSetSkillLevel());
        this.addSubcommand((ICommand)new CmdToggleTrait());
    }
    
    @Nonnull
    public String getName() {
        return "reskillable";
    }
    
    @Nonnull
    public String getUsage(@Nonnull final ICommandSender sender) {
        return "reskillable.command.usage";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
