package jdw.chat.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Conversation {
	private final List<User> users = new ArrayList<>();
	private final List<Message> messages = new LinkedList<>();
	private final String name;
	private String displayName;
	
	public Conversation(String name, String displayName) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name was null or empty");
		}
		
		this.name = name;
		if (displayName == null) {
			this.displayName = "Conversation";
		} else {
			this.displayName = displayName;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}