// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.magic;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitSafePort extends Trait
{
    public TraitSafePort() {
        super(new ResourceLocation("reskillable", "safe_port"), 1, 1, new ResourceLocation("reskillable", "magic"), 6, new String[] { "reskillable:magic|20", "reskillable:agility|16", "reskillable:defense|16" });
    }
    
    @Override
    public void onEnderTeleport(final EnderTeleportEvent event) {
        if (!event.isCanceled()) {
            event.setAttackDamage(0.0f);
        }
    }
}
