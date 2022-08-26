// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE;
    
    public static void init() {
        PacketHandler.INSTANCE.registerMessage((Class)PacketOpenBaublesInventory.class, (Class)PacketOpenBaublesInventory.class, 0, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketOpenNormalInventory.class, (Class)PacketOpenNormalInventory.class, 1, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSync.Handler.class, (Class)PacketSync.class, 2, Side.CLIENT);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("baubles".toLowerCase());
    }
}
