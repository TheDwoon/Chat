package edu.kit.fsmi.schnupperstudium.chat.server.network;

public interface PacketExecutor {
	boolean executePacket(NetworkChannel channel, Packet packet);
}
