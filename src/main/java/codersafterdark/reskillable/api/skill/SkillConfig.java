// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.skill;

import java.util.Map;

public class SkillConfig
{
    private boolean enabled;
    private boolean levelButton;
    private int levelCap;
    private int skillPointInterval;
    private int baseLevelCost;
    private Map<Integer, Integer> levelStaggering;
    
    public SkillConfig() {
        this.enabled = true;
        this.levelButton = true;
        this.levelCap = 32;
        this.skillPointInterval = 2;
        this.baseLevelCost = 3;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean hasLevelButton() {
        return this.levelButton;
    }
    
    public void setLevelButton(final boolean button) {
        this.levelButton = button;
    }
    
    public int getLevelCap() {
        return this.levelCap;
    }
    
    public void setLevelCap(final int levelCap) {
        this.levelCap = levelCap;
    }
    
    public int getSkillPointInterval() {
        return this.skillPointInterval;
    }
    
    public void setSkillPointInterval(final int skillPointInterval) {
        this.skillPointInterval = skillPointInterval;
    }
    
    public int getBaseLevelCost() {
        return this.baseLevelCost;
    }
    
    public void setBaseLevelCost(final int baseLevelCost) {
        this.baseLevelCost = baseLevelCost;
    }
    
    public Map<Integer, Integer> getLevelStaggering() {
        return this.levelStaggering;
    }
    
    public void setLevelStaggering(final Map<Integer, Integer> levelStaggering) {
        this.levelStaggering = levelStaggering;
    }
}
