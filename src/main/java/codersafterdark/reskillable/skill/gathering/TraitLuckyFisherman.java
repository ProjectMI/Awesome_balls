//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.gathering;

import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitLuckyFisherman extends Trait
{
    public TraitLuckyFisherman() {
        super(new ResourceLocation("reskillable", "lucky_fisherman"), 3, 2, new ResourceLocation("reskillable", "gathering"), 6, new String[] { "reskillable:gathering|12", "reskillable:magic|4" });
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (event.player.fishEntity != null) {
            event.player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, 0, true, true));
        }
    }
}
