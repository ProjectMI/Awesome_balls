// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.cap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IBaublesItemHandler extends IItemHandlerModifiable
{
    boolean isItemValidForSlot(final int p0, final ItemStack p1, final EntityLivingBase p2);
    
    boolean isEventBlocked();
    
    void setEventBlock(final boolean p0);
    
    boolean isChanged(final int p0);
    
    void setChanged(final int p0, final boolean p1);
    
    void setPlayer(final EntityLivingBase p0);
}
