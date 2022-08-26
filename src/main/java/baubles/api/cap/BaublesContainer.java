//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api.cap;

import baubles.api.IBauble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.items.ItemStackHandler;

public class BaublesContainer extends ItemStackHandler implements IBaublesItemHandler
{
    private static final int BAUBLE_SLOTS = 7;
    private boolean[] changed;
    private boolean blockEvents;
    private EntityLivingBase player;
    
    public BaublesContainer() {
        super(7);
        this.changed = new boolean[7];
        this.blockEvents = false;
    }
    
    public void setSize(int size) {
        if (size < 7) {
            size = 7;
        }
        super.setSize(size);
        final boolean[] old = this.changed;
        this.changed = new boolean[size];
        for (int i = 0; i < old.length && i < this.changed.length; ++i) {
            this.changed[i] = old[i];
        }
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack stack, final EntityLivingBase player) {
        if (stack == null || stack.isEmpty() || !stack.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
            return false;
        }
        final IBauble bauble = (IBauble)stack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
        return bauble.canEquip(stack, player) && bauble.getBaubleType(stack).hasSlot(slot);
    }
    
    public void setStackInSlot(final int slot, final ItemStack stack) {
        if (stack == null || stack.isEmpty() || this.isItemValidForSlot(slot, stack, this.player)) {
            super.setStackInSlot(slot, stack);
        }
    }
    
    public ItemStack insertItem(final int slot, final ItemStack stack, final boolean simulate) {
        if (!this.isItemValidForSlot(slot, stack, this.player)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
    
    public boolean isEventBlocked() {
        return this.blockEvents;
    }
    
    public void setEventBlock(final boolean blockEvents) {
        this.blockEvents = blockEvents;
    }
    
    protected void onContentsChanged(final int slot) {
        this.setChanged(slot, true);
    }
    
    public boolean isChanged(final int slot) {
        if (this.changed == null) {
            this.changed = new boolean[this.getSlots()];
        }
        return this.changed[slot];
    }
    
    public void setChanged(final int slot, final boolean change) {
        if (this.changed == null) {
            this.changed = new boolean[this.getSlots()];
        }
        this.changed[slot] = change;
    }
    
    public void setPlayer(final EntityLivingBase player) {
        this.player = player;
    }
}
