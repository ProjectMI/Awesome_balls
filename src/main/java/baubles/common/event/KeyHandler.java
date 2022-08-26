//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import baubles.common.network.PacketOpenBaublesInventory;
import baubles.common.network.PacketHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler
{
    public KeyBinding key;
    
    public KeyHandler() {
        ClientRegistry.registerKeyBinding(this.key = new KeyBinding(I18n.translateToLocal("keybind.baublesinventory"), 48, "key.categories.inventory"));
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        if (event.phase == TickEvent.Phase.START && this.key.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketOpenBaublesInventory(event.player));
        }
    }
}
