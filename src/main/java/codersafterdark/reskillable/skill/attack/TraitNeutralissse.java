//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.attack;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import codersafterdark.reskillable.lib.LibObfuscation;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitNeutralissse extends Trait
{
    private static final String TAG_DEFUSED = "skillable:defuse";
    
    public TraitNeutralissse() {
        super(new ResourceLocation("reskillable", "neutralissse"), 1, 2, new ResourceLocation("reskillable", "attack"), 10, new String[] { "reskillable:attack|24", "reskillable:agility|8" });
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onAttackMob(final LivingHurtEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getEntity() instanceof EntityCreeper) {
            final EntityCreeper creeper = (EntityCreeper)event.getEntity();
            final int time = (int)ReflectionHelper.getPrivateValue((Class)EntityCreeper.class, (Object)creeper, LibObfuscation.TIME_SINCE_IGNITED);
            if (time < 5) {
                creeper.getEntityData().setInteger("skillable:defuse", 40);
            }
        }
    }
    
    @SubscribeEvent
    public void entityTick(final LivingEvent.LivingUpdateEvent event) {
        if (!event.isCanceled() && event.getEntity() instanceof EntityCreeper) {
            final EntityCreeper creeper = (EntityCreeper)event.getEntity();
            final int defuseTime = creeper.getEntityData().getInteger("skillable:defuse");
            if (defuseTime > 0) {
                creeper.getEntityData().setInteger("skillable:defuse", defuseTime - 1);
                ReflectionHelper.setPrivateValue((Class)EntityCreeper.class, (Object)creeper, (Object)6, LibObfuscation.TIME_SINCE_IGNITED);
            }
        }
    }
}
