// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic.impl;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.requirement.logic.OuterRequirement;
import codersafterdark.reskillable.api.requirement.Requirement;

public class NOTRequirement extends Requirement implements OuterRequirement
{
    private final Requirement requirement;
    
    public NOTRequirement(final Requirement requirement) {
        this.requirement = requirement;
        String parentToolTip = this.requirement.internalToolTip();
        if (parentToolTip.startsWith(TextFormatting.GRAY + " - ")) {
            parentToolTip = parentToolTip.replaceFirst(TextFormatting.GRAY + " - ", "");
        }
        String start = TextFormatting.GRAY + " - ";
        if (parentToolTip.length() > 2 && parentToolTip.startsWith("§")) {
            start += parentToolTip.substring(0, 2);
            parentToolTip = parentToolTip.substring(2);
        }
        this.tooltip = start + '!' + parentToolTip;
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer player) {
        return !this.requirement.achievedByPlayer(player);
    }
    
    @Override
    public String getToolTip(final PlayerData data) {
        try {
            return String.format(this.internalToolTip(), (data == null || !data.requirementAchieved(this)) ? TextFormatting.RED : TextFormatting.GREEN);
        }
        catch (IllegalArgumentException e) {
            String parentToolTip = this.requirement.getToolTip(data);
            if (parentToolTip == null) {
                return "";
            }
            if (parentToolTip.startsWith(TextFormatting.GRAY + " - ")) {
                parentToolTip = parentToolTip.replaceFirst(TextFormatting.GRAY + " - ", "");
            }
            final char colorCode = '§';
            String start = TextFormatting.GRAY + " - ";
            if (parentToolTip.length() > 2 && parentToolTip.startsWith(Character.toString(colorCode))) {
                start += parentToolTip.substring(0, 2);
                parentToolTip = parentToolTip.substring(2);
            }
            start += '!';
            final StringBuilder tooltip = new StringBuilder(start);
            final char[] chars = parentToolTip.toCharArray();
            char lastChar = '!';
            final char red = 'c';
            final char green = 'a';
            for (final char c : chars) {
                if (lastChar == colorCode && (c == red || c == green)) {
                    if (c == red) {
                        tooltip.append(green);
                    }
                    else {
                        tooltip.append(red);
                    }
                }
                else {
                    tooltip.append(c);
                }
                lastChar = c;
            }
            return tooltip.toString();
        }
    }
    
    public Requirement getRequirement() {
        return this.requirement;
    }
    
    @Override
    public RequirementComparision matches(final Requirement o) {
        if (!(o instanceof NOTRequirement)) {
            return RequirementComparision.NOT_EQUAL;
        }
        final RequirementComparision match = this.requirement.matches(((NOTRequirement)o).requirement);
        switch (match) {
            case GREATER_THAN: {
                return RequirementComparision.LESS_THAN;
            }
            case LESS_THAN: {
                return RequirementComparision.GREATER_THAN;
            }
            default: {
                return match;
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof NOTRequirement && this.requirement.equals(((NOTRequirement)o).requirement));
    }
    
    @Override
    public int hashCode() {
        return this.requirement.hashCode();
    }
    
    @Nonnull
    @Override
    public List<Class<? extends Requirement>> getInternalTypes() {
        if (this.requirement instanceof OuterRequirement) {
            return ((OuterRequirement)this.requirement).getInternalTypes();
        }
        return Collections.singletonList(this.requirement.getClass());
    }
    
    @Override
    public boolean isCacheable() {
        return this.requirement.isCacheable();
    }
}
