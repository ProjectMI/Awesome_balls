package com.example.test;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenInventoryMessage implements IMessage {
    
	//Наша задача просто открыть ГУИ с серверной стороны, ничего больше передавать не нужно.
		
	//ОБЯЗАТЕЛЬНЫЙ конструктор без параметров
    public OpenInventoryMessage() { }

    @Override
    public void fromBytes(ByteBuf buf) {
      
    }

    @Override
    public void toBytes(ByteBuf buf) {
       
    }

  
    //Класс-обработчик пакета. Реализует метод onMessage, который срабатывает когда пакет приходит на сервер(в данном случае)
    
    public static class Handler implements IMessageHandler<OpenInventoryMessage, IMessage> {
        
    	//Пишем тут то, что должно произойти когда пакет дойдет до сервера
        @Override
        public IMessage onMessage(OpenInventoryMessage message, MessageContext ctx) {
        	
        	/*	В данном случае достаем игрока, который отправил пакет и открываем ГУИ.
        		TestMod.INSTANCE - инстанс главного класса, т.е. его обьект.
        		Допустим имя главного класса TestMod, а modid = "testmod", тогда в главном классе пишем: @Mod.Instance("testmod") public static TestMod INSTANCE;
         		GuiHandler.INVENTORY_GUI_ID - идентификатор нашего ГУИ. Я присвоил значение 0.
        	*/
        	EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        	player.openGui(TestMod.INSTANCE, GuiHandler.INVENTORY_GUI_ID, player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);			
            return null; 
        }
    }
}