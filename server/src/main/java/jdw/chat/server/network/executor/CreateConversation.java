package jdw.chat.server.network.executor;

import java.util.function.Consumer;

import jdw.chat.common.network.PacketInputStream;
import jdw.chat.common.network.ReceivedPacket;

public class CreateConversation implements Consumer<ReceivedPacket> {

	@Override
	public void accept(ReceivedPacket packet) {
		PacketInputStream stream = packet.getInputStream();
		
		
	}

}
