//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement.logic;

import codersafterdark.reskillable.api.requirement.RequirementComparision;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.requirement.Requirement;

public class TrueRequirement extends Requirement
{
    public TrueRequirement() {
        this.tooltip = TextFormatting.GREEN + new TextComponentTranslation("reskillable.requirements.format.unobtainable", new Object[0]).getUnformattedComponentText();
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer entityPlayerMP) {
        return true;
    }
    
    @Override
    public String getToolTip(final PlayerData data) {
        return this.tooltip;
    }
    
    @Override
    public RequirementComparision matches(final Requirement other) {
        return (other instanceof TrueRequirement) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof TrueRequirement;
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
}
