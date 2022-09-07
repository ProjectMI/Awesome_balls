//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.toast;

import codersafterdark.reskillable.network.SkillToastPacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import codersafterdark.reskillable.network.UnlockableToastPacket;
import codersafterdark.reskillable.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.Minecraft;

public class ToastHelper
{
    @SideOnly(Side.CLIENT)
    public static void sendToast(final AbstractToast toast) {
        Minecraft.getMinecraft().getToastGui().add((IToast)toast);
    }
    
    @SideOnly(Side.CLIENT)
    public static void sendUnlockableToast(final Unlockable u) {
        if (u != null) {
            sendToast(new UnlockableToast(u));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static void sendSkillToast(final Skill skill, final int level) {
        if (skill != null) {
            sendToast(new SkillToast(skill, level));
        }
    }
    
    public static void sendUnlockableToast(final EntityPlayerMP player, final Unlockable u) {
        if (u != null) {
            PacketHandler.INSTANCE.sendTo((IMessage)new UnlockableToastPacket(u.getRegistryName()), player);
        }
    }
    
    public static void sendSkillToast(final EntityPlayerMP player, final Skill skill, final int level) {
        if (skill != null) {
            PacketHandler.INSTANCE.sendTo((IMessage)new SkillToastPacket(skill.getRegistryName(), level), player);
        }
    }
}
