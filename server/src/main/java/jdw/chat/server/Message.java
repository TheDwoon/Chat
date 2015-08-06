package jdw.chat.server;

import java.util.HashMap;

public abstract class Message {
	private final long timestamp;
	private final MessageType type;
	
	public Message(MessageType type, long timestamp) {
		if (type == null) {
			throw new IllegalArgumentException("type must not be null");
		}
		
		this.type = type;
		this.timestamp = timestamp;
	}
	
	public final long getTimestamp() {
		return timestamp;
	}
	
	public final MessageType getType() {
		return type;
	}
	
	public static enum MessageType {		
		TEXT(1),
		IMAGE(2),
		FILE(3);
		
		private static HashMap<Integer, MessageType> FROM_ID = new HashMap<>();
		static {
			FROM_ID.put(TEXT.id, TEXT);
			FROM_ID.put(IMAGE.id, IMAGE);
			FROM_ID.put(FILE.id, FILE);
		}
		
		public static MessageType fromId(int id) {
			return FROM_ID.get(id);
		}
		
		public final int id;
		
		private MessageType(int id) {
			this.id = id;
		}
	}
}
