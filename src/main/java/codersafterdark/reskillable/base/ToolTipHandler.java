//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import java.util.HashMap;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import java.util.Collection;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import codersafterdark.reskillable.api.data.RequirementHolder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.gui.GuiScreen;
import java.util.Map;

public class ToolTipHandler
{
    private static Map<Class<? extends GuiScreen>, Function<ToolTipInfo, List<String>>> tooltipInjectors;
    private static RequirementHolder lastLock;
    private static Class<? extends GuiScreen> currentGui;
    private static List<String> toolTip;
    private static ItemStack lastItem;
    private static boolean enabled;
    
    public static void resetLast() {
        ToolTipHandler.lastItem = null;
        ToolTipHandler.lastLock = LevelLockHandler.EMPTY_LOCK;
        ToolTipHandler.toolTip = new ArrayList<String>();
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onTooltip(final ItemTooltipEvent event) {
        if (!ToolTipHandler.enabled || event.isCanceled()) {
            return;
        }
        final ItemStack current = event.getItemStack();
        final PlayerData data = PlayerDataHandler.get((EntityPlayer)Minecraft.getMinecraft().player);
        if (ToolTipHandler.lastItem != current) {
            ToolTipHandler.lastItem = current;
            (ToolTipHandler.lastLock = LevelLockHandler.getSkillLock(current)).addRequirementsIgnoreShift(data, ToolTipHandler.toolTip = new ArrayList<String>());
        }
        final boolean showDetails = !ConfigHandler.hideRequirements || GuiScreen.isShiftKeyDown();
        final List<String> extraToolTips = new ArrayList<String>();
        if (ToolTipHandler.currentGui != null) {
            final ToolTipInfo info = new ToolTipInfo(showDetails, data, ToolTipHandler.lastItem);
            for (final Map.Entry<Class<? extends GuiScreen>, Function<ToolTipInfo, List<String>>> injectorInfo : ToolTipHandler.tooltipInjectors.entrySet()) {
                if (injectorInfo.getKey().isAssignableFrom(ToolTipHandler.currentGui)) {
                    extraToolTips.addAll(injectorInfo.getValue().apply(info));
                }
            }
        }
        if (!ToolTipHandler.toolTip.isEmpty() || !extraToolTips.isEmpty()) {
            final List<String> curTooltip = (List<String>)event.getToolTip();
            if (showDetails) {
                curTooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("reskillable.misc.requirements", new Object[0]).getUnformattedComponentText());
                curTooltip.addAll(ToolTipHandler.toolTip);
            }
            else {
                curTooltip.add(TextFormatting.DARK_PURPLE + new TextComponentTranslation("reskillable.misc.requirements_shift", new Object[0]).getUnformattedComponentText());
            }
            curTooltip.addAll(extraToolTips);
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onGuiOpen(final GuiOpenEvent event) {
        if (ToolTipHandler.enabled && !event.isCanceled()) {
            ToolTipHandler.currentGui = ((event.getGui() == null) ? null : event.getGui().getClass());
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void connect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ToolTipHandler.enabled = true;
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void disconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ToolTipHandler.enabled = false;
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCacheInvalidated(final CacheInvalidatedEvent event) {
        if (event.anyModified()) {
            resetLast();
        }
    }
    
    public static void addTooltipInjector(final Class<? extends GuiScreen> gui, final Function<ToolTipInfo, List<String>> creator) {
        ToolTipHandler.tooltipInjectors.put(gui, creator);
    }
    
    static {
        ToolTipHandler.tooltipInjectors = new HashMap<Class<? extends GuiScreen>, Function<ToolTipInfo, List<String>>>();
        ToolTipHandler.lastLock = LevelLockHandler.EMPTY_LOCK;
        ToolTipHandler.toolTip = new ArrayList<String>();
    }
    
    public static class ToolTipInfo
    {
        private boolean showDetails;
        private PlayerData data;
        private ItemStack item;
        
        public ToolTipInfo(final boolean showDetails, final PlayerData data, final ItemStack item) {
            this.showDetails = showDetails;
            this.data = data;
            this.item = item;
        }
        
        public boolean showDetails() {
            return this.showDetails;
        }
        
        public PlayerData getData() {
            return this.data;
        }
        
        public ItemStack getItem() {
            return this.item;
        }
    }
}
