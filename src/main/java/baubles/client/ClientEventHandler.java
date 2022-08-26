//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client;

import baubles.api.BaubleType;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import baubles.api.IBauble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.PacketHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import baubles.common.items.ItemRing;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class ClientEventHandler
{
    @SubscribeEvent
    public void registerItemModels(final ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(ItemRing.RING, 0, new ModelResourceLocation("baubles:ring", "inventory"));
    }
    
    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START && ClientProxy.KEY_BAUBLES.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketOpenBaublesInventory());
        }
    }
    
    @SubscribeEvent
    public void tooltipEvent(final ItemTooltipEvent event) {
        if (!event.getItemStack().isEmpty() && event.getItemStack().hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
            final IBauble bauble = (IBauble)event.getItemStack().getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
            final BaubleType bt = bauble.getBaubleType(event.getItemStack());
            event.getToolTip().add(TextFormatting.GOLD + I18n.format("name." + bt, new Object[0]));
        }
    }
}
