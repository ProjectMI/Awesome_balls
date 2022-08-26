//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.event;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.IBauble;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraft.util.ResourceLocation;

public class EventHandlerItem
{
    private static ResourceLocation capabilityResourceLocation;
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void itemCapabilityAttach(final AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStack stack = (ItemStack)event.getObject();
        if (stack.isEmpty() || !(stack.getItem() instanceof IBauble) || stack.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null) || event.getCapabilities().values().stream().anyMatch(c -> c.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null))) {
            return;
        }
        event.addCapability(EventHandlerItem.capabilityResourceLocation, (ICapabilityProvider)new ICapabilityProvider() {
            public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
                return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
            }
            
            @Nullable
            public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
                return (T)((capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE) ? BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast((Object)stack.getItem()) : null);
            }
        });
    }
    
    static {
        EventHandlerItem.capabilityResourceLocation = new ResourceLocation("baubles", "bauble_cap");
    }
}
