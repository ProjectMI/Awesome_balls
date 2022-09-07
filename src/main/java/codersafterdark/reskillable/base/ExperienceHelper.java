//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import net.minecraft.entity.player.EntityPlayer;

public class ExperienceHelper
{
    public static int getPlayerXP(final EntityPlayer player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + player.experience * player.xpBarCap());
    }
    
    public static void drainPlayerXP(final EntityPlayer player, final int amount) {
        addPlayerXP(player, -amount);
    }
    
    public static void addPlayerXP(final EntityPlayer player, final int amount) {
        final int experience = getPlayerXP(player) + amount;
        player.experienceTotal = experience;
        player.experienceLevel = getLevelForExperience(experience);
        final int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experience = (experience - expForLevel) / (float)player.xpBarCap();
    }
    
    public static int getExperienceForLevel(final int level) {
        if (level == 0) {
            return 0;
        }
        if (level > 0 && level < 17) {
            return level * level + 6 * level;
        }
        if (level > 16 && level < 32) {
            return (int)(2.5 * level * level - 40.5 * level + 360.0);
        }
        return (int)(4.5 * level * level - 162.5 * level + 2220.0);
    }
    
    public static int getLevelForExperience(final int experience) {
        int i;
        for (i = 0; getExperienceForLevel(i) <= experience; ++i) {}
        return i - 1;
    }
}
