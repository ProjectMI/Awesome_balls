// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.skilllevel;

import codersafterdark.reskillable.api.skill.Skill;
import net.minecraft.advancements.PlayerAdvancements;
import codersafterdark.reskillable.advancement.CriterionListeners;

public class SkillLevelListeners extends CriterionListeners<SkillLevelCriterionInstance>
{
    public SkillLevelListeners(final PlayerAdvancements playerAdvancements) {
        super(playerAdvancements);
    }
    
    public void trigger(final Skill skill, final int level) {
        this.trigger(instance -> instance.test(skill, level));
    }
}
