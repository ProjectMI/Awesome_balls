//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.container;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBauble extends SlotItemHandler
{
    int baubleSlot;
    EntityPlayer player;
    
    public SlotBauble(final EntityPlayer player, final IBaublesItemHandler itemHandler, final int slot, final int par4, final int par5) {
        super((IItemHandler)itemHandler, slot, par4, par5);
        this.baubleSlot = slot;
        this.player = player;
    }
    
    public boolean isItemValid(final ItemStack stack) {
        return ((IBaublesItemHandler)this.getItemHandler()).isItemValidForSlot(this.baubleSlot, stack, (EntityLivingBase)this.player);
    }
    
    public boolean canTakeStack(final EntityPlayer player) {
        final ItemStack stack = this.getStack();
        if (stack.isEmpty()) {
            return false;
        }
        final IBauble bauble = (IBauble)stack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
        return bauble.canUnequip(stack, (EntityLivingBase)player);
    }
    
    public ItemStack onTake(final EntityPlayer playerIn, final ItemStack stack) {
        if (!this.getHasStack() && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && stack.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
            ((IBauble)stack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)).onUnequipped(stack, (EntityLivingBase)playerIn);
        }
        super.onTake(playerIn, stack);
        return stack;
    }
    
    public void putStack(final ItemStack stack) {
        if (this.getHasStack() && !ItemStack.areItemStacksEqual(stack, this.getStack()) && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && this.getStack().hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
            ((IBauble)this.getStack().getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)).onUnequipped(this.getStack(), (EntityLivingBase)this.player);
        }
        final ItemStack oldstack = this.getStack().copy();
        super.putStack(stack);
        if (this.getHasStack() && !ItemStack.areItemStacksEqual(oldstack, this.getStack()) && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && this.getStack().hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
            ((IBauble)this.getStack().getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)).onEquipped(this.getStack(), (EntityLivingBase)this.player);
        }
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
}
