package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class PacketOutputStream extends DataOutputStream {
	private static final int INITIAL_SIZE = 64;
	
	public PacketOutputStream() {
		super(new ByteArrayOutputStream(INITIAL_SIZE));
	}

	public <T> void writeList(List<T> list, BinarySerializer<T> serialzier) throws IOException {
		if (list == null) {
			writeShort(0);
			return;
		}
		
		writeShort(list.size());
		for (T element : list) {
			serialzier.serialize(this, element);
		}
	}
	
	public Packet toPacket(int packetId) {
		byte[] data = ((ByteArrayOutputStream) out).toByteArray();
		
		return new Packet(packetId, data);
	}
}
