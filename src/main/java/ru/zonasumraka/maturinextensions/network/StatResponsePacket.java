package ru.zonasumraka.maturinextensions.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.zonasumraka.maturinextensions.capability.MECapabilities;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.IMECapabilities;

public class StatResponsePacket implements IMessage {

	public StatResponsePacket() {
		this.caps = new MECapabilities.MECapabilitiesDefault();
	}

	public StatResponsePacket(IMECapabilities caps) {
		this.caps = caps;
	}

	public MECapabilities.IMECapabilities caps;

	@Override
	public void fromBytes(ByteBuf buf) {
		MECapabilities.MECapabilitiesStorage.internalReadNBT(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, caps,
				null, ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, (NBTTagCompound) MECapabilities.MECapabilitiesStorage
				.internalWriteNBT(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, caps, null));
	}

	public static class Handler implements IMessageHandler<StatResponsePacket, IMessage> {

		@Override
		public IMessage onMessage(StatResponsePacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player
					.getCapability(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, null).transfer(message.caps));
			return null;
		}

	}
}
