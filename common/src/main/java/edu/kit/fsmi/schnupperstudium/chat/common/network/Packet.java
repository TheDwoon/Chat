package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.util.Objects;

public class Packet {
	private final PacketType type;
	private final byte[] data;

	public Packet(final PacketType type, final byte[] data) {
		Objects.requireNonNull(type);
		
		this.type = type;
		
		if (data == null) {
			this.data = new byte[0];
		} else {
			this.data = data;
		}
	}

	public PacketType getType() {
		return type;
	}

	public int getId() {
		return type.getId();
	}
	
	public byte[] getData() {
		return data;
	}

}
