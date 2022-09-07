// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.trait;

import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;

public class UnlockUnlockableCriterionInstance extends AbstractCriterionInstance
{
    private final Unlockable unlockable;
    
    public UnlockUnlockableCriterionInstance(final Unlockable unlockable) {
        super(new ResourceLocation("reskillable", "unlockable"));
        this.unlockable = unlockable;
    }
    
    public boolean test(final Unlockable other) {
        return this.unlockable == other;
    }
}
