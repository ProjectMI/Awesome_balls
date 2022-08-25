package lazy.baubles.client.event;

import lazy.baubles.api.bauble.BaubleType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import lazy.baubles.api.bauble.IBauble;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "baubles", value = { Dist.CLIENT })
public class RingItemTooltip
{
    @SubscribeEvent
    public static void tooltipEvent(final ItemTooltipEvent event) {
        if (!event.getItemStack().m_41619_() && event.getItemStack().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).isPresent()) {
            event.getItemStack().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).ifPresent(bauble -> {
                final BaubleType bt = bauble.getBaubleType(event.getItemStack());
                final TranslatableComponent text = new TranslatableComponent(invokedynamic(makeConcatWithConstants:(Llazy/baubles/api/bauble/BaubleType;)Ljava/lang/String;, bt));
                text.m_130940_(ChatFormatting.GOLD);
                event.getToolTip().add(text);
            });
        }
    }
}
