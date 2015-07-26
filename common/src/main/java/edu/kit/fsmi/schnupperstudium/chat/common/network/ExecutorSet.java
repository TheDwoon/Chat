package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.IOException;
import java.util.HashMap;

public class ExecutorSet implements PacketExecutor {
	private HashMap<Integer, PacketExecutor> executors;
	private PacketExecutor defaultExecutor;
	
	public ExecutorSet() {
		executors = new HashMap<>();		
	}
	
	public void setDefaultExecutor(PacketExecutor defaultExecutor) {
		this.defaultExecutor = defaultExecutor;
	}

	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) throws IOException {
		if (packet == null) {
			return false;
		}
		
		PacketExecutor executor = executors.get(packet.getId());
		
		if (executor == null) {
			executor = defaultExecutor;
		}
		
		if (defaultExecutor != null) {
			return executor.executePacket(channel, packet);
		}
		
		return false;
	}
	
	public void addExecutor(int packetId, PacketExecutor executor) {
		executors.put(packetId, executor);
	}
	
	public void removeExecutor(int packetId) {
		executors.remove(packetId);
	}
}
