// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common;

import org.apache.logging.log4j.LogManager;
import net.minecraft.command.ICommand;
import baubles.common.event.CommandBaubles;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import baubles.common.network.PacketHandler;
import baubles.api.cap.BaubleItem;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesContainer;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import java.io.File;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "baubles", name = "Baubles", version = "1.5.2", guiFactory = "baubles.client.gui.BaublesGuiFactory", dependencies = "required-after:forge@[14.21.0.2348,);", acceptedMinecraftVersions = "[1.12]")
public class Baubles
{
    public static final String MODID = "baubles";
    public static final String MODNAME = "Baubles";
    public static final String VERSION = "1.5.2";
    @SidedProxy(clientSide = "baubles.client.ClientProxy", serverSide = "baubles.common.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("baubles")
    public static Baubles instance;
    public File modDir;
    public static final Logger log;
    public static final int GUI = 0;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        this.modDir = event.getModConfigurationDirectory();
        try {
            Config.initialize(event.getSuggestedConfigurationFile());
        }
        catch (Exception e) {
            Baubles.log.error("BAUBLES has a problem loading it's configuration");
        }
        finally {
            if (Config.config != null) {
                Config.save();
            }
        }
        CapabilityManager.INSTANCE.register((Class)IBaublesItemHandler.class, (Capability.IStorage)new BaublesCapabilities.CapabilityBaubles(), (Class)BaublesContainer.class);
        CapabilityManager.INSTANCE.register((Class)IBauble.class, (Capability.IStorage)new BaublesCapabilities.CapabilityItemBaubleStorage(), () -> new BaubleItem(BaubleType.TRINKET));
        Baubles.proxy.registerEventHandlers();
        PacketHandler.init();
        Config.save();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)Baubles.instance, (IGuiHandler)Baubles.proxy);
        Baubles.proxy.init();
    }
    
    @Mod.EventHandler
    public void serverLoad(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new CommandBaubles());
    }
    
    static {
        log = LogManager.getLogger("baubles".toUpperCase());
    }
}
