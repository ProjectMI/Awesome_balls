// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.cap;

import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;
import baubles.api.IBauble;

public class BaubleItem implements IBauble
{
    private BaubleType baubleType;
    
    public BaubleItem(final BaubleType type) {
        this.baubleType = type;
    }
    
    @Override
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return this.baubleType;
    }
}
