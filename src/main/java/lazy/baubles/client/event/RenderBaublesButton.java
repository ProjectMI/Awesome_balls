package lazy.baubles.client.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import lazy.baubles.client.gui.widget.BaublesButton;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = { Dist.CLIENT })
public class RenderBaublesButton
{
    @SubscribeEvent
    public static void onGuiPostInit(final ScreenEvent.InitScreenEvent.Post event) {
        final Screen screen = event.getScreen();
        if (screen instanceof EffectRenderingInventoryScreen) {
            final EffectRenderingInventoryScreen effectRenderingInventoryScreen = (EffectRenderingInventoryScreen)screen;
            if (event.getListenersList() != null) {
                event.addListener((GuiEventListener)new BaublesButton((AbstractContainerScreen)effectRenderingInventoryScreen, 64, 9, 10, 10));
            }
        }
    }
}
