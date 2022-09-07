//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import java.util.Objects;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.skill.Skill;

public class SkillRequirement extends Requirement
{
    private final Skill skill;
    private final int level;
    
    public SkillRequirement(final Skill skill, final int level) {
        this.skill = skill;
        this.level = level;
        this.tooltip = TextFormatting.GRAY + " - " + new TextComponentTranslation("reskillable.requirements.format.skill", new Object[] { TextFormatting.DARK_AQUA, skill.getName(), "%s", level }).getUnformattedComponentText();
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer entityPlayer) {
        return PlayerDataHandler.get(entityPlayer).getSkillInfo(this.skill).getLevel() >= this.level;
    }
    
    public Skill getSkill() {
        return this.skill;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    @Override
    public RequirementComparision matches(final Requirement other) {
        if (other instanceof SkillRequirement) {
            final SkillRequirement skillRequirement = (SkillRequirement)other;
            if (this.skill == null || skillRequirement.skill == null) {
                return RequirementComparision.NOT_EQUAL;
            }
            if (this.skill.getKey().equals(skillRequirement.skill.getKey())) {
                if (this.level == skillRequirement.level) {
                    return RequirementComparision.EQUAL_TO;
                }
                return (this.level > skillRequirement.level) ? RequirementComparision.GREATER_THAN : RequirementComparision.LESS_THAN;
            }
        }
        return RequirementComparision.NOT_EQUAL;
    }
    
    @Override
    public boolean isEnabled() {
        return this.skill != null && this.skill.isEnabled();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SkillRequirement) {
            final SkillRequirement sReq = (SkillRequirement)o;
            return this.skill.equals(sReq.skill) && this.level == sReq.level;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.skill, this.level);
    }
}
