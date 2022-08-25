package lazy.baubles.setup;

import lazy.baubles.container.PlayerExpandedContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS;
    public static final RegistryObject<MenuType<PlayerExpandedContainer>> PLAYER_BAUBLES;

    public ModMenus() {
    }

    public static void init() {
        MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    static {
        MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, "baubles");
        PLAYER_BAUBLES = MENUS.register("player_baubles", () -> {
            return IForgeMenuType.create((windowId, inv, data) -> {
                return new PlayerExpandedContainer(windowId, inv, !inv.f_35978_.f_19853_.f_46443_);
            });
        });
    }
}
