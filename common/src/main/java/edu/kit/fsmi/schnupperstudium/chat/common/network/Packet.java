package edu.kit.fsmi.schnupperstudium.chat.common.network;

public class Packet {

	private final PacketType type;
	private final byte[] data;

	public Packet(final PacketType type, final byte[] data) {
		this.type = type;
		this.data = data;
	}

	public PacketType getType() {
		return type;
	}

	public byte[] getData() {
		return data;
	}

}
