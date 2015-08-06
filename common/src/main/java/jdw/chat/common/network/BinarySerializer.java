package jdw.chat.common.network;

import java.io.DataOutputStream;
import java.io.IOException;

public interface BinarySerializer<T> {
	void serialize(DataOutputStream output, T object) throws IOException;
}
