//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import codersafterdark.reskillable.api.unlockable.IAbilityEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.Ability;
import java.util.Set;
import net.minecraft.nbt.NBTBase;
import java.util.Iterator;
import net.minecraftforge.registries.IForgeRegistryEntry;
import java.util.function.Consumer;
import java.util.Optional;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.ReskillableRegistries;
import net.minecraft.nbt.NBTTagCompound;
import java.util.ArrayList;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import java.util.List;
import codersafterdark.reskillable.api.skill.Skill;

public class PlayerSkillInfo
{
    private static final String TAG_LEVEL = "level";
    private static final String TAG_SKILL_POINTS = "skillPoints";
    private static final String TAG_UNLOCKABLES = "unlockables";
    public final Skill skill;
    private int level;
    private int skillPoints;
    private List<Unlockable> unlockables;
    
    public PlayerSkillInfo(final Skill skill) {
        this.unlockables = new ArrayList<Unlockable>();
        this.skill = skill;
        this.level = 1;
        this.respec();
    }
    
    public void loadFromNBT(final NBTTagCompound cmp) {
        this.level = cmp.getInteger("level");
        this.skillPoints = cmp.getInteger("skillPoints");
        this.unlockables.clear();
        final NBTTagCompound unlockablesCmp = cmp.getCompoundTag("unlockables");
        for (final String s : unlockablesCmp.getKeySet()) {
            Optional.ofNullable(ReskillableRegistries.UNLOCKABLES.getValue(new ResourceLocation(s.replace(".", ":")))).ifPresent(this.unlockables::add);
        }
    }
    
    public void saveToNBT(final NBTTagCompound cmp) {
        cmp.setInteger("level", this.level);
        cmp.setInteger("skillPoints", this.skillPoints);
        final NBTTagCompound unlockablesCmp = new NBTTagCompound();
        for (final Unlockable u : this.unlockables) {
            unlockablesCmp.setBoolean(u.getKey(), true);
        }
        cmp.setTag("unlockables", (NBTBase)unlockablesCmp);
    }
    
    public int getLevel() {
        if (this.level <= 0) {
            this.level = 1;
        }
        if (this.level > this.skill.getCap()) {
            this.level = this.skill.getCap();
        }
        return this.level;
    }
    
    public void setLevel(final int level) {
        final int interval = this.skill.getSkillPointInterval();
        this.skillPoints += level / interval - this.level / interval;
        this.level = level;
    }
    
    public int getRank() {
        return this.skill.getRank(this.level);
    }
    
    public int getSkillPoints() {
        return this.skillPoints;
    }
    
    public boolean isCapped() {
        return this.level >= this.skill.getCap();
    }
    
    public int getLevelUpCost() {
        return this.skill.getLevelUpCost(this.level);
    }
    
    public boolean isUnlocked(final Unlockable u) {
        return this.unlockables.contains(u);
    }
    
    public void addAbilities(final Set<Ability> abilities) {
        for (final Unlockable u : this.unlockables) {
            if (u instanceof Ability) {
                abilities.add((Ability)u);
            }
        }
    }
    
    public void levelUp() {
        ++this.level;
        if (this.level % this.skill.getSkillPointInterval() == 0) {
            ++this.skillPoints;
        }
    }
    
    public void lock(final Unlockable u, final EntityPlayer p) {
        this.skillPoints += u.getCost();
        this.unlockables.remove(u);
        u.onLock(p);
    }
    
    public void unlock(final Unlockable u, final EntityPlayer p) {
        this.skillPoints -= u.getCost();
        this.unlockables.add(u);
        u.onUnlock(p);
    }
    
    public void respec() {
        this.unlockables.clear();
        this.skillPoints = this.level / this.skill.getSkillPointInterval();
    }
    
    public void forEachEventHandler(final Consumer<IAbilityEventHandler> consumer) {
        this.unlockables.forEach(u -> {
            if (((Unlockable)u).isEnabled() && u instanceof IAbilityEventHandler) {
                consumer.accept(u);
            }
        });
    }
}
