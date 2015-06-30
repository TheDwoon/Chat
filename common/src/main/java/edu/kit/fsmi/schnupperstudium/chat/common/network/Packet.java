package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.ByteArrayInputStream;


public class Packet {
	public static final int REQ_AUTH = 1;
	public static final int RPL_AUTH = 2;
	
	private final int id;
	private final byte[] data;

	public Packet(final int id, int data) {
		this(id, (byte) data);
	}
	
	public Packet(final int id, byte data) {
		this(id, new byte[] { data });
	}
	
	public Packet(final int id, final byte[] data) {	
		this.id = id;
		
		if (data == null) {
			this.data = new byte[0];
		} else {
			this.data = data;
		}
	}

	public int getId() {
		return id;
	}
		
	public byte[] getData() {
		return data;
	}

	public PacketInputStream getInputStream() {
		return new PacketInputStream(new ByteArrayInputStream(data));
	}
	
	@Override
	public String toString() {
		return "Packet [id=" + id + ", size=" + data.length + "]";
	}
}
