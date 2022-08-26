// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.cap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class BaublesContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider
{
    private final BaublesContainer container;
    
    public BaublesContainerProvider(final BaublesContainer container) {
        this.container = container;
    }
    
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == BaublesCapabilities.CAPABILITY_BAUBLES;
    }
    
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability == BaublesCapabilities.CAPABILITY_BAUBLES) {
            return (T)this.container;
        }
        return null;
    }
    
    public NBTTagCompound serializeNBT() {
        return this.container.serializeNBT();
    }
    
    public void deserializeNBT(final NBTTagCompound nbt) {
        this.container.deserializeNBT(nbt);
    }
}
