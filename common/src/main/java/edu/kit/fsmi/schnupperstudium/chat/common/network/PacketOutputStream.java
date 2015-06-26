package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PacketOutputStream extends DataOutputStream {	
	public PacketOutputStream(OutputStream out) {
		super(out);
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
}
