package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PacketInputStream extends DataInputStream {

	public PacketInputStream(InputStream in) {
		super(in);
	}

	public <T> List<T> readList(BinaryDeserializer<T> deserialzer) throws IOException {
		List<T> list = new ArrayList<>();
		
		int length = readUnsignedShort();
		for (int i = 0; i < length; i++) {
			list.add(deserialzer.deserialize(this));
		}
		
		return list;
	}
}
