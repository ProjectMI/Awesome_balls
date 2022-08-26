package ru.zonasumraka.maturinextensions.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.zonasumraka.maturinextensions.capability.MECapabilities;

public class StatRequestPacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<StatRequestPacket, StatResponsePacket> {

		@Override
		public StatResponsePacket onMessage(StatRequestPacket message, MessageContext ctx) {
			return new StatResponsePacket(ctx.getServerHandler().player
					.getCapability(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, null));
		}

	}
}
