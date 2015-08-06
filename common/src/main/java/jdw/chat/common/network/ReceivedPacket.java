package jdw.chat.common.network;

public final class ReceivedPacket extends Packet {
	private final NetworkChannel channel;
	
	public ReceivedPacket(NetworkChannel channel, int id, byte[] data) {
		super(id, data);

		if (channel == null) {
			throw new IllegalArgumentException("channel was null");
		}
		
		this.channel = channel;
	}

	public NetworkChannel getChannel() {
		return channel;
	}
}
