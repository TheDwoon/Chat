package jdw.chat.common.network;

import java.util.HashMap;
import java.util.function.Consumer;

public class ExecutorSet implements Consumer<ReceivedPacket> {
	private HashMap<Integer, Consumer<ReceivedPacket>> executors;
	private Consumer<ReceivedPacket> defaultExecutor;
	
	public ExecutorSet() {
		executors = new HashMap<>();		
	}
	
	public void setDefaultExecutor(Consumer<ReceivedPacket> defaultExecutor) {
		this.defaultExecutor = defaultExecutor;
	}

	@Override
	public void accept(ReceivedPacket packet) {
		if (packet == null) {
			return;
		}
		
		Consumer<ReceivedPacket> executor = executors.get(packet.getId());
		
		if (executor == null) {
			executor = defaultExecutor;
		}
		
		if (defaultExecutor != null) {
			executor.accept(packet);;
		}	
	}
	
	public void addExecutor(int packetId, Consumer<ReceivedPacket> executor) {
		executors.put(packetId, executor);
	}
	
	public void removeExecutor(int packetId) {
		executors.remove(packetId);
	}
}
