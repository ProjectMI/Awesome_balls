//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.agility;

import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitHillWalker extends Trait
{
    public TraitHillWalker() {
        super(new ResourceLocation("reskillable", "hillwalker"), 2, 2, new ResourceLocation("reskillable", "agility"), 8, new String[] { "reskillable:agility|32" });
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        PlayerData data = null;
        if (player != null) {
            data = PlayerDataHandler.get(player);
        }
        if (data != null && data.getSkillInfo(this.getParentSkill()).isUnlocked(this)) {
            if (player.isSneaking()) {
                player.stepHeight = 0.9f;
            }
            else {
                player.stepHeight = 1.0625f;
            }
        }
    }
}
