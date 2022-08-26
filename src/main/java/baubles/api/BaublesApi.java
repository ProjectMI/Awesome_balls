//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api;

import net.minecraft.item.Item;
import baubles.api.inv.BaublesInventoryWrapper;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;

public class BaublesApi
{
    public static IBaublesItemHandler getBaublesHandler(final EntityPlayer player) {
        final IBaublesItemHandler handler = (IBaublesItemHandler)player.getCapability((Capability)BaublesCapabilities.CAPABILITY_BAUBLES, (EnumFacing)null);
        handler.setPlayer((EntityLivingBase)player);
        return handler;
    }
    
    @Deprecated
    public static IInventory getBaubles(final EntityPlayer player) {
        final IBaublesItemHandler handler = (IBaublesItemHandler)player.getCapability((Capability)BaublesCapabilities.CAPABILITY_BAUBLES, (EnumFacing)null);
        handler.setPlayer((EntityLivingBase)player);
        return (IInventory)new BaublesInventoryWrapper(handler, player);
    }
    
    public static int isBaubleEquipped(final EntityPlayer player, final Item bauble) {
        final IBaublesItemHandler handler = getBaublesHandler(player);
        for (int a = 0; a < handler.getSlots(); ++a) {
            if (!handler.getStackInSlot(a).isEmpty() && handler.getStackInSlot(a).getItem() == bauble) {
                return a;
            }
        }
        return -1;
    }
}
