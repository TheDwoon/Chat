package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;


public class Packet {
	private final int id;
	private final byte[] data;

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

	public DataInputStream getInputStream() {
		return new DataInputStream(new ByteArrayInputStream(data));
	}
}
