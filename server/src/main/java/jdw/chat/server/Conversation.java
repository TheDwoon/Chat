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
	
	public void addUser(User user) {
		if (user == null) {
			return;
		}
		
		synchronized (users) {
			users.add(user);
		}
	}
	
	public boolean containsUser(User user) {
		synchronized (users) {			
			return users.contains(users);
		}
	}
	
	public void removeUser(User user) {
		synchronized (users) {
			users.remove(user);
		}
	}
	
	public void addMessage(Message message) {
		if (message == null) {
			return;
		}
		
		synchronized (messages) {
			messages.add(message);
		}
	}
	
	public List<Message> getMessages() {
		return new ArrayList<>(messages);
	}
	
	public List<Message> getMessages(int limit) {
		if (limit < 0) {
			throw new IllegalArgumentException("limit was less than zero");
		}
		
		int count = Math.min(limit, messages.size());
		
		List<Message> list = new ArrayList<>(count);
		synchronized (messages) {
			int size = messages.size();
			
			for (int i = 0; i < count; i++) {
				list.add(messages.get(size - i - 1));
			}
		}
		
		return list;
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