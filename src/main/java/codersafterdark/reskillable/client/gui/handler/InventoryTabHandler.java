//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.handler;

import codersafterdark.reskillable.client.base.RenderHelper;
import java.util.Collections;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import codersafterdark.reskillable.client.gui.GuiAbilities;
import codersafterdark.reskillable.client.gui.GuiSkillInfo;
import codersafterdark.reskillable.client.gui.GuiSkills;
import net.minecraft.client.gui.inventory.GuiInventory;
import codersafterdark.reskillable.client.gui.button.GuiButtonInventoryTab;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import codersafterdark.reskillable.base.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class InventoryTabHandler
{
    public static String tooltip;
    public static int mx;
    public static int my;
    
    public static void addTabs(final GuiScreen currScreen, final List<GuiButton> buttonList) {
        if (!ConfigHandler.enableTabs) {
            return;
        }
        int x = currScreen.width / 2 - 120;
        int y = currScreen.height / 2 - 76;
        if (currScreen instanceof GuiContainerCreative) {
            x -= 10;
            y += 15;
        }
        buttonList.add(new GuiButtonInventoryTab(82931, x, y, GuiButtonInventoryTab.TabType.INVENTORY, gui -> gui instanceof GuiInventory || gui instanceof GuiContainerCreative));
        buttonList.add(new GuiButtonInventoryTab(82932, x, y + 29, GuiButtonInventoryTab.TabType.SKILLS, gui -> gui instanceof GuiSkills || gui instanceof GuiSkillInfo));
        buttonList.add(new GuiButtonInventoryTab(82933, x, y + 58, GuiButtonInventoryTab.TabType.ABILITIES, gui -> gui instanceof GuiAbilities));
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void initGui(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiContainerCreative) {
            addTabs(event.getGui(), event.getButtonList());
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void performAction(final GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getButton() instanceof GuiButtonInventoryTab) {
            final GuiButtonInventoryTab tab = (GuiButtonInventoryTab)event.getButton();
            final Minecraft mc = Minecraft.getMinecraft();
            switch (tab.type) {
                case INVENTORY: {
                    mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)mc.player));
                    break;
                }
                case SKILLS: {
                    mc.displayGuiScreen((GuiScreen)new GuiSkills());
                    break;
                }
                case ABILITIES: {
                    mc.displayGuiScreen((GuiScreen)new GuiAbilities());
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void finishRenderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END && InventoryTabHandler.tooltip != null) {
            RenderHelper.renderTooltip(InventoryTabHandler.mx, InventoryTabHandler.my, Collections.singletonList(InventoryTabHandler.tooltip));
            InventoryTabHandler.tooltip = null;
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onPotionShiftEvent(final GuiScreenEvent.PotionShiftEvent event) {
        if (ConfigHandler.enableTabs) {
            event.setCanceled(true);
        }
    }
    
    public static int getPotionOffset() {
        return ConfigHandler.enableTabs ? 156 : 124;
    }
}
