package edu.kit.fsmi.schnupperstudium.chat.common;

public enum AttachmentType {
	IMAGE(1),
	FILE(256);
	
	private final int id;
	
	private AttachmentType(final int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
