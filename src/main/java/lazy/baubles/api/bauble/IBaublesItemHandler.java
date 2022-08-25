package lazy.baubles.api.bauble;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IBaublesItemHandler extends IItemHandlerModifiable {
    boolean isItemValidForSlot(int var1, ItemStack var2);

    boolean isEventBlocked();

    void setEventBlock(boolean var1);

    void tick();
}
