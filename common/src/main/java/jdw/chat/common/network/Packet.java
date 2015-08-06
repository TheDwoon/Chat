package jdw.chat.common.network;

import java.io.ByteArrayInputStream;


public class Packet {
	public static final int REQ_AUTH = 1;
	public static final int RPL_AUTH = 2;
	
	protected final int id;
	protected final byte[] data;

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

	public final int getId() {
		return id;
	}
		
	public final byte[] getData() {
		return data;
	}

	public final PacketInputStream getInputStream() {
		return new PacketInputStream(new ByteArrayInputStream(data));
	}
	
	@Override
	public String toString() {
		return "Packet [id=" + id + ", size=" + data.length + "]";
	}
}
