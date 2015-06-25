package edu.kit.fsmi.schnupperstudium.chat.common.network;

public interface PacketExecutor {
	boolean executePacket(NetworkChannel channel, Packet packet);
}
