//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.attack;

import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitBattleSpirit extends Trait
{
    public TraitBattleSpirit() {
        super(new ResourceLocation("reskillable", "battle_spirit"), 3, 2, new ResourceLocation("reskillable", "attack"), 6, new String[] { "reskillable:attack|16", "reskillable:defense|16", "reskillable:agility|12" });
    }
    
    @Override
    public void onKillMob(final LivingDeathEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof IMob && event.getSource().getTrueSource() instanceof EntityPlayer) {
            ((EntityPlayer)event.getSource().getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 120, 0));
        }
    }
}
