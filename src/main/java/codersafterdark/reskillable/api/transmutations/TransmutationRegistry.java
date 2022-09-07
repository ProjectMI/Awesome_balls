//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.transmutations;

import java.util.Iterator;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockSponge;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import java.util.Map;

public class TransmutationRegistry
{
    private static Map<Item, Map<IBlockState, IBlockState>> reagentStateMap;
    
    public static Map<Item, Map<IBlockState, IBlockState>> getReagentStateMap() {
        return TransmutationRegistry.reagentStateMap;
    }
    
    public static Map<IBlockState, IBlockState> getStateMapByReagent(final Item item) {
        return TransmutationRegistry.reagentStateMap.get(item);
    }
    
    public static void initDefaultMap() {
        final Item item = Items.CHORUS_FRUIT;
        addEntryToReagentByBlockDefaultState(item, Blocks.MELON_BLOCK, Blocks.PUMPKIN);
        addEntryToReagentByBlockDefaultState(item, Blocks.MAGMA, Blocks.ICE);
        addEntryToReagentByBlockDefaultState(item, Blocks.ICE, Blocks.MAGMA);
        addEntryToReagentByBlockDefaultState(item, Blocks.STONE, Blocks.END_STONE);
        addEntryToReagentByBlockDefaultState(item, Blocks.END_STONE, Blocks.STONE);
        addEntryToReagentByBlockDefaultState(item, Blocks.CLAY, Blocks.HARDENED_CLAY);
        addEntryToReagentByBlockDefaultState(item, Blocks.HARDENED_CLAY, Blocks.CLAY);
        addEntryToReagent(item, Blocks.SPONGE.getDefaultState().withProperty((IProperty)BlockSponge.WET, (Comparable)true), Blocks.SPONGE.getDefaultState());
        addEntryToReagent(item, Blocks.SPONGE.getDefaultState(), Blocks.SPONGE.getDefaultState().withProperty((IProperty)BlockSponge.WET, (Comparable)true));
        addEntryToReagent(item, Blocks.PRISMARINE.getDefaultState(), Blocks.PRISMARINE.getDefaultState().withProperty((IProperty)BlockPrismarine.VARIANT, (Comparable)BlockPrismarine.EnumType.BRICKS));
        addEntryToReagent(item, Blocks.PRISMARINE.getDefaultState().withProperty((IProperty)BlockPrismarine.VARIANT, (Comparable)BlockPrismarine.EnumType.BRICKS), Blocks.PRISMARINE.getDefaultState().withProperty((IProperty)BlockPrismarine.VARIANT, (Comparable)BlockPrismarine.EnumType.DARK));
        addEntryToReagent(item, Blocks.PRISMARINE.getDefaultState().withProperty((IProperty)BlockPrismarine.VARIANT, (Comparable)BlockPrismarine.EnumType.DARK), Blocks.PRISMARINE.getDefaultState());
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            addEntryToReagent(item, Blocks.PUMPKIN.getDefaultState().withProperty((IProperty)BlockPumpkin.FACING, (Comparable)face), Blocks.MELON_BLOCK.getDefaultState());
        }
        for (int i = 0; i < 6; ++i) {
            addEntryToReagent(item, Blocks.SAPLING.getStateFromMeta(i), Blocks.SAPLING.getStateFromMeta((i == 5) ? 0 : (i + 1)));
        }
        for (int i = 0; i < 16; ++i) {
            addEntryToReagent(item, Blocks.WOOL.getStateFromMeta(i), Blocks.WOOL.getStateFromMeta((i == 15) ? 0 : (i + 1)));
            addEntryToReagent(item, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(i), Blocks.STAINED_HARDENED_CLAY.getStateFromMeta((i == 15) ? 0 : (i + 1)));
            addEntryToReagent(item, Blocks.STAINED_GLASS.getStateFromMeta(i), Blocks.STAINED_GLASS.getStateFromMeta((i == 15) ? 0 : (i + 1)));
            addEntryToReagent(item, Blocks.STAINED_GLASS_PANE.getStateFromMeta(i), Blocks.STAINED_GLASS_PANE.getStateFromMeta((i == 15) ? 0 : (i + 1)));
            addEntryToReagent(item, Blocks.CARPET.getStateFromMeta(i), Blocks.CARPET.getStateFromMeta((i == 15) ? 0 : (i + 1)));
        }
    }
    
    public static boolean doesStateMapContainKeyState(final IBlockState state, final Map<IBlockState, IBlockState> stateMap) {
        return stateMap.containsKey(state);
    }
    
    public static boolean doesReagentStateMapContainReagentItem(final Item item) {
        return TransmutationRegistry.reagentStateMap.containsKey(item);
    }
    
    public static void addEntryToReagentByBlockDefaultState(final Item item, final Block block1, final Block block2) {
        if (TransmutationRegistry.reagentStateMap.containsKey(item)) {
            final Map<IBlockState, IBlockState> map = TransmutationRegistry.reagentStateMap.get(item);
            map.put(block1.getDefaultState(), block2.getDefaultState());
        }
        else {
            final Map<IBlockState, IBlockState> stateMap = new HashMap<IBlockState, IBlockState>();
            stateMap.put(block1.getDefaultState(), block2.getDefaultState());
            TransmutationRegistry.reagentStateMap.put(item, stateMap);
        }
    }
    
    public static void addEntryToReagent(final Item item, final IBlockState state1, final IBlockState state2) {
        if (TransmutationRegistry.reagentStateMap.containsKey(item)) {
            final Map<IBlockState, IBlockState> map = TransmutationRegistry.reagentStateMap.get(item);
            map.put(state1, state2);
        }
        else {
            final Map<IBlockState, IBlockState> stateMap = new HashMap<IBlockState, IBlockState>();
            stateMap.put(state1, state2);
            TransmutationRegistry.reagentStateMap.put(item, stateMap);
        }
    }
    
    public static void addEntriesToReagent(final Item item, final Map<IBlockState, IBlockState> map) {
        if (TransmutationRegistry.reagentStateMap.containsKey(item)) {
            final Map<IBlockState, IBlockState> mapster = TransmutationRegistry.reagentStateMap.get(item);
            for (final Map.Entry<IBlockState, IBlockState> entry : map.entrySet()) {
                mapster.put(entry.getKey(), entry.getValue());
            }
        }
        else {
            final Map<IBlockState, IBlockState> stateMap = new HashMap<IBlockState, IBlockState>();
            for (final Map.Entry<IBlockState, IBlockState> entry : map.entrySet()) {
                stateMap.put(entry.getKey(), entry.getValue());
            }
            TransmutationRegistry.reagentStateMap.put(item, stateMap);
        }
    }
    
    public static void addEntryReagentAgnostic(final IBlockState state1, final IBlockState state2) {
        for (final Item key : TransmutationRegistry.reagentStateMap.keySet()) {
            final Map<IBlockState, IBlockState> map = TransmutationRegistry.reagentStateMap.get(key);
            map.put(state1, state2);
        }
    }
    
    public static void addEntriesReagentAgnostic(final Map<IBlockState, IBlockState> mapster) {
        for (final Item key : TransmutationRegistry.reagentStateMap.keySet()) {
            final Map<IBlockState, IBlockState> map = TransmutationRegistry.reagentStateMap.get(key);
            for (final Map.Entry<IBlockState, IBlockState> entry : mapster.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public static void removeStartStateFromReagentAgnostic(final IBlockState state) {
        for (final Item reagent : TransmutationRegistry.reagentStateMap.keySet()) {
            TransmutationRegistry.reagentStateMap.get(reagent).remove(state);
        }
    }
    
    public static void removeEndStateFromReagentAgnostic(final IBlockState state) {
        for (final Item reagent : TransmutationRegistry.reagentStateMap.keySet()) {
            for (final Map.Entry<IBlockState, IBlockState> stateEntry : TransmutationRegistry.reagentStateMap.get(reagent).entrySet()) {
                if (stateEntry.getValue() == state) {
                    TransmutationRegistry.reagentStateMap.get(reagent).remove(stateEntry.getKey());
                }
            }
        }
    }
    
    public static void removeStartStateFromReagent(final Item reagent, final IBlockState state) {
        TransmutationRegistry.reagentStateMap.get(reagent).remove(state);
    }
    
    public static void removeEndStateFromReagent(final Item reagent, final IBlockState state) {
        for (final Map.Entry<IBlockState, IBlockState> stateEntry : TransmutationRegistry.reagentStateMap.get(reagent).entrySet()) {
            if (stateEntry.getValue() == state) {
                TransmutationRegistry.reagentStateMap.get(reagent).remove(stateEntry.getKey());
            }
        }
    }
    
    public static void clearMapOfReagent(final Item stack) {
        TransmutationRegistry.reagentStateMap.remove(stack);
    }
    
    public static void clearReagentOfEntries(final Item stack) {
        TransmutationRegistry.reagentStateMap.get(stack).clear();
    }
    
    public static void clearReagentMap() {
        TransmutationRegistry.reagentStateMap.clear();
    }
    
    static {
        TransmutationRegistry.reagentStateMap = new HashMap<Item, Map<IBlockState, IBlockState>>();
    }
}
