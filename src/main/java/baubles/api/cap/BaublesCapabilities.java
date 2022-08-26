// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.cap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import baubles.api.IBauble;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.Capability;

public class BaublesCapabilities
{
    @CapabilityInject(IBaublesItemHandler.class)
    public static final Capability<IBaublesItemHandler> CAPABILITY_BAUBLES;
    @CapabilityInject(IBauble.class)
    public static final Capability<IBauble> CAPABILITY_ITEM_BAUBLE;
    
    static {
        CAPABILITY_BAUBLES = null;
        CAPABILITY_ITEM_BAUBLE = null;
    }
    
    public static class CapabilityBaubles<T extends IBaublesItemHandler> implements Capability.IStorage<IBaublesItemHandler>
    {
        public NBTBase writeNBT(final Capability<IBaublesItemHandler> capability, final IBaublesItemHandler instance, final EnumFacing side) {
            return null;
        }
        
        public void readNBT(final Capability<IBaublesItemHandler> capability, final IBaublesItemHandler instance, final EnumFacing side, final NBTBase nbt) {
        }
    }
    
    public static class CapabilityItemBaubleStorage implements Capability.IStorage<IBauble>
    {
        public NBTBase writeNBT(final Capability<IBauble> capability, final IBauble instance, final EnumFacing side) {
            return null;
        }
        
        public void readNBT(final Capability<IBauble> capability, final IBauble instance, final EnumFacing side, final NBTBase nbt) {
        }
    }
}
