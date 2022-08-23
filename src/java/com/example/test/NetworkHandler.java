package com.example.test;


import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	
	public static SimpleNetworkWrapper network;

	public static void init() {
		
		//инициализируем класс, что занимаетс€ передачей и обработкой пакетов между клиентом и сервером. TestMod.MOD_ID - айди мода.
		network = NetworkRegistry.INSTANCE.newSimpleChannel(TestMod.MOD_ID);
		
		/*	–егистрируем пакет. ѕараметры: класс обработчика(статический класс, который лежит внутри OpenInventoryMessage), 
		  	класс самого сообщени€, идентификатор, сторона, на которой будет обрабатыватьс€ пакет.
		  	“ак как мы посылаем его на сервер, дл€ открыти€ √”» менно оттуда, то указываем Side.SERVER
		*/
		network.registerMessage(OpenInventoryMessage.Handler.class, OpenInventoryMessage.class, 0, Side.SERVER);
	}	
}
