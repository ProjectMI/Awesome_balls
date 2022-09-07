// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api;

import net.minecraftforge.fml.common.registry.GameRegistry;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.skill.Skill;
import net.minecraftforge.registries.IForgeRegistry;

public class ReskillableRegistries
{
    public static final IForgeRegistry<Skill> SKILLS;
    public static final IForgeRegistry<Unlockable> UNLOCKABLES;
    
    static {
        SKILLS = GameRegistry.findRegistry((Class)Skill.class);
        UNLOCKABLES = GameRegistry.findRegistry((Class)Unlockable.class);
    }
}
