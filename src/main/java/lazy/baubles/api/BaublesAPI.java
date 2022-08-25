package lazy.baubles.api;

import lazy.baubles.api.bauble.BaubleType;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import lazy.baubles.api.cap.CapabilityBaubles;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;

public class BaublesAPI {
    public static final String MOD_ID = "baubles";

    public BaublesAPI() {
    }

    public static LazyOptional<IBaublesItemHandler> getBaublesHandler(Player player) {
        return player.getCapability(CapabilityBaubles.BAUBLES);
    }

    public static int isBaubleEquipped(Player player, Item bauble) {
        return (Integer)getBaublesHandler(player).map((handler) -> {
            for(int a = 0; a < handler.getSlots(); ++a) {
                if (!handler.getStackInSlot(a).m_41619_() && handler.getStackInSlot(a).m_41720_() == bauble) {
                    return a;
                }
            }

            return -1;
        }).orElse(-1);
    }

    public static int getEmptySlotForBaubleType(Player playerEntity, BaubleType type) {
        IBaublesItemHandler itemHandler = (IBaublesItemHandler)getBaublesHandler(playerEntity).orElseThrow(NullPointerException::new);
        int[] var3 = type.getValidSlots();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int i = var3[var5];
            if (itemHandler.getStackInSlot(i).m_41619_()) {
                return i;
            }
        }

        return -1;
    }
}
