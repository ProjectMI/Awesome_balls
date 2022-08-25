package lazy.baubles.container;

import net.minecraft.world.inventory.InventoryMenu;
import lazy.baubles.container.slots.SlotBauble;
import net.minecraft.world.entity.LivingEntity;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.CraftingMenu;
import javax.annotation.Nonnull;
import lazy.baubles.container.slots.OffHandSlot;
import lazy.baubles.container.slots.ArmorSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraftforge.common.capabilities.Capability;
import lazy.baubles.api.cap.CapabilityBaubles;
import lazy.baubles.setup.ModMenus;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Inventory;
import lazy.baubles.api.bauble.IBaublesItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PlayerExpandedContainer extends AbstractContainerMenu
{
    public static final ResourceLocation[] ARMOR_SLOT_TEXTURES;
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS;
    private final CraftingContainer craftMatrix;
    private final ResultContainer craftResult;
    public final boolean isLocalWorld;
    private final Player player;
    public IBaublesItemHandler baubles;
    
    public PlayerExpandedContainer(final int id, final Inventory playerInventory, final boolean localWorld) {
        super((MenuType)ModMenus.PLAYER_BAUBLES.get(), id);
        this.craftMatrix = new CraftingContainer((AbstractContainerMenu)this, 2, 2);
        this.craftResult = new ResultContainer();
        this.isLocalWorld = localWorld;
        this.player = playerInventory.f_35978_;
        this.baubles = (IBaublesItemHandler)this.player.getCapability((Capability)CapabilityBaubles.BAUBLES).orElseThrow(NullPointerException::new);
        this.m_38897_((Slot)new ResultSlot(playerInventory.f_35978_, this.craftMatrix, (Container)this.craftResult, 0, 154, 28));
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.m_38897_(new Slot((Container)this.craftMatrix, j + i * 2, 116 + j * 18, 18 + i * 18));
            }
        }
        for (int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentSlotType = PlayerExpandedContainer.VALID_EQUIPMENT_SLOTS[k];
            this.m_38897_((Slot)new ArmorSlot((Container)playerInventory, 36 + (3 - k), 8, 8 + k * 18, equipmentSlotType, this.player));
        }
        this.addBaubleSlots();
        for (int l = 0; l < 3; ++l) {
            for (int j2 = 0; j2 < 9; ++j2) {
                this.m_38897_(new Slot((Container)playerInventory, j2 + (l + 1) * 9, 8 + j2 * 18, 84 + l * 18));
            }
        }
        for (int i2 = 0; i2 < 9; ++i2) {
            this.m_38897_(new Slot((Container)playerInventory, i2, 8 + i2 * 18, 142));
        }
        this.m_38897_((Slot)new OffHandSlot((Container)playerInventory, 40, 96, 62));
    }
    
    public void m_6199_(@Nonnull final Container container) {
        try {
            final Method onCraftChange = ObfuscationReflectionHelper.findMethod((Class)CraftingMenu.class, "slotChangedCraftingGrid", new Class[] { AbstractContainerMenu.class, Level.class, Player.class, CraftingContainer.class, ResultContainer.class });
            onCraftChange.invoke(null, this, this.player.f_19853_, this.player, this.craftMatrix, this.craftResult);
        }
        catch (IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
    
    public void m_6877_(@Nonnull final Player player) {
        super.m_6877_(player);
        this.craftResult.m_6211_();
        if (!player.f_19853_.f_46443_) {
            this.m_150411_(player, (Container)this.craftMatrix);
        }
    }
    
    public boolean m_6875_(@Nonnull final Player par1PlayerEntity) {
        return true;
    }
    
    @Nonnull
    public ItemStack m_7648_(@Nonnull final Player playerIn, final int index) {
        ItemStack itemStack = ItemStack.f_41583_;
        final Slot slot = (Slot)this.f_38839_.get(index);
        if (slot != null && slot.m_6657_()) {
            final ItemStack stackInSlot = slot.m_7993_();
            itemStack = stackInSlot.m_41777_();
            final EquipmentSlot entityEquipmentSlot = Mob.m_147233_(itemStack);
            final int slotShift = this.baubles.getSlots();
            if (index == 0) {
                if (!this.m_38903_(stackInSlot, 9 + slotShift, 45 + slotShift, true)) {
                    return ItemStack.f_41583_;
                }
                slot.m_40234_(stackInSlot, itemStack);
            }
            else if (index >= 1 && index < 5) {
                if (!this.m_38903_(stackInSlot, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (index >= 5 && index < 9) {
                if (!this.m_38903_(stackInSlot, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (index >= 9 && index < 9 + slotShift) {
                if (!this.m_38903_(stackInSlot, 9 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (entityEquipmentSlot.m_20743_() == EquipmentSlot.Type.ARMOR && !((Slot)this.f_38839_.get(8 - entityEquipmentSlot.m_20749_())).m_6657_()) {
                final int i = 8 - entityEquipmentSlot.m_20749_();
                if (!this.m_38903_(stackInSlot, i, i + 1, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (entityEquipmentSlot == EquipmentSlot.OFFHAND && !((Slot)this.f_38839_.get(45 + slotShift)).m_6657_()) {
                if (!this.m_38903_(stackInSlot, 45 + slotShift, 46 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).isPresent()) {
                final IBauble bauble = (IBauble)itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).orElseThrow(NullPointerException::new);
                for (final int baubleSlot : bauble.getBaubleType(itemStack).getValidSlots()) {
                    if (bauble.canEquip((LivingEntity)this.player) && !((Slot)this.f_38839_.get(baubleSlot + 9)).m_6657_() && !this.m_38903_(stackInSlot, baubleSlot + 9, baubleSlot + 10, false)) {
                        return ItemStack.f_41583_;
                    }
                    if (stackInSlot.m_41613_() == 0) {
                        break;
                    }
                }
            }
            else if (index >= 9 + slotShift && index < 36 + slotShift) {
                if (!this.m_38903_(stackInSlot, 36 + slotShift, 45 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (index >= 36 + slotShift && index < 45 + slotShift) {
                if (!this.m_38903_(stackInSlot, 9 + slotShift, 36 + slotShift, false)) {
                    return ItemStack.f_41583_;
                }
            }
            else if (!this.m_38903_(stackInSlot, 9 + slotShift, 45 + slotShift, false)) {
                return ItemStack.f_41583_;
            }
            if (stackInSlot.m_41619_()) {
                slot.m_5852_(ItemStack.f_41583_);
            }
            else {
                slot.m_6654_();
            }
            if (stackInSlot.m_41613_() == itemStack.m_41613_()) {
                return ItemStack.f_41583_;
            }
            if (stackInSlot.m_41619_() && !this.baubles.isEventBlocked() && slot instanceof SlotBauble && itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).isPresent()) {
                final ItemStack finalItemStack = itemStack;
                itemStack.getCapability((Capability)CapabilityBaubles.ITEM_BAUBLE, (Direction)null).ifPresent(iBauble -> iBauble.onEquipped((LivingEntity)playerIn, finalItemStack));
            }
        }
        return itemStack;
    }
    
    public boolean m_5882_(final ItemStack stack, final Slot slot) {
        return slot.f_40218_ != this.craftResult && super.m_5882_(stack, slot);
    }
    
    private void addBaubleSlots() {
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 0, 77, 8));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 1, 77, 26));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 2, 77, 44));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 3, 77, 62));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 4, 96, 8));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 5, 96, 26));
        this.m_38897_((Slot)new SlotBauble(this.player, this.baubles, 6, 96, 44));
    }
    
    static {
        ARMOR_SLOT_TEXTURES = new ResourceLocation[] { InventoryMenu.f_39696_, InventoryMenu.f_39695_, InventoryMenu.f_39694_, InventoryMenu.f_39693_ };
        VALID_EQUIPMENT_SLOTS = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
    }
}
