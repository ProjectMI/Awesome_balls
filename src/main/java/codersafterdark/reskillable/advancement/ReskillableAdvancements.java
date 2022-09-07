//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import codersafterdark.reskillable.advancement.trait.UnlockUnlockableTrigger;
import codersafterdark.reskillable.advancement.skilllevel.SkillLevelTrigger;

public class ReskillableAdvancements
{
    public static final SkillLevelTrigger SKILL_LEVEL;
    public static final UnlockUnlockableTrigger UNLOCK_UNLOCKABLE;
    
    public static void preInit() {
        CriteriaTriggers.register((ICriterionTrigger)ReskillableAdvancements.SKILL_LEVEL);
        CriteriaTriggers.register((ICriterionTrigger)ReskillableAdvancements.UNLOCK_UNLOCKABLE);
    }
    
    static {
        SKILL_LEVEL = new SkillLevelTrigger();
        UNLOCK_UNLOCKABLE = new UnlockUnlockableTrigger();
    }
}
