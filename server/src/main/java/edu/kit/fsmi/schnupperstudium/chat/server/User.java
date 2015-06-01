package edu.kit.fsmi.schnupperstudium.chat.server;


public class User extends ChatEntity {
	private String email;
	
	public User(String name, String displayName) {
		super(name, displayName);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
