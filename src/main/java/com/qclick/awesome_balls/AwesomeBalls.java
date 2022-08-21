package com.qclick.awesome_balls;


import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// Значение здесь должно соответствовать записи в файле META-INF/mods.toml
@Mod("awesome_balls")
public class AwesomeBalls
{
    // Прямая ссылка на регистратор log4j.
    private static final Logger LOGGER = LogManager.getLogger();

    public AwesomeBalls() {
        // Зарегистрируйте метод настройки для modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Зарегистрируйте метод doClientStuff для modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Зарегистрируйте себя для участия в серверных и других игровых ивентах, в которых мы заинтересованы
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // некий код для преинициализации

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // а это делает что-то, что можно сделать только на клиенте

    }

}
