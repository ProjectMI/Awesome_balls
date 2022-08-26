//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.inv;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.entity.player.EntityPlayer;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.inventory.IInventory;

public class BaublesInventoryWrapper implements IInventory
{
    final IBaublesItemHandler handler;
    final EntityPlayer player;
    
    public BaublesInventoryWrapper(final IBaublesItemHandler handler) {
        this.handler = handler;
        this.player = null;
    }
    
    public BaublesInventoryWrapper(final IBaublesItemHandler handler, final EntityPlayer player) {
        this.handler = handler;
        this.player = player;
    }
    
    public String getName() {
        return "BaublesInventory";
    }
    
    public boolean hasCustomName() {
        return false;
    }
    
    public ITextComponent getDisplayName() {
        return (ITextComponent)new TextComponentString(this.getName());
    }
    
    public int getSizeInventory() {
        return this.handler.getSlots();
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public ItemStack getStackInSlot(final int index) {
        return this.handler.getStackInSlot(index);
    }
    
    public ItemStack decrStackSize(final int index, final int count) {
        return this.handler.extractItem(index, count, false);
    }
    
    public ItemStack removeStackFromSlot(final int index) {
        final ItemStack out = this.getStackInSlot(index);
        this.handler.setStackInSlot(index, ItemStack.EMPTY);
        return out;
    }
    
    public void setInventorySlotContents(final int index, final ItemStack stack) {
        this.handler.setStackInSlot(index, stack);
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public void markDirty() {
    }
    
    public boolean isUsableByPlayer(final EntityPlayer player) {
        return true;
    }
    
    public void openInventory(final EntityPlayer player) {
    }
    
    public void closeInventory(final EntityPlayer player) {
    }
    
    public boolean isItemValidForSlot(final int index, final ItemStack stack) {
        return this.handler.isItemValidForSlot(index, stack, (EntityLivingBase)this.player);
    }
    
    public int getField(final int id) {
        return 0;
    }
    
    public void setField(final int id, final int value) {
    }
    
    public int getFieldCount() {
        return 0;
    }
    
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }
}
