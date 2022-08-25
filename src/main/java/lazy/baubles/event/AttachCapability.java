package lazy.baubles.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.item.Item;
import lazy.baubles.api.cap.CapabilityBaubles;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import javax.annotation.Nonnull;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = "baubles")
public class AttachCapability
{
    private static final ResourceLocation CAP;
    
    @SubscribeEvent
    public static void attachCaps(final AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStack stack = (ItemStack)event.getObject();
        final Item 41720_ = stack.m_41720_();
        if (41720_ instanceof IBauble) {
            final IBauble bauble = (IBauble)41720_;
            event.addCapability(AttachCapability.CAP, (ICapabilityProvider)new ICapabilityProvider() {
                private final LazyOptional<IBauble> opt = LazyOptional.of(() -> bauble);
                
                @Nonnull
                public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
                    return (LazyOptional<T>)CapabilityBaubles.ITEM_BAUBLE.orEmpty((Capability)cap, (LazyOptional)this.opt);
                }
            });
        }
    }
    
    static {
        CAP = new ResourceLocation("baubles", "bauble_cap");
    }
}
