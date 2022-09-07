// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public abstract class Requirement
{
    protected String tooltip;
    
    public Requirement() {
        this.tooltip = "";
    }
    
    public abstract boolean achievedByPlayer(final EntityPlayer p0);
    
    public String getToolTip(final PlayerData data) {
        try {
            return String.format(this.internalToolTip(), (data == null || !data.requirementAchieved(this)) ? TextFormatting.RED : TextFormatting.GREEN);
        }
        catch (IllegalArgumentException e) {
            return this.internalToolTip();
        }
    }
    
    public RequirementComparision matches(final Requirement other) {
        return this.equals(other) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
    
    public boolean isEnabled() {
        return true;
    }
    
    public final String internalToolTip() {
        return this.tooltip;
    }
    
    public boolean isCacheable() {
        return true;
    }
}
