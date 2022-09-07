//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.building;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitPerfectRecover extends Trait
{
    private Item glowstone;
    private Item lantern;
    
    public TraitPerfectRecover() {
        super(new ResourceLocation("reskillable", "perfect_recover"), 1, 1, new ResourceLocation("reskillable", "building"), 4, new String[] { "reskillable:building|8", "reskillable:gathering|4", "reskillable:mining|6" });
    }
    
    @Override
    public void onBlockDrops(final BlockEvent.HarvestDropsEvent event) {
        if (event.getState().getBlock() == Blocks.GLOWSTONE) {
            if (!(boolean)event.getDrops().stream().map(stack -> stack.getItem() == this.getGlowstone()).reduce((Object)false, (a, b) -> a || b)) {
                event.getDrops().removeIf(s -> s.getItem() == Items.GLOWSTONE_DUST);
                event.getDrops().add(new ItemStack(Items.GLOWSTONE_DUST, 4));
            }
        }
        else if (event.getState().getBlock() == Blocks.SEA_LANTERN && !(boolean)event.getDrops().stream().map(stack -> stack.getItem() == this.getSeaLantern()).reduce((Object)false, (a, b) -> a || b)) {
            event.getDrops().removeIf(s -> s.getItem() == Items.PRISMARINE_CRYSTALS);
            event.getDrops().add(new ItemStack(Items.PRISMARINE_CRYSTALS, 5));
            event.getDrops().add(new ItemStack(Items.PRISMARINE_SHARD, 4));
        }
    }
    
    private Item getGlowstone() {
        if (this.glowstone == null) {
            this.glowstone = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:glowstone"));
        }
        return this.glowstone;
    }
    
    private Item getSeaLantern() {
        if (this.lantern == null) {
            this.lantern = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:sea_lantern"));
        }
        return this.lantern;
    }
}
