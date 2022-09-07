//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import java.util.HashMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import codersafterdark.reskillable.network.MessageLockedItem;
import codersafterdark.reskillable.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.init.Blocks;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.ParentLockKey;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashSet;
import codersafterdark.reskillable.api.data.GenericLockKey;
import org.apache.logging.log4j.Level;
import codersafterdark.reskillable.api.ReskillableAPI;
import java.util.ArrayList;
import codersafterdark.reskillable.api.data.GenericNBTLockKey;
import codersafterdark.reskillable.api.data.ModLockKey;
import codersafterdark.reskillable.api.data.ItemInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import codersafterdark.reskillable.api.data.FuzzyLockKey;
import java.util.Set;
import java.util.List;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.data.LockKey;
import java.util.Map;

public class LevelLockHandler
{
    public static final String[] DEFAULT_SKILL_LOCKS;
    private static final Map<LockKey, RequirementHolder> locks;
    public static RequirementHolder EMPTY_LOCK;
    private static Map<Class<?>, List<Class<? extends LockKey>>> lockTypesMap;
    private static Map<LockKey, Set<FuzzyLockKey>> fuzzyLockInfo;
    private static String[] configLocks;
    
    public static void loadFromConfig(final String[] configValues) {
        LevelLockHandler.configLocks = configValues;
    }
    
    public static void setupLocks() {
        registerDefaultLockKeys();
        if (LevelLockHandler.configLocks != null) {
            for (final String s : LevelLockHandler.configLocks) {
                final String[] tokens = s.split("=");
                if (tokens.length == 2) {
                    String itemName = tokens[0].toLowerCase();
                    final String[] itemParts = itemName.split(":");
                    if (itemParts.length == 1) {
                        addModLock(itemName, RequirementHolder.fromString(tokens[1]));
                    }
                    else {
                        int metadata = 0;
                        if (itemParts.length > 2) {
                            final String meta = itemParts[2];
                            try {
                                if (meta.equals("*")) {
                                    metadata = 32767;
                                }
                                else {
                                    metadata = Integer.parseInt(meta);
                                }
                                itemName = itemParts[0] + ':' + itemParts[1];
                            }
                            catch (NumberFormatException ex) {}
                        }
                        final Item item = Item.getByNameOrId(itemName);
                        if (item != null) {
                            addLock(new ItemStack(item, 1, metadata), RequirementHolder.fromString(tokens[1]));
                        }
                    }
                }
            }
        }
    }
    
    private static void registerDefaultLockKeys() {
        registerLockKey(ItemStack.class, ItemInfo.class, ModLockKey.class, GenericNBTLockKey.class);
    }
    
    public static void registerLockKey(final Class<?> lockTypeClass, final Class<? extends LockKey>... keyClasses) {
        for (final Class<? extends LockKey> keyClass : keyClasses) {
            try {
                keyClass.getDeclaredConstructor(lockTypeClass);
                LevelLockHandler.lockTypesMap.computeIfAbsent(lockTypeClass, k -> new ArrayList()).add(keyClass);
            }
            catch (NoSuchMethodException | SecurityException ex2) {
                final Exception ex;
                final Exception e = ex;
                ReskillableAPI.getInstance().log(Level.ERROR, keyClass.getSimpleName() + " does not have a constructor with the parameter: " + lockTypeClass.getSimpleName());
            }
        }
    }
    
    public static void addLockByKey(final LockKey key, final RequirementHolder holder) {
        if (key == null || key instanceof GenericLockKey) {
            return;
        }
        if (holder == null || holder.equals(LevelLockHandler.EMPTY_LOCK) || holder.getRestrictionLength() == 0) {
            return;
        }
        LevelLockHandler.locks.put(key, holder);
        if (key instanceof FuzzyLockKey) {
            final FuzzyLockKey fuzzy = (FuzzyLockKey)key;
            if (!fuzzy.isNotFuzzy()) {
                LockKey without = fuzzy.getNotFuzzy();
                if (without == null) {
                    without = new GenericLockKey(key.getClass());
                }
                LevelLockHandler.fuzzyLockInfo.computeIfAbsent(without, k -> new HashSet()).add(fuzzy);
            }
        }
        ToolTipHandler.resetLast();
    }
    
    public static void addModLock(final String modName, final RequirementHolder holder) {
        addLockByKey(new ModLockKey(modName), holder);
    }
    
    public static void addLock(final ItemStack stack, final RequirementHolder holder) {
        addLockByKey(new ItemInfo(stack), holder);
    }
    
    public static RequirementHolder getLockByKey(final LockKey key) {
        if (LevelLockHandler.locks.containsKey(key)) {
            final RequirementHolder holder = LevelLockHandler.locks.get(key);
            if (!holder.hasNone()) {
                return holder;
            }
        }
        return LevelLockHandler.EMPTY_LOCK;
    }
    
    public static RequirementHolder getLockByFuzzyKey(final FuzzyLockKey key) {
        final List<RequirementHolder> requirements = getFuzzyRequirements(key);
        if (requirements.isEmpty()) {
            return LevelLockHandler.EMPTY_LOCK;
        }
        final RequirementHolder holder = new RequirementHolder((RequirementHolder[])requirements.toArray(new RequirementHolder[0]));
        return holder.hasNone() ? LevelLockHandler.EMPTY_LOCK : holder;
    }
    
    private static List<RequirementHolder> getFuzzyRequirements(final FuzzyLockKey key) {
        final List<RequirementHolder> requirements = new ArrayList<RequirementHolder>();
        if (!key.isNotFuzzy()) {
            LockKey baseLock = key.getNotFuzzy();
            if (baseLock == null) {
                baseLock = new GenericLockKey(key.getClass());
            }
            else if (LevelLockHandler.locks.containsKey(baseLock)) {
                final RequirementHolder holder = LevelLockHandler.locks.get(baseLock);
                if (holder.hasNone()) {
                    return Collections.singletonList(holder);
                }
                requirements.add(holder);
            }
            final Set<FuzzyLockKey> fuzzyLookup = LevelLockHandler.fuzzyLockInfo.get(baseLock);
            if (fuzzyLookup != null) {
                for (final FuzzyLockKey fuzzyLock : fuzzyLookup) {
                    if (key.fuzzyEquals(fuzzyLock) && LevelLockHandler.locks.containsKey(fuzzyLock)) {
                        final RequirementHolder holder2 = LevelLockHandler.locks.get(fuzzyLock);
                        if (holder2.hasNone()) {
                            return Collections.singletonList(holder2);
                        }
                        requirements.add(holder2);
                    }
                }
            }
        }
        else if (LevelLockHandler.locks.containsKey(key)) {
            final RequirementHolder holder3 = LevelLockHandler.locks.get(key);
            if (holder3.hasNone()) {
                return Collections.singletonList(holder3);
            }
            requirements.add(holder3);
        }
        return requirements;
    }
    
    public static RequirementHolder getSkillLock(final ItemStack stack) {
        return (stack == null || stack.isEmpty()) ? LevelLockHandler.EMPTY_LOCK : getLocks(ItemStack.class, new ItemStack[] { stack });
    }
    
    @SafeVarargs
    public static <T> RequirementHolder getLocks(final Class<? extends T> classType, final T... tToCheck) {
        if (tToCheck == null || tToCheck.length == 0 || !LevelLockHandler.lockTypesMap.containsKey(classType)) {
            return LevelLockHandler.EMPTY_LOCK;
        }
        final List<Class<? extends LockKey>> lockTypes = LevelLockHandler.lockTypesMap.get(classType);
        final List<RequirementHolder> requirements = new ArrayList<RequirementHolder>();
        for (final T toCheck : tToCheck) {
            for (final Class<? extends LockKey> keyClass : lockTypes) {
                LockKey lock;
                try {
                    lock = (LockKey)keyClass.getDeclaredConstructor(classType).newInstance(toCheck);
                }
                catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex2) {
                    final ReflectiveOperationException ex;
                    final ReflectiveOperationException e = ex;
                    e.printStackTrace();
                    continue;
                }
                if (lock instanceof FuzzyLockKey) {
                    requirements.addAll(getFuzzyRequirements((FuzzyLockKey)lock));
                }
                else if (LevelLockHandler.locks.containsKey(lock)) {
                    final RequirementHolder holder = LevelLockHandler.locks.get(lock);
                    if (holder.hasNone()) {
                        return LevelLockHandler.EMPTY_LOCK;
                    }
                    requirements.add(holder);
                }
                if (lock instanceof ParentLockKey) {
                    final RequirementHolder subLocks = ((ParentLockKey)lock).getSubRequirements();
                    if (subLocks == null || subLocks.equals(LevelLockHandler.EMPTY_LOCK)) {
                        continue;
                    }
                    if (subLocks.hasNone()) {
                        return LevelLockHandler.EMPTY_LOCK;
                    }
                    requirements.add(subLocks);
                }
            }
        }
        if (requirements.isEmpty()) {
            return LevelLockHandler.EMPTY_LOCK;
        }
        final RequirementHolder holder2 = new RequirementHolder((RequirementHolder[])requirements.toArray(new RequirementHolder[0]));
        return holder2.hasNone() ? LevelLockHandler.EMPTY_LOCK : holder2;
    }
    
    public static boolean canPlayerUseItem(final EntityPlayer player, final ItemStack stack) {
        final RequirementHolder lock = getSkillLock(stack);
        return lock.equals(LevelLockHandler.EMPTY_LOCK) || PlayerDataHandler.get(player).matchStats(lock);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void hurtEvent(final LivingAttackEvent event) {
        if (!event.isCanceled() && event.getSource().getTrueSource() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
            genericEnforce((Event)event, player, player.getHeldItemMainhand(), "reskillable.misc.locked.item");
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void leftClick(final PlayerInteractEvent.LeftClickBlock event) {
        enforce((PlayerInteractEvent)event);
        if (event.isCanceled()) {
            return;
        }
        final IBlockState state = event.getWorld().getBlockState(event.getPos());
        final Block block = state.getBlock();
        ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        if (stack.isEmpty()) {
            stack = block.getItem(event.getWorld(), event.getPos(), state);
        }
        if (block.hasTileEntity(state)) {
            final TileEntity te = event.getWorld().getTileEntity(event.getPos());
            if (te != null && !te.isInvalid()) {
                stack.setTagCompound(te.writeToNBT(new NBTTagCompound()));
            }
        }
        genericEnforce((Event)event, event.getEntityPlayer(), stack, "reskillable.misc.locked.block_break");
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void rightClickItem(final PlayerInteractEvent.RightClickItem event) {
        enforce((PlayerInteractEvent)event);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void rightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        enforce((PlayerInteractEvent)event);
        if (event.isCanceled()) {
            return;
        }
        if (event.getItemStack().isEmpty()) {
            final EntityPlayer player = event.getEntityPlayer();
            genericEnforce((Event)event, player, event.getHand().equals((Object)EnumHand.MAIN_HAND) ? player.getHeldItemOffhand() : player.getHeldItemMainhand(), "reskillable.misc.locked.item");
            if (event.isCanceled()) {
                return;
            }
        }
        final IBlockState state = event.getWorld().getBlockState(event.getPos());
        final Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1, state.getBlock().getMetaFromState(state));
        if (stack.isEmpty()) {
            stack = block.getItem(event.getWorld(), event.getPos(), state);
        }
        if (block.hasTileEntity(state)) {
            final TileEntity te = event.getWorld().getTileEntity(event.getPos());
            if (te != null && !te.isInvalid()) {
                stack.setTagCompound(te.writeToNBT(new NBTTagCompound()));
            }
        }
        genericEnforce((Event)event, event.getEntityPlayer(), stack, "reskillable.misc.locked.block_use");
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockBreak(final BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final IBlockState state = event.getWorld().getBlockState(event.getPos());
        final ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        if (state.getBlock().hasTileEntity(state)) {
            final TileEntity te = event.getWorld().getTileEntity(event.getPos());
            if (te != null && !te.isInvalid()) {
                stack.setTagCompound(te.writeToNBT(new NBTTagCompound()));
            }
        }
        genericEnforce((Event)event, event.getPlayer(), stack, "reskillable.misc.locked.block_break");
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void entityInteract(final PlayerInteractEvent.EntityInteract event) {
        enforce((PlayerInteractEvent)event);
    }
    
    @SubscribeEvent
    public static void onArmorEquip(final LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntity();
            if ((ConfigHandler.enforceOnCreative || !player.isCreative()) && !isFake((Entity)player)) {
                final EntityEquipmentSlot slot = event.getSlot();
                if (slot.getSlotType().equals((Object)EntityEquipmentSlot.Type.ARMOR)) {
                    final ItemStack stack = (ItemStack)player.inventory.armorInventory.get(slot.getIndex());
                    if (!canPlayerUseItem(player, stack)) {
                        if (!player.inventory.addItemStackToInventory(stack)) {
                            player.dropItem(stack, false);
                        }
                        player.inventory.armorInventory.set(slot.getIndex(), (Object)ItemStack.EMPTY);
                        tellPlayer(player, stack, "reskillable.misc.locked.armor_equip");
                    }
                }
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityDrops(final LivingDropsEvent event) {
        if (!event.isCanceled() && ConfigHandler.disableSheepWool && event.getEntity() instanceof EntitySheep) {
            event.getDrops().removeIf(e -> e.getItem().getItem() == Item.getItemFromBlock(Blocks.WOOL));
        }
    }
    
    public static boolean isFake(final Entity e) {
        return e instanceof FakePlayer;
    }
    
    public static void enforce(final PlayerInteractEvent event) {
        genericEnforce((Event)event, event.getEntityPlayer(), event.getItemStack(), "reskillable.misc.locked.item");
    }
    
    public static void genericEnforce(final Event event, final EntityPlayer player, final ItemStack stack, final String lockMessage) {
        if (!event.isCancelable() || event.isCanceled() || player == null || stack == null || stack.isEmpty() || (!ConfigHandler.enforceOnCreative && player.isCreative())) {
            return;
        }
        if (ConfigHandler.enforceFakePlayers) {
            if (!canPlayerUseItem(player, stack)) {
                event.setCanceled(true);
                if (!isFake((Entity)player)) {
                    tellPlayer(player, stack, lockMessage);
                }
            }
        }
        else if (!isFake((Entity)player) && !canPlayerUseItem(player, stack)) {
            tellPlayer(player, stack, lockMessage);
            event.setCanceled(true);
        }
    }
    
    public static void tellPlayer(final EntityPlayer player, final ItemStack stack, final String msg) {
        if (player instanceof EntityPlayerMP) {
            PacketHandler.INSTANCE.sendTo((IMessage)new MessageLockedItem(stack, msg), (EntityPlayerMP)player);
        }
    }
    
    static {
        DEFAULT_SKILL_LOCKS = new String[] { "minecraft:iron_shovel:*=reskillable:gathering|5", "minecraft:iron_axe:*=reskillable:gathering|5", "minecraft:iron_sword:*=reskillable:attack|5", "minecraft:iron_pickaxe:*=reskillable:mining|5", "minecraft:iron_hoe:*=reskillable:farming|5", "minecraft:iron_helmet:*=reskillable:defense|5", "minecraft:iron_chestplate:*=reskillable:defense|5", "minecraft:iron_leggings:*=reskillable:defense|5", "minecraft:iron_boots:*=reskillable:defense|5", "minecraft:golden_shovel:*=reskillable:gathering|5,reskillable:magic|5", "minecraft:golden_axe:*=reskillable:gathering|5,reskillable:magic|5", "minecraft:golden_sword:*=reskillable:attack|5,reskillable:magic|5", "minecraft:golden_pickaxe:*=reskillable:mining|5,reskillable:magic|5", "minecraft:golden_hoe:*=reskillable:farming|5,reskillable:magic|5", "minecraft:golden_helmet:*=reskillable:defense|5,reskillable:magic|5", "minecraft:golden_chestplate:*=reskillable:defense|5,reskillable:magic|5", "minecraft:golden_leggings:*=reskillable:defense|5,reskillable:magic|5", "minecraft:golden_boots:*=reskillable:defense|5,reskillable:magic|5", "minecraft:diamond_shovel:*=reskillable:gathering|16", "minecraft:diamond_axe:*=reskillable:gathering|16", "minecraft:diamond_sword:*=reskillable:attack|16", "minecraft:diamond_pickaxe:*=reskillable:mining|16", "minecraft:diamond_hoe:*=reskillable:farming|16", "minecraft:diamond_helmet:*=reskillable:defense|16", "minecraft:diamond_chestplate:*=reskillable:defense|16", "minecraft:diamond_leggings:*=reskillable:defense|16", "minecraft:diamond_boots:*=reskillable:defense|16", "minecraft:shears:*=reskillable:farming|5,reskillable:gathering|5", "minecraft:fishing_rod:*=reskillable:gathering|8", "minecraft:shield:*=reskillable:defense|8", "minecraft:bow:*=reskillable:attack|8", "minecraft:ender_pearl=reskillable:magic|8", "minecraft:ender_eye=reskillable:magic|16,reskillable:building|8", "minecraft:elytra:*=reskillable:defense|16,reskillable:agility|24,reskillable:magic|16", "minecraft:lead=reskillable:farming|5", "minecraft:end_crystal=reskillable:building|24,reskillable:magic|32", "minecraft:iron_horse_armor:*=reskillable:defense|5,reskillable:agility|5", "minecraft:golden_horse_armor:*=reskillable:defense|5,reskillable:magic|5,reskillable:agility|5", "minecraft:diamond_horse_armor:*=reskillable:defense|16,reskillable:agility|16", "minecraft:fireworks=reskillable:agility|24", "minecraft:dye:15=reskillable:farming|12", "minecraft:saddle=reskillable:agility|12", "minecraft:redstone=reskillable:building|5", "minecraft:redstone_torch=reskillable:building|5", "minecraft:skull:1=reskillable:building|20,reskillable:attack|20,reskillable:defense|20" };
        locks = new HashMap<LockKey, RequirementHolder>();
        LevelLockHandler.EMPTY_LOCK = new RequirementHolder();
        LevelLockHandler.lockTypesMap = new HashMap<Class<?>, List<Class<? extends LockKey>>>();
        LevelLockHandler.fuzzyLockInfo = new HashMap<LockKey, Set<FuzzyLockKey>>();
    }
}
