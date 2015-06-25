package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;
import java.util.List;

public class Conversation {
	private String name;
	private String displayName;
	private String description;
	private BufferedImage picture;
	private List<ChatEntity> participants;
	private List<Message> messages;
	
	public Conversation(String name, String displayName) {
		this.name = name; 
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public BufferedImage getPicture() {
		return picture;
	}
	
	public List<ChatEntity> getParticipants() {
		return participants;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}
