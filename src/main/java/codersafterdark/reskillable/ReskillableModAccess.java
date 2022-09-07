// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable;

import org.apache.logging.log4j.Level;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import codersafterdark.reskillable.network.PacketHandler;
import codersafterdark.reskillable.network.MessageDataSync;
import net.minecraft.entity.player.EntityPlayerMP;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.unlockable.UnlockableConfig;
import javax.annotation.Nonnull;
import com.google.common.collect.Maps;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Arrays;
import java.util.Map;
import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.api.skill.SkillConfig;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.IModAccess;

public class ReskillableModAccess implements IModAccess
{
    @Nonnull
    @Override
    public SkillConfig getSkillConfig(final ResourceLocation name) {
        final SkillConfig skillConfig = new SkillConfig();
        final String categoryName = "skill." + name.toString();
        skillConfig.setEnabled(ConfigHandler.config.get(categoryName, "Enabled", skillConfig.isEnabled()).getBoolean());
        skillConfig.setLevelButton(ConfigHandler.config.get(categoryName, "LevelButton", skillConfig.hasLevelButton()).getBoolean());
        skillConfig.setLevelCap(ConfigHandler.config.get(categoryName, "Level Cap", skillConfig.getLevelCap()).getInt());
        skillConfig.setBaseLevelCost(ConfigHandler.config.get(categoryName, "Base Level Cost", skillConfig.getBaseLevelCost()).getInt());
        skillConfig.setSkillPointInterval(ConfigHandler.config.get(categoryName, "Skill Point Interval", skillConfig.getSkillPointInterval()).getInt());
        final String[] levelMapping = ConfigHandler.config.get(categoryName, "Level Staggering", new String[] { "1|1" }).getStringList();
        final Map<Integer, Integer> configLevelStaggering = Arrays.stream(levelMapping).map(string -> string.split("\\|")).filter(array -> array.length == 2).map(array -> Pair.of((Object)array[0], (Object)array[1])).map(pair -> Pair.of((Object)Integer.parseInt((String)pair.getKey()), (Object)Integer.parseInt((String)pair.getValue()))).collect(Collectors.toMap((Function<? super Object, ? extends Integer>)Pair::getKey, (Function<? super Object, ? extends Integer>)Pair::getValue));
        final Map<Integer, Integer> levelStaggering = (Map<Integer, Integer>)Maps.newHashMap();
        int lastLevel = skillConfig.getBaseLevelCost();
        for (int i = 1; i < skillConfig.getLevelCap(); ++i) {
            if (configLevelStaggering.containsKey(i)) {
                lastLevel = configLevelStaggering.get(i);
            }
            levelStaggering.put(i, lastLevel);
        }
        skillConfig.setLevelStaggering(levelStaggering);
        return skillConfig;
    }
    
    @Nonnull
    @Override
    public UnlockableConfig getUnlockableConfig(final ResourceLocation name, final int x, final int y, final int cost, final String[] defaultRequirements) {
        final UnlockableConfig unlockableConfig = new UnlockableConfig();
        final String categoryName = "trait." + name.toString();
        unlockableConfig.setEnabled(ConfigHandler.config.get(categoryName, "Enabled", unlockableConfig.isEnabled()).getBoolean());
        unlockableConfig.setX(ConfigHandler.config.get(categoryName, "X-Pos [0-4]:", x).getInt());
        unlockableConfig.setY(ConfigHandler.config.get(categoryName, "Y-Pos [0-3]:", y).getInt());
        unlockableConfig.setCost(ConfigHandler.config.get(categoryName, "Skill Point Cost", cost).getInt());
        unlockableConfig.setRequirementHolder(RequirementHolder.fromStringList(ConfigHandler.config.get(categoryName, "Requirements", defaultRequirements).getStringList()));
        return unlockableConfig;
    }
    
    @Override
    public void syncPlayerData(final EntityPlayer entityPlayer, final PlayerData playerData) {
        if (entityPlayer instanceof EntityPlayerMP) {
            final MessageDataSync message = new MessageDataSync(playerData);
            PacketHandler.INSTANCE.sendTo((IMessage)message, (EntityPlayerMP)entityPlayer);
        }
    }
    
    @Override
    public AdvancementProgress getAdvancementProgress(final EntityPlayer entityPlayer, final Advancement advancement) {
        return Reskillable.proxy.getPlayerAdvancementProgress(entityPlayer, advancement);
    }
    
    @Override
    public void log(final Level warn, final String s) {
        Reskillable.logger.log(warn, s);
    }
}
