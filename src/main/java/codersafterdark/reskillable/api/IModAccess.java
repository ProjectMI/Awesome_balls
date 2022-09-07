// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api;

import org.apache.logging.log4j.Level;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.UnlockableConfig;
import codersafterdark.reskillable.api.skill.SkillConfig;
import net.minecraft.util.ResourceLocation;

public interface IModAccess
{
    SkillConfig getSkillConfig(final ResourceLocation p0);
    
    UnlockableConfig getUnlockableConfig(final ResourceLocation p0, final int p1, final int p2, final int p3, final String[] p4);
    
    void syncPlayerData(final EntityPlayer p0, final PlayerData p1);
    
    AdvancementProgress getAdvancementProgress(final EntityPlayer p0, final Advancement p1);
    
    void log(final Level p0, final String p1);
}
