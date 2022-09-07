//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.base;

import net.minecraft.client.multiplayer.ClientAdvancementManager;
import java.util.Map;
import net.minecraft.client.resources.I18n;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.function.Function;
import net.minecraft.client.network.NetHandlerPlayClient;
import java.util.Optional;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.io.File;
import net.minecraft.advancements.AdvancementManager;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.base.ToolTipHandler;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import net.minecraftforge.common.MinecraftForge;
import codersafterdark.reskillable.client.gui.handler.KeyBindings;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import codersafterdark.reskillable.base.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
        KeyBindings.init();
        MinecraftForge.EVENT_BUS.register((Object)ClientTickHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)InventoryTabHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)HUDHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)ToolTipHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)this.getClass());
        MinecraftForge.EVENT_BUS.register((Object)new KeyBindings());
    }
    
    @Override
    public EntityPlayer getClientPlayer() {
        return (EntityPlayer)Minecraft.getMinecraft().player;
    }
    
    @SubscribeEvent
    public static void connect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        new AdvancementManager((File)null);
    }
    
    @Nullable
    @Override
    public AdvancementProgress getPlayerAdvancementProgress(final EntityPlayer entityPlayer, final Advancement advancement) {
        return Optional.ofNullable(Minecraft.getMinecraft().getConnection()).map((Function<? super NetHandlerPlayClient, ?>)NetHandlerPlayClient::getAdvancementManager).map(advancementManager -> advancementManager.advancementToProgress).map(advancementAdvancementProgressMap -> advancementAdvancementProgressMap.get(advancement)).orElseGet(AdvancementProgress::new);
    }
    
    @Override
    public String getLocalizedString(final String string) {
        return I18n.format(string, new Object[0]);
    }
}
