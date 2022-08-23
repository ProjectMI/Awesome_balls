package com.example.test;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenInventoryMessage implements IMessage {
    
	//���� ������ ������ ������� ��� � ��������� �������, ������ ������ ���������� �� �����.
		
	//������������ ����������� ��� ����������
    public OpenInventoryMessage() { }

    @Override
    public void fromBytes(ByteBuf buf) {
      
    }

    @Override
    public void toBytes(ByteBuf buf) {
       
    }

  
    //�����-���������� ������. ��������� ����� onMessage, ������� ����������� ����� ����� �������� �� ������(� ������ ������)
    
    public static class Handler implements IMessageHandler<OpenInventoryMessage, IMessage> {
        
    	//����� ��� ��, ��� ������ ��������� ����� ����� ������ �� �������
        @Override
        public IMessage onMessage(OpenInventoryMessage message, MessageContext ctx) {
        	
        	/*	� ������ ������ ������� ������, ������� �������� ����� � ��������� ���.
        		TestMod.INSTANCE - ������� �������� ������, �.�. ��� ������.
        		�������� ��� �������� ������ TestMod, � modid = "testmod", ����� � ������� ������ �����: @Mod.Instance("testmod") public static TestMod INSTANCE;
         		GuiHandler.INVENTORY_GUI_ID - ������������� ������ ���. � �������� �������� 0.
        	*/
        	EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        	player.openGui(TestMod.INSTANCE, GuiHandler.INVENTORY_GUI_ID, player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);			
            return null; 
        }
    }
}