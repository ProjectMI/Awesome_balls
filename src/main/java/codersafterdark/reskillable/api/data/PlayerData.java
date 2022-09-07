//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import codersafterdark.reskillable.api.unlockable.IAbilityEventHandler;
import java.util.function.Consumer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.nbt.NBTBase;
import java.util.Iterator;
import codersafterdark.reskillable.api.ReskillableAPI;
import net.minecraft.nbt.NBTTagCompound;
import codersafterdark.reskillable.api.requirement.Requirement;
import java.util.function.Predicate;
import java.util.TreeSet;
import codersafterdark.reskillable.api.unlockable.Ability;
import java.util.Set;
import java.util.Collection;
import codersafterdark.reskillable.api.ReskillableRegistries;
import java.util.HashMap;
import codersafterdark.reskillable.api.skill.Skill;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import java.lang.ref.WeakReference;
import codersafterdark.reskillable.api.requirement.RequirementCache;

public class PlayerData
{
    private static final String TAG_SKILLS_CMP = "SkillLevels";
    private final boolean client;
    private final RequirementCache requirementCache;
    public WeakReference<EntityPlayer> playerWR;
    private Map<Skill, PlayerSkillInfo> skillInfo;
    
    public PlayerData(final EntityPlayer player) {
        this.skillInfo = new HashMap<Skill, PlayerSkillInfo>();
        this.playerWR = new WeakReference<EntityPlayer>(player);
        this.client = player.getEntityWorld().isRemote;
        this.requirementCache = RequirementCache.getCache(player);
        final PlayerSkillInfo[] playerSkillInfo = new PlayerSkillInfo[1];
        ReskillableRegistries.SKILLS.getValuesCollection().forEach(s -> playerSkillInfo[0] = this.skillInfo.put(s, new PlayerSkillInfo(s)));
        this.load();
    }
    
    public PlayerSkillInfo getSkillInfo(final Skill s) {
        return this.skillInfo.get(s);
    }
    
    public Collection<PlayerSkillInfo> getAllSkillInfo() {
        return this.skillInfo.values();
    }
    
    public boolean hasAnyAbilities() {
        return !this.getAllAbilities().isEmpty();
    }
    
    public Set<Ability> getAllAbilities() {
        final Set<Ability> set = new TreeSet<Ability>();
        this.skillInfo.values().forEach(info -> info.addAbilities(set));
        return set;
    }
    


    public boolean requirementAchieved(final Requirement requirement) {
        return this.getRequirementCache().requirementAchieved(requirement);
    }
    public boolean matchStats(final RequirementHolder holder) {
        return this.playerWR.get() == null || holder.getRequirements().stream().allMatch((Predicate<? super Object>)this::requirementAchieved);
    }
    public final RequirementCache getRequirementCache() {
        return this.requirementCache;
    }
    
    public void load() {
        if (!this.client) {
            final EntityPlayer player = this.playerWR.get();
            if (player != null) {
                final NBTTagCompound cmp = PlayerDataHandler.getDataCompoundForPlayer(player);
                this.loadFromNBT(cmp);
            }
        }
    }
    
    public void save() {
        if (!this.client) {
            final EntityPlayer player = this.playerWR.get();
            if (player != null) {
                final NBTTagCompound cmp = PlayerDataHandler.getDataCompoundForPlayer(player);
                this.saveToNBT(cmp);
            }
        }
    }
    
    public void sync() {
        if (!this.client) {
            final EntityPlayer player = this.playerWR.get();
            ReskillableAPI.getInstance().syncPlayerData(player, this);
        }
    }
    
    public void saveAndSync() {
        this.save();
        this.sync();
    }
    
    public void loadFromNBT(final NBTTagCompound cmp) {
        final NBTTagCompound skillsCmp = cmp.getCompoundTag("SkillLevels");
        for (final PlayerSkillInfo info : this.skillInfo.values()) {
            final String key = info.skill.getKey();
            if (skillsCmp.hasKey(key)) {
                final NBTTagCompound infoCmp = skillsCmp.getCompoundTag(key);
                info.loadFromNBT(infoCmp);
            }
        }
    }
    
    public void saveToNBT(final NBTTagCompound cmp) {
        final NBTTagCompound skillsCmp = new NBTTagCompound();
        for (final PlayerSkillInfo info : this.skillInfo.values()) {
            final String key = info.skill.getKey();
            final NBTTagCompound infoCmp = new NBTTagCompound();
            info.saveToNBT(infoCmp);
            skillsCmp.setTag(key, (NBTBase)infoCmp);
        }
        cmp.setTag("SkillLevels", (NBTBase)skillsCmp);
    }
    
    public void tickPlayer(final TickEvent.PlayerTickEvent event) {
        this.forEachEventHandler(h -> h.onPlayerTick(event));
    }
    
    public void blockDrops(final BlockEvent.HarvestDropsEvent event) {
        this.forEachEventHandler(h -> h.onBlockDrops(event));
    }
    
    public void mobDrops(final LivingDropsEvent event) {
        this.forEachEventHandler(h -> h.onMobDrops(event));
    }
    
    public void breakSpeed(final PlayerEvent.BreakSpeed event) {
        this.forEachEventHandler(h -> h.getBreakSpeed(event));
    }
    
    public void attackMob(final LivingHurtEvent event) {
        this.forEachEventHandler(h -> h.onAttackMob(event));
    }
    
    public void hurt(final LivingHurtEvent event) {
        this.forEachEventHandler(h -> h.onHurt(event));
    }
    
    public void rightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        this.forEachEventHandler(h -> h.onRightClickBlock(event));
    }
    
    public void enderTeleport(final EnderTeleportEvent event) {
        this.forEachEventHandler(h -> h.onEnderTeleport(event));
    }
    
    public void killMob(final LivingDeathEvent event) {
        this.forEachEventHandler(h -> h.onKillMob(event));
    }
    
    public void forEachEventHandler(final Consumer<IAbilityEventHandler> consumer) {
        this.skillInfo.values().forEach(info -> info.forEachEventHandler(consumer));
    }
}
