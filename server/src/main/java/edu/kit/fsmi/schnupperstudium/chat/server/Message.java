package edu.kit.fsmi.schnupperstudium.chat.server;

import java.awt.Image;
import java.util.HashMap;

public abstract class Message {
	private final MessageType type;
	
	public Message(MessageType type) {
		if (type == null) {
			throw new IllegalArgumentException("type must not be null");
		}
		
		this.type = type;
	}
	
	public final MessageType getType() {
		return type;
	}
	
	public static enum MessageType {		
		TEXT(1),
		IMAGE(2);
		
		private static HashMap<Integer, MessageType> FROM_ID = new HashMap<>();
		static {
			FROM_ID.put(TEXT.id, TEXT);
			FROM_ID.put(IMAGE.id, IMAGE);
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
