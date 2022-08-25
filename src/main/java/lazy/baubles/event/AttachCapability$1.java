package lazy.baubles.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lazy.baubles.api.bauble.IBauble;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

class AttachCapability$1 implements ICapabilityProvider {
    private final LazyOptional<IBauble> opt;

    AttachCapability$1(IBauble var1) {
        this.val$bauble = var1;
        this.opt = LazyOptional.of(() -> {
            return bauble;
        });
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityBaubles.ITEM_BAUBLE.orEmpty(cap, this.opt);
    }
}
