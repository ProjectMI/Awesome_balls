//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.advancement;

import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.advancements.PlayerAdvancements;
import java.util.function.Function;
import net.minecraft.util.ResourceLocation;
import mcp.MethodsReturnNonnullByDefault;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.ICriterionInstance;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class CriterionTrigger<T extends CriterionListeners<U>, U extends ICriterionInstance> implements ICriterionTrigger<U>
{
    private final ResourceLocation id;
    private final Function<PlayerAdvancements, T> createNew;
    private final Map<PlayerAdvancements, T> listeners;
    
    protected CriterionTrigger(final ResourceLocation id, final Function<PlayerAdvancements, T> createNew) {
        this.listeners = (Map<PlayerAdvancements, T>)Maps.newHashMap();
        this.id = id;
        this.createNew = createNew;
    }
    
    public ResourceLocation getId() {
        return this.id;
    }
    
    public void addListener(final PlayerAdvancements playerAdvancements, final ICriterionTrigger.Listener<U> listener) {
        T listeners = this.listeners.get(playerAdvancements);
        if (listeners == null) {
            listeners = this.createNew.apply(playerAdvancements);
            this.listeners.put(playerAdvancements, listeners);
        }
        listeners.add(listener);
    }
    
    public void removeListener(final PlayerAdvancements playerAdvancements, final ICriterionTrigger.Listener<U> listener) {
        final T listeners = this.listeners.get(playerAdvancements);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancements);
            }
        }
    }
    
    @Nullable
    public T getListeners(final PlayerAdvancements playerAdvancements) {
        return this.listeners.get(playerAdvancements);
    }
    
    public void removeAllListeners(final PlayerAdvancements playerAdvancements) {
        this.listeners.remove(playerAdvancements);
    }
}
