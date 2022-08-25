package lazy.baubles.capability;

import net.minecraft.nbt.Tag;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraft.core.Direction;
import javax.annotation.Nonnull;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class BaublesContainerProvider implements INBTSerializable<CompoundTag>, ICapabilityProvider
{
    private final BaublesContainer inner;
    private final LazyOptional<IBaublesItemHandler> baublesHandlerCap;
    
    public BaublesContainerProvider(final Player player) {
        this.inner = new BaublesContainer((LivingEntity)player);
        this.baublesHandlerCap = (LazyOptional<IBaublesItemHandler>)LazyOptional.of(() -> this.inner);
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final Direction facing) {
        return (LazyOptional<T>)CapabilityBaubles.BAUBLES.orEmpty((Capability)capability, (LazyOptional)this.baublesHandlerCap);
    }
    
    public CompoundTag serializeNBT() {
        return this.inner.serializeNBT();
    }
    
    public void deserializeNBT(final CompoundTag nbt) {
        this.inner.deserializeNBT(nbt);
    }
}
