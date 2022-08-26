package ru.zonasumraka.maturinextensions.gui;

import java.util.List;
import codersafterdark.reskillable.client.gui.GuiAbilities;
import codersafterdark.reskillable.client.gui.GuiSkillInfo;
import codersafterdark.reskillable.client.gui.GuiSkills;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomInventoryTabHandler {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void initGui(GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiContainerCreative
				|| event.getGui() instanceof GuiSkills || event.getGui() instanceof GuiAbilities || event.getGui() instanceof GuiSkillInfo) {
			addTabs(event.getGui(), event.getButtonList());
		}
	}

	public static void addTabs(GuiScreen currScreen, List<GuiButton> buttonList) {
		int x = currScreen.width / 2 - 120;
        int y = currScreen.height / 2 - 76;
        
        if (currScreen instanceof GuiContainerCreative) {
            x -= 10;
            y += 15;
        }
        
        int offs = codersafterdark.reskillable.client.gui.button.GuiButtonInventoryTab.TabType.ABILITIES.shouldRender() ? 87 : 58;

		buttonList.add(new GuiButtonInventoryTab(82934, x, y + offs, GuiButtonInventoryTab.TabType.STATS, gui -> gui instanceof GuiMaturinStats));
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void actionPerformed(ActionPerformedEvent event) {
		if(event.getButton().id == 82934) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiMaturinStats());
		}
	}
}
