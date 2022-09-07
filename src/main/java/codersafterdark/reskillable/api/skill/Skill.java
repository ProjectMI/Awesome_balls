//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.skill;

import javax.annotation.Nonnull;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.util.text.TextComponentTranslation;
import codersafterdark.reskillable.api.ReskillableAPI;
import java.util.ArrayList;
import java.util.HashMap;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Skill extends IForgeRegistryEntry.Impl<Skill> implements Comparable<Skill>
{
    private final Map<Integer, ResourceLocation> customSprites;
    private final List<Unlockable> unlockables;
    private final ResourceLocation spriteLocation;
    private final String name;
    protected ResourceLocation background;
    protected SkillConfig skillConfig;
    private boolean hidden;
    
    public Skill(final ResourceLocation name, final ResourceLocation background) {
        this.customSprites = new HashMap<Integer, ResourceLocation>();
        this.unlockables = new ArrayList<Unlockable>();
        this.name = name.toString().replace(":", ".");
        this.background = background;
        this.spriteLocation = new ResourceLocation(name.getNamespace(), "textures/skills/" + name.getPath() + ".png");
        this.setRegistryName(name);
        this.skillConfig = ReskillableAPI.getInstance().getSkillConfig(name);
    }
    
    public void addUnlockable(final Unlockable unlockable) {
        this.unlockables.add(unlockable);
    }
    
    public List<Unlockable> getUnlockables() {
        return this.unlockables;
    }
    
    public String getKey() {
        return this.name;
    }
    
    public String getName() {
        return new TextComponentTranslation("reskillable.skill." + this.getKey(), new Object[0]).getUnformattedComponentText();
    }
    
    public ResourceLocation getBackground() {
        return this.background;
    }
    
    public void setBackground(final ResourceLocation resourceLocation) {
        this.background = resourceLocation;
    }
    
    public int getCap() {
        return this.skillConfig.getLevelCap();
    }
    
    public boolean isEnabled() {
        return this.skillConfig.isEnabled();
    }
    
    public boolean hasLevelButton() {
        return this.skillConfig.hasLevelButton();
    }
    
    public ResourceLocation getSpriteLocation() {
        return this.spriteLocation;
    }
    
    public Pair<Integer, Integer> getSpriteFromRank(final int rank) {
        return (Pair<Integer, Integer>)new MutablePair((Object)(Math.min(rank / 2, 3) * 16), (Object)0);
    }
    
    public void setCustomSprite(final int rank, final ResourceLocation location) {
        this.customSprites.put(rank, location);
    }
    
    public void removeCustomSprite(final int rank) {
        this.customSprites.remove(rank);
    }
    
    public ResourceLocation getSpriteLocation(final int rank) {
        if (this.customSprites.containsKey(rank)) {
            return this.customSprites.get(rank);
        }
        for (int i = rank - 1; i >= 0; --i) {
            if (this.customSprites.containsKey(i)) {
                return this.customSprites.get(i);
            }
        }
        return null;
    }
    
    public boolean hasCustomSprites() {
        return !this.customSprites.isEmpty();
    }
    
    public int compareTo(@Nonnull final Skill o) {
        return o.getName().compareTo(this.getName());
    }
    
    public int getSkillPointInterval() {
        return this.skillConfig.getSkillPointInterval();
    }
    
    public int getLevelUpCost(final int level) {
        final int cost = this.skillConfig.getLevelStaggering().entrySet().stream().filter(entry -> entry.getKey() < level + 1).mapToInt(Map.Entry::getValue).sum() + this.skillConfig.getBaseLevelCost();
        return (cost < 0) ? 0 : cost;
    }
    
    public final SkillConfig getSkillConfig() {
        return this.skillConfig;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public int getRank(final int level) {
        return 8 * level / this.getCap();
    }
}
