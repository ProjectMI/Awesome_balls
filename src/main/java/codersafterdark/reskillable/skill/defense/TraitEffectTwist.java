//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.defense;

import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.potion.PotionEffect;
import java.util.List;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.init.MobEffects;
import java.util.HashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Potion;
import java.util.Map;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitEffectTwist extends Trait
{
    private Map<Potion, Potion> badPotions;
    
    public TraitEffectTwist() {
        super(new ResourceLocation("reskillable", "effect_twist"), 3, 1, new ResourceLocation("reskillable", "defense"), 8, new String[] { "reskillable:defense|20", "reskillable:attack|16", "reskillable:magic|16" });
        (this.badPotions = new HashMap<Potion, Potion>()).put(MobEffects.SPEED, MobEffects.SLOWNESS);
        this.badPotions.put(MobEffects.HASTE, MobEffects.MINING_FATIGUE);
        this.badPotions.put(MobEffects.STRENGTH, MobEffects.WEAKNESS);
        this.badPotions.put(MobEffects.REGENERATION, MobEffects.POISON);
        this.badPotions.put(MobEffects.NIGHT_VISION, MobEffects.BLINDNESS);
        this.badPotions.put(MobEffects.LUCK, MobEffects.UNLUCK);
    }
    
    @Override
    public void onHurt(final LivingHurtEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final Entity src = event.getSource().getTrueSource();
        if (src instanceof EntityLivingBase && src instanceof IMob && src.world.rand.nextBoolean()) {
            final List<PotionEffect> effects = event.getEntityLiving().getActivePotionEffects().stream().filter(p -> this.badPotions.containsKey(p.getPotion())).collect((Collector<? super Object, ?, List<PotionEffect>>)Collectors.toList());
            if (effects.size() > 0) {
                final PotionEffect target = effects.get(src.world.rand.nextInt(effects.size()));
                final PotionEffect newEff = new PotionEffect((Potion)this.badPotions.get(target.getPotion()), 80 + src.world.rand.nextInt(60), 0);
                ((EntityLivingBase)src).addPotionEffect(newEff);
            }
        }
    }
}
