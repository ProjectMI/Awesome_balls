package lazy.baubles.container.slots;

import javax.annotation.Nullable;
import lazy.baubles.container.PlayerExpandedContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.Slot;

public class ArmorSlot extends Slot
{
    private final EquipmentSlot slotType;
    private final Player playerEntity;
    
    public ArmorSlot(final Container inventoryIn, final int index, final int xPosition, final int yPosition, final EquipmentSlot slotType, final Player playerEntity) {
        super(inventoryIn, index, xPosition, yPosition);
        this.slotType = slotType;
        this.playerEntity = playerEntity;
    }
    
    public int m_6641_() {
        return 1;
    }
    
    public boolean m_5857_(final ItemStack stack) {
        return stack.canEquip(this.slotType, (Entity)this.playerEntity);
    }
    
    public boolean m_8010_(@Nonnull final Player playerIn) {
        final ItemStack itemStack = this.m_7993_();
        return (itemStack.m_41619_() || playerIn.m_7500_() || !EnchantmentHelper.m_44920_(itemStack)) && super.m_8010_(playerIn);
    }
    
    @Nullable
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return (Pair<ResourceLocation, ResourceLocation>)Pair.of((Object)InventoryMenu.f_39692_, (Object)PlayerExpandedContainer.ARMOR_SLOT_TEXTURES[this.slotType.m_20749_()]);
    }
}
