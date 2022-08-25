package lazy.baubles.capability;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import lazy.baubles.event.EventHandlerEntity;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import javax.annotation.Nonnull;
import net.minecraftforge.common.util.LazyOptional;
import lazy.baubles.api.bauble.IBauble;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import java.util.Arrays;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BaublesContainer extends ItemStackHandler implements IBaublesItemHandler
{
    private static final int BAUBLE_SLOTS = 7;
    private final ItemStack[] previous;
    private final boolean[] changed;
    private boolean blockEvents;
    private final LivingEntity holder;
    
    public BaublesContainer(final LivingEntity player) {
        super(7);
        this.previous = new ItemStack[7];
        this.changed = new boolean[7];
        this.blockEvents = false;
        this.holder = player;
        Arrays.fill(this.previous, ItemStack.f_41583_);
    }
    
    public void setSize(final int size) {
        if (size != 7) {
            System.out.println("Cannot resize baubles container");
        }
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack stack) {
        final LazyOptional<IBauble> baubleCap = (LazyOptional<IBauble>)stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE);
        if (stack.m_41619_() || !baubleCap.isPresent()) {
            return false;
        }
        final IBauble bauble = (IBauble)baubleCap.orElseThrow(NullPointerException::new);
        return bauble.canEquip(this.holder) && bauble.getBaubleType(stack).hasSlot(slot);
    }
    
    public void setStackInSlot(final int slot, @Nonnull final ItemStack stack) {
        if (stack.m_41619_() || this.isItemValidForSlot(slot, stack)) {
            super.setStackInSlot(slot, stack);
        }
    }
    
    @Nonnull
    public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        if (!this.isItemValidForSlot(slot, stack)) {
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
        this.changed[slot] = true;
    }
    
    public void tick() {
        for (int i = 0; i < this.getSlots(); ++i) {
            final ItemStack stack = this.getStackInSlot(i);
            stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).ifPresent(b -> b.onWornTick(this.holder, stack));
        }
        this.sync();
    }
    
    private void sync() {
        if (!(this.holder instanceof ServerPlayer)) {
            return;
        }
        final ServerPlayer holder = (ServerPlayer)this.holder;
        List<ServerPlayer> receivers = null;
        for (byte i = 0; i < this.getSlots(); ++i) {
            final ItemStack stack = this.getStackInSlot((int)i);
            final boolean autoSync = stack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE).map(b -> b.willAutoSync(this.holder)).orElse(false);
            if (this.changed[i] || (autoSync && !ItemStack.m_41746_(stack, this.previous[i]))) {
                if (receivers == null) {
                    receivers = new ArrayList<ServerPlayer>(((ServerLevel)this.holder.f_19853_).m_8795_(serverPlayerEntity -> true));
                    receivers.add(holder);
                }
                EventHandlerEntity.syncSlot((Player)holder, i, stack, (Collection<? extends Player>)receivers);
                this.changed[i] = false;
                this.previous[i] = stack.m_41777_();
            }
        }
    }
}
