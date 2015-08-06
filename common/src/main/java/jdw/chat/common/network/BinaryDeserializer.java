package jdw.chat.common.network;

import java.io.DataInputStream;
import java.io.IOException;

public interface BinaryDeserializer<T> {
	T deserialize(DataInputStream output) throws IOException;
}
