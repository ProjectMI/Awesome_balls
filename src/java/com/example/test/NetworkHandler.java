package com.example.test;


import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

ppublic class NetworkHandler {

	public static SimpleNetworkWrapper network;

	public static void init() {

		//инициализация класса, что занимается передачей и обработкой пакетов между клиентом и сервером. TestMod.MOD_ID - айди мода.
		network = NetworkRegistry.INSTANCE.newSimpleChannel(TestMod.MOD_ID);
        /*    Регистрирация пакета. Параметры: класс обработчика(статический класс, который лежит внутри OpenInventoryMessage),
              класс самого сообщения, идентификатор, сторона, на которой будет обрабатываться пакет.
              Так он посылается на сервер, для открытия GUI менно оттуда, то указываем Side.SERVER
        */
		network.registerMessage(OpenInventoryMessage.Handler.class, OpenInventoryMessage.class, 0, Side.SERVER);
	}
}
