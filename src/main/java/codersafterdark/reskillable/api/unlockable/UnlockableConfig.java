// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.unlockable;

import codersafterdark.reskillable.api.data.RequirementHolder;

public class UnlockableConfig
{
    private boolean enabled;
    private int x;
    private int y;
    private int cost;
    private RequirementHolder requirementHolder;
    
    public UnlockableConfig() {
        this.enabled = true;
        this.x = 1;
        this.y = 1;
        this.cost = 1;
        this.requirementHolder = RequirementHolder.realEmpty();
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public void setCost(final int cost) {
        this.cost = cost;
        AutoUnlocker.recheckUnlockables();
    }
    
    public RequirementHolder getRequirementHolder() {
        return this.requirementHolder;
    }
    
    public void setRequirementHolder(final RequirementHolder requirementHolder) {
        this.requirementHolder = requirementHolder;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
}
