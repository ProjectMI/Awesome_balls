package com.example.test;


import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	
	public static SimpleNetworkWrapper network;

	public static void init() {
		
		//�������������� �����, ��� ���������� ��������� � ���������� ������� ����� �������� � ��������. TestMod.MOD_ID - ���� ����.
		network = NetworkRegistry.INSTANCE.newSimpleChannel(TestMod.MOD_ID);
		
		/*	������������ �����. ���������: ����� �����������(����������� �����, ������� ����� ������ OpenInventoryMessage), 
		  	����� ������ ���������, �������������, �������, �� ������� ����� �������������� �����.
		  	��� ��� �� �������� ��� �� ������, ��� �������� ��� ����� ������, �� ��������� Side.SERVER
		*/
		network.registerMessage(OpenInventoryMessage.Handler.class, OpenInventoryMessage.class, 0, Side.SERVER);
	}	
}
