package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;

public class ChatEntity {
	private String name;
	private String displayName;
	private String description;
	private BufferedImage picture;
	
	public ChatEntity(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public BufferedImage getPicture() {
		return picture;
	}

	public void setPicture(BufferedImage picture) {
		this.picture = picture;
	}
}
