package lazy.baubles.container.slots;

import javax.annotation.Nullable;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class OffHandSlot extends Slot
{
    public OffHandSlot(final Container inventoryIn, final int index, final int xPosition, final int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    public boolean m_5857_(final ItemStack stack) {
        return super.m_5857_(stack);
    }
    
    @Nullable
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return (Pair<ResourceLocation, ResourceLocation>)Pair.of((Object)InventoryMenu.f_39692_, (Object)InventoryMenu.f_39697_);
    }
}
