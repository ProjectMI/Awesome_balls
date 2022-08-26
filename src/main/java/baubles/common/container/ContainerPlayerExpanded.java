//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.container;

import net.minecraft.entity.EntityLivingBase;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Container;

public class ContainerPlayerExpanded extends Container
{
    public final InventoryCrafting craftMatrix;
    public final InventoryCraftResult craftResult;
    public IBaublesItemHandler baubles;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final EntityEquipmentSlot[] equipmentSlots;
    
    public ContainerPlayerExpanded(final InventoryPlayer playerInv, final boolean par2, final EntityPlayer player) {
        this.craftMatrix = new InventoryCrafting((Container)this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = par2;
        this.thePlayer = player;
        this.baubles = (IBaublesItemHandler)player.getCapability((Capability)BaublesCapabilities.CAPABILITY_BAUBLES, (EnumFacing)null);
        this.addSlotToContainer((Slot)new SlotCrafting(playerInv.player, this.craftMatrix, (IInventory)this.craftResult, 0, 154, 28));
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlotToContainer(new Slot((IInventory)this.craftMatrix, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }
        for (int k = 0; k < 4; ++k) {
            final EntityEquipmentSlot slot = ContainerPlayerExpanded.equipmentSlots[k];
            this.addSlotToContainer((Slot)new Slot(playerInv, 36 + (3 - k), 8, 8 + k * 18) {
                public int getSlotStackLimit() {
                    return 1;
                }
                
                public boolean isItemValid(final ItemStack stack) {
                    return stack.getItem().isValidArmor(stack, slot, (Entity)player);
                }
                
                public boolean canTakeStack(final EntityPlayer playerIn) {
                    final ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
                }
                
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
                }
            });
        }
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 0, 77, 8));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 1, 77, 26));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 2, 77, 44));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 3, 77, 62));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 4, 96, 8));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 5, 96, 26));
        this.addSlotToContainer((Slot)new SlotBauble(player, this.baubles, 6, 96, 44));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)playerInv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)playerInv, i, 8 + i * 18, 142));
        }
        this.addSlotToContainer((Slot)new Slot(playerInv, 40, 96, 62) {
            public boolean isItemValid(final ItemStack stack) {
                return super.isItemValid(stack);
            }
            
            public String getSlotTexture() {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });
        this.onCraftMatrixChanged((IInventory)this.craftMatrix);
    }
    
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.slotChangedCraftingGrid(this.thePlayer.getEntityWorld(), this.thePlayer, this.craftMatrix, this.craftResult);
    }
    
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        this.craftResult.clear();
        if (!player.world.isRemote) {
            this.clearContainer(player, player.world, (IInventory)this.craftMatrix);
        }
    }
    
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            final EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
            final int slotShift = this.baubles.getSlots();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack2, 9 + slotShift, 45 + slotShift, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack2, itemstack);
            }
            else if (index >= 1 && index < 5) {
                if (!this.mergeItemStack(itemstack2, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 5 && index < 9) {
                if (!this.mergeItemStack(itemstack2, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 9 && index < 9 + slotShift) {
                if (!this.mergeItemStack(itemstack2, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR && !this.inventorySlots.get(8 - entityequipmentslot.getIndex()).getHasStack()) {
                final int i = 8 - entityequipmentslot.getIndex();
                if (!this.mergeItemStack(itemstack2, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND && !this.inventorySlots.get(45 + slotShift).getHasStack()) {
                if (!this.mergeItemStack(itemstack2, 45 + slotShift, 46 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (itemstack.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
                final IBauble bauble = (IBauble)itemstack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null);
                for (final int baubleSlot : bauble.getBaubleType(itemstack).getValidSlots()) {
                    if (bauble.canEquip(itemstack2, (EntityLivingBase)this.thePlayer) && !this.inventorySlots.get(baubleSlot + 9).getHasStack() && !this.mergeItemStack(itemstack2, baubleSlot + 9, baubleSlot + 10, false)) {
                        return ItemStack.EMPTY;
                    }
                    if (itemstack2.getCount() == 0) {
                        break;
                    }
                }
            }
            else if (index >= 9 + slotShift && index < 36 + slotShift) {
                if (!this.mergeItemStack(itemstack2, 36 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 36 + slotShift && index < 45 + slotShift) {
                if (!this.mergeItemStack(itemstack2, 9 + slotShift, 36 + slotShift, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 9 + slotShift, 45 + slotShift, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            if (itemstack2.isEmpty() && !this.baubles.isEventBlocked() && slot instanceof SlotBauble && itemstack.hasCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)) {
                ((IBauble)itemstack.getCapability((Capability)BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, (EnumFacing)null)).onUnequipped(itemstack, (EntityLivingBase)playerIn);
            }
            final ItemStack itemstack3 = slot.onTake(playerIn, itemstack2);
            if (index == 0) {
                playerIn.dropItem(itemstack3, false);
            }
        }
        return itemstack;
    }
    
    public boolean canMergeSlot(final ItemStack stack, final Slot slot) {
        return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
    }
    
    static {
        equipmentSlots = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
    }
}
