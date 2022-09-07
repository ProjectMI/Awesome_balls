//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.defense;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitUndershirt extends Trait
{
    private static final String TAG_COOLDOWN = "skillable:UndershirtCD";
    
    public TraitUndershirt() {
        super(new ResourceLocation("reskillable", "undershirt"), 1, 2, new ResourceLocation("reskillable", "defense"), 6, new String[] { "reskillable:defense|12", "reskillable:agility|4" });
    }
    
    @Override
    public void onHurt(final LivingHurtEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final EntityLivingBase e = event.getEntityLiving();
        if (e.getEntityData().getInteger("skillable:UndershirtCD") == 0 && e.getHealth() >= 6.0f && event.getAmount() >= e.getHealth() && !event.getSource().isUnblockable()) {
            event.setAmount(e.getHealth() - 1.0f);
            e.getEntityData().setInteger("skillable:UndershirtCD", 200);
        }
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final int cd = event.player.getEntityData().getInteger("skillable:UndershirtCD");
        if (cd > 0) {
            event.player.getEntityData().setInteger("skillable:UndershirtCD", cd - 1);
        }
    }
}
