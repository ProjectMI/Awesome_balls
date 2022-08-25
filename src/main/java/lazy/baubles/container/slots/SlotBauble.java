package lazy.baubles.container.slots;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBauble extends SlotItemHandler
{
    int baubleSlot;
    Player player;
    
    public SlotBauble(final Player player, final IBaublesItemHandler itemHandler, final int slot, final int par4, final int par5) {
        super((IItemHandler)itemHandler, slot, par4, par5);
        this.baubleSlot = slot;
        this.player = player;
    }
    
    public boolean m_5857_(final ItemStack stack) {
        return ((IBaublesItemHandler)this.getItemHandler()).isItemValidForSlot(this.baubleSlot, stack);
    }
    
    public boolean m_8010_(final Player player) {
        final ItemStack stack = this.m_7993_();
        if (stack.m_41619_()) {
            return false;
        }
        final IBauble bauble = (IBauble)stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).orElseThrow(NullPointerException::new);
        return bauble.canUnequip((LivingEntity)player);
    }
    
    public void m_142406_(final Player playerIn, final ItemStack stack) {
        if (!this.m_6657_() && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).isPresent()) {
            stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).ifPresent(iBauble -> iBauble.onUnequipped((LivingEntity)playerIn, stack));
        }
        super.m_142406_(playerIn, stack);
    }
    
    public void m_5852_(final ItemStack stack) {
        if (this.m_6657_() && !ItemStack.m_41746_(stack, this.m_7993_()) && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && this.m_7993_().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).isPresent()) {
            this.m_7993_().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).ifPresent(iBauble -> iBauble.onUnequipped((LivingEntity)this.player, stack));
        }
        final ItemStack oldstack = this.m_7993_().m_41777_();
        super.m_5852_(stack);
        if (this.m_6657_() && !ItemStack.m_41746_(oldstack, this.m_7993_()) && !((IBaublesItemHandler)this.getItemHandler()).isEventBlocked() && this.m_7993_().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).isPresent()) {
            this.m_7993_().getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).ifPresent(iBauble -> iBauble.onEquipped((LivingEntity)this.player, stack));
        }
    }
    
    public int m_6641_() {
        return 1;
    }
}
