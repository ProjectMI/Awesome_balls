//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.client.base;

import java.util.ArrayDeque;
import net.minecraft.client.gui.GuiScreen;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Queue;

public class ClientTickHandler
{
    public static volatile Queue<Runnable> scheduledActions;
    public static int ticksInGame;
    public static float partialTicks;
    public static float delta;
    public static float total;
    
    private static void calcDelta() {
        final float oldTotal = ClientTickHandler.total;
        ClientTickHandler.total = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
        ClientTickHandler.delta = ClientTickHandler.total - oldTotal;
    }
    
    @SubscribeEvent
    public static void renderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ClientTickHandler.partialTicks = event.renderTickTime;
        }
    }
    
    @SubscribeEvent
    public static void clientTickEnd(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.world == null) {
                PlayerDataHandler.cleanup();
            }
            else if (mc.player != null) {
                while (!ClientTickHandler.scheduledActions.isEmpty()) {
                    ClientTickHandler.scheduledActions.poll().run();
                }
            }
            final GuiScreen gui = mc.currentScreen;
            if (gui == null || !gui.doesGuiPauseGame()) {
                ++ClientTickHandler.ticksInGame;
                ClientTickHandler.partialTicks = 0.0f;
                HUDHandler.tick();
            }
            calcDelta();
        }
    }
    
    static {
        ClientTickHandler.scheduledActions = new ArrayDeque<Runnable>();
    }
}
