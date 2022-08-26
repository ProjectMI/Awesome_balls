//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common;

import baubles.common.event.EventHandlerItem;
import baubles.common.event.EventHandlerEntity;
import net.minecraftforge.common.MinecraftForge;
import baubles.common.container.ContainerPlayerExpanded;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        switch (ID) {
            case 0: {
                return new ContainerPlayerExpanded(player.inventory, !world.isRemote, player);
            }
            default: {
                return null;
            }
        }
    }
    
    public World getClientWorld() {
        return null;
    }
    
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register((Object)new EventHandlerEntity());
        MinecraftForge.EVENT_BUS.register((Object)new EventHandlerItem());
    }
    
    public void init() {
    }
}
