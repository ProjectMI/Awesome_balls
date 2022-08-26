//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;

public class GuiEvents
{
    @SubscribeEvent
    public void guiPostInit(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiPlayerExpanded) {
            final GuiContainer gui = (GuiContainer)event.getGui();
            event.getButtonList().add(new GuiBaublesButton(55, gui, 64, 9, 10, 10, I18n.format((event.getGui() instanceof GuiInventory) ? "button.baubles" : "button.normal", new Object[0])));
        }
    }
}
