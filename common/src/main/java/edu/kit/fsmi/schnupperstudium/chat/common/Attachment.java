package edu.kit.fsmi.schnupperstudium.chat.common;

import java.util.Objects;

public abstract class Attachment {
	private AttachmentType type;
	
	public Attachment(AttachmentType type) {
		Objects.requireNonNull(type);
		
		this.type = type;
	}
	
	public final AttachmentType getType() {
		return type;
	}
}
