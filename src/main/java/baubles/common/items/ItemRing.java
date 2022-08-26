//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.items;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.util.EnumActionResult;
import net.minecraft.entity.EntityLivingBase;
import baubles.api.BaublesApi;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.Mod;
import baubles.api.IBauble;
import net.minecraft.item.Item;

@Mod.EventBusSubscriber
public class ItemRing extends Item implements IBauble
{
    @GameRegistry.ObjectHolder("baubles:ring")
    public static final Item RING;
    
    public ItemRing() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }
    
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemRing().setTranslationKey("Ring").setRegistryName("ring"));
    }
    
    public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            list.add((Object)new ItemStack((Item)this, 1, 0));
        }
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.RING;
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.isRemote) {
            final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
            for (int i = 0; i < baubles.getSlots(); ++i) {
                if ((baubles.getStackInSlot(i) == null || baubles.getStackInSlot(i).isEmpty()) && baubles.isItemValidForSlot(i, player.getHeldItem(hand), (EntityLivingBase)player)) {
                    baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
                    if (!player.capabilities.isCreativeMode) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                    this.onEquipped(player.getHeldItem(hand), (EntityLivingBase)player);
                    break;
                }
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.getHeldItem(hand));
    }
    
    public void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
        if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 0, true, true));
        }
    }
    
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return true;
    }
    
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return EnumRarity.RARE;
    }
    
    public String getTranslationKey(final ItemStack par1ItemStack) {
        return super.getTranslationKey() + "." + par1ItemStack.getItemDamage();
    }
    
    public void onEquipped(final ItemStack itemstack, final EntityLivingBase player) {
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75f, 1.9f);
    }
    
    public void onUnequipped(final ItemStack itemstack, final EntityLivingBase player) {
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.75f, 2.0f);
    }
    
    static {
        RING = null;
    }
}
