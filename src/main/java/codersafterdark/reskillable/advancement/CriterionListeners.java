//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import com.google.common.collect.Sets;
import net.minecraft.advancements.ICriterionTrigger;
import java.util.Set;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.ICriterionInstance;

public class CriterionListeners<T extends ICriterionInstance>
{
    private final PlayerAdvancements playerAdvancements;
    private final Set<ICriterionTrigger.Listener<T>> listeners;
    
    public CriterionListeners(final PlayerAdvancements playerAdvancements) {
        this.listeners = (Set<ICriterionTrigger.Listener<T>>)Sets.newHashSet();
        this.playerAdvancements = playerAdvancements;
    }
    
    public boolean isEmpty() {
        return this.listeners.isEmpty();
    }
    
    public void add(final ICriterionTrigger.Listener<T> listener) {
        this.listeners.add(listener);
    }
    
    public void remove(final ICriterionTrigger.Listener<T> listener) {
        this.listeners.remove(listener);
    }
    
    public void trigger(final Predicate<T> test) {
        final List<ICriterionTrigger.Listener<T>> toGrant = new ArrayList<ICriterionTrigger.Listener<T>>();
        for (final ICriterionTrigger.Listener<T> listener2 : this.listeners) {
            if (test.test((T)listener2.getCriterionInstance())) {
                toGrant.add(listener2);
            }
        }
        toGrant.forEach(listener -> listener.grantCriterion(this.playerAdvancements));
    }
}
