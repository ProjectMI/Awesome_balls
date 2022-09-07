//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.gui.handler;

import codersafterdark.reskillable.Reskillable;
import net.minecraft.client.gui.GuiScreen;
import codersafterdark.reskillable.client.gui.GuiSkills;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindings
{
    public static KeyBinding openGUI;
    
    public static void init() {
        ClientRegistry.registerKeyBinding(KeyBindings.openGUI);
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        this.keyTyped(KeyBindings.openGUI);
    }
    
    private void keyTyped(final KeyBinding binding) {
        final Minecraft minecraft = FMLClientHandler.instance().getClient();
        if (binding.isPressed() && minecraft.currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiSkills());
        }
    }
    
    static {
        KeyBindings.openGUI = new KeyBinding(Reskillable.proxy.getLocalizedString("key.open_gui"), 21, Reskillable.proxy.getLocalizedString("key.controls.reskillable"));
    }
}
