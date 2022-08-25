package lazy.baubles.client.util;

import javax.annotation.Nullable;
import lazy.baubles.container.PlayerExpandedContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import javax.annotation.Nonnull;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;

public class GuiProvider implements MenuProvider
{
    @Nonnull
    public Component m_5446_() {
        return (Component)new TextComponent("PlayerBaublesInv");
    }
    
    @Nullable
    public AbstractContainerMenu m_7208_(final int id, @Nonnull final Inventory playerInventory, final Player playerEntity) {
        return new PlayerExpandedContainer(id, playerInventory, !playerEntity.f_19853_.f_46443_);
    }
}
