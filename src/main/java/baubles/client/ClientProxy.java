//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.client;

import java.util.Map;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import baubles.client.gui.GuiPlayerExpanded;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import baubles.client.gui.GuiEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import baubles.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
    public static final KeyBinding KEY_BAUBLES;
    
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        ClientRegistry.registerKeyBinding(ClientProxy.KEY_BAUBLES);
        MinecraftForge.EVENT_BUS.register((Object)new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register((Object)new GuiEvents());
    }
    
    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (world instanceof WorldClient) {
            switch (ID) {
                case 0: {
                    return new GuiPlayerExpanded(player);
                }
            }
        }
        return null;
    }
    
    @Override
    public World getClientWorld() {
        return (World)FMLClientHandler.instance().getClient().world;
    }
    
    @Override
    public void init() {
        final Map<String, RenderPlayer> skinMap = (Map<String, RenderPlayer>)Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer render = skinMap.get("default");
        render.addLayer((LayerRenderer)new BaublesRenderLayer());
        render = skinMap.get("slim");
        render.addLayer((LayerRenderer)new BaublesRenderLayer());
    }
    
    static {
        KEY_BAUBLES = new KeyBinding("keybind.baublesinventory", 48, "key.categories.inventory");
    }
}
