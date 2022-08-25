package lazy.baubles.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModConfigs {
    private static final ForgeConfigSpec CLIENT;
    public static final ForgeConfigSpec.BooleanValue RENDER_BAUBLE;

    public ModConfigs() {
    }

    public static void registerAndLoadConfig() {
        ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT);
        CommentedFileConfig config = (CommentedFileConfig)CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("baubles".concat("-client.toml"))).sync().writingMode(WritingMode.REPLACE).build();
        config.load();
        CLIENT.setConfig(config);
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("baubles");
        RENDER_BAUBLE = builder.comment("When enabled baubles can render on player.").define("render_baubles", true);
        builder.pop();
        CLIENT = builder.build();
    }
}
