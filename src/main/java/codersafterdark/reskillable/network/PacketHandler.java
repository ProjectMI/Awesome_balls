// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.network;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler
{
    public static SimpleNetworkWrapper INSTANCE;
    public static int ID;
    
    public static void preInit() {
        PacketHandler.INSTANCE.registerMessage((Class)MessageDataSync.class, (Class)MessageDataSync.class, PacketHandler.ID++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)MessageDodge.class, (Class)MessageDodge.class, PacketHandler.ID++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)MessageLevelUp.class, (Class)MessageLevelUp.class, PacketHandler.ID++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)MessageLockedItem.class, (Class)MessageLockedItem.class, PacketHandler.ID++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)MessageUnlockUnlockable.class, (Class)MessageUnlockUnlockable.class, PacketHandler.ID++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)InvalidateRequirementPacket.class, (Class)InvalidateRequirementPacket.class, PacketHandler.ID++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)InvalidateRequirementPacket.class, (Class)InvalidateRequirementPacket.class, PacketHandler.ID++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)UnlockableToastPacket.class, (Class)UnlockableToastPacket.class, PacketHandler.ID++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)SkillToastPacket.class, (Class)SkillToastPacket.class, PacketHandler.ID++, Side.CLIENT);
    }
    
    static {
        PacketHandler.INSTANCE = new SimpleNetworkWrapper("reskillable");
    }
}
