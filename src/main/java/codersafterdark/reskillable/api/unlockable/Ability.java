// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.unlockable;

import net.minecraft.util.ResourceLocation;

public abstract class Ability extends Unlockable
{
    public Ability(final ResourceLocation name, final int x, final int y, final ResourceLocation skillName, final int cost, final String... requirements) {
        super(name, x, y, skillName, cost, requirements);
    }
    
    @Override
    public boolean hasSpikes() {
        return true;
    }
}
