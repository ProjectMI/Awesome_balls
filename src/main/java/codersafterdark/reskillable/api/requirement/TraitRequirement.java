//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.requirement;

import java.util.Objects;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import codersafterdark.reskillable.api.unlockable.Unlockable;

public class TraitRequirement extends Requirement
{
    private Unlockable unlockable;
    
    public TraitRequirement(final Unlockable unlockable) {
        this.unlockable = unlockable;
        this.tooltip = TextFormatting.GRAY + " - " + TextFormatting.LIGHT_PURPLE + new TextComponentTranslation("reskillable.requirements.format.trait", new Object[] { "%s", this.unlockable.getName() }).getUnformattedComponentText();
    }
    
    @Override
    public boolean achievedByPlayer(final EntityPlayer entityPlayer) {
        return PlayerDataHandler.get(entityPlayer).getSkillInfo(this.unlockable.getParentSkill()).isUnlocked(this.unlockable);
    }
    
    public Skill getSkill() {
        return this.unlockable.getParentSkill();
    }
    
    public Unlockable getUnlockable() {
        return this.unlockable;
    }
    
    @Override
    public RequirementComparision matches(final Requirement other) {
        return (other instanceof TraitRequirement) ? (this.unlockable.getKey().equals(((TraitRequirement)other).unlockable.getKey()) ? RequirementComparision.EQUAL_TO : RequirementComparision.NOT_EQUAL) : RequirementComparision.NOT_EQUAL;
    }
    
    @Override
    public boolean isEnabled() {
        return this.unlockable != null && this.unlockable.isEnabled();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof TraitRequirement && this.unlockable.equals(((TraitRequirement)o).unlockable));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.unlockable);
    }
}
