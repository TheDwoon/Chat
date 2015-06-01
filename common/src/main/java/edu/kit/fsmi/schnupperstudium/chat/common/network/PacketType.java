package edu.kit.fsmi.schnupperstudium.chat.common.network;

public enum PacketType {

	PING(1), PONG(2), MESSAGE(3), USER(4), SERVICE(5), GROUP(6);

	private final int id;

	private PacketType(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
