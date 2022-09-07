//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.agility;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitRoadWalk extends Trait
{
    public TraitRoadWalk() {
        super(new ResourceLocation("reskillable", "roadwalk"), 1, 1, new ResourceLocation("reskillable", "agility"), 6, new String[] { "reskillable:agility|12", "reskillable:building|8" });
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (event.player.world.getBlockState(event.player.getPosition().down()).getBlock() == Blocks.GRASS_PATH && event.player.onGround && event.player.moveForward > 0.0f) {
            event.player.moveRelative(0.0f, 0.0f, 1.0f, 0.05f);
        }
    }
}
