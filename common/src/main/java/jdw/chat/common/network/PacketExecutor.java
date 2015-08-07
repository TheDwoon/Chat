package jdw.chat.common.network;

import java.io.IOException;

public interface PacketExecutor {
	void handlePacket(ReceivedPacket packet) throws IOException;
}
