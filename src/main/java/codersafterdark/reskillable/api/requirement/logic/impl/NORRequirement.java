//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic.impl;

import codersafterdark.reskillable.api.requirement.RequirementComparision;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.requirement.Requirement;
import codersafterdark.reskillable.api.requirement.logic.DoubleRequirement;

public class NORRequirement extends DoubleRequirement
{
    public NORRequirement(final Requirement left, final Requirement right) {
        super(left, right);
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer player) {
        return !this.leftAchieved(player) && !this.rightAchieved(player);
    }
    
    @Override
    protected String getFormat() {
        return new TextComponentTranslation("reskillable.requirements.format.nor", new Object[0]).getUnformattedComponentText();
    }
    
    @Override
    public RequirementComparision matches(final Requirement o) {
        if (o instanceof ORRequirement) {
            final ORRequirement other = (ORRequirement)o;
            final RequirementComparision left = this.getLeft().matches(other.getLeft());
            final RequirementComparision right = this.getRight().matches(other.getRight());
            final boolean same = left.equals(right);
            if (same && left.equals(RequirementComparision.EQUAL_TO)) {
                return RequirementComparision.EQUAL_TO;
            }
            final RequirementComparision leftAlt = this.getLeft().matches(other.getRight());
            final RequirementComparision rightAlt = this.getRight().matches(other.getLeft());
            final boolean altSame = leftAlt.equals(rightAlt);
            if (altSame && leftAlt.equals(RequirementComparision.EQUAL_TO)) {
                return RequirementComparision.EQUAL_TO;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }
}
