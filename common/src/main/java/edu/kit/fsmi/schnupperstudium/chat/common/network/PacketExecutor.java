package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.IOException;

public interface PacketExecutor {
	boolean executePacket(NetworkChannel channel, Packet packet) throws IOException;
}
