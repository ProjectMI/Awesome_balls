// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.skilllevel;

import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;

public class SkillLevelCriterionInstance extends AbstractCriterionInstance
{
    private final Skill skill;
    private final int level;
    
    public SkillLevelCriterionInstance(@Nullable final Skill skill, final int level) {
        super(new ResourceLocation("reskillable", "skill_level"));
        this.skill = skill;
        this.level = level;
    }
    
    public boolean test(final Skill skill, final int level) {
        return (this.skill == null || this.skill == skill) && level >= this.level;
    }
}
