// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement.trait;

import codersafterdark.reskillable.api.unlockable.Unlockable;
import net.minecraft.advancements.PlayerAdvancements;
import codersafterdark.reskillable.advancement.CriterionListeners;

public class UnlockUnlockableListeners extends CriterionListeners<UnlockUnlockableCriterionInstance>
{
    public UnlockUnlockableListeners(final PlayerAdvancements playerAdvancements) {
        super(playerAdvancements);
    }
    
    public void trigger(final Unlockable unlockable) {
        this.trigger(instance -> instance.test(unlockable));
    }
}
