package edu.kit.fsmi.schnupperstudium.chat.server;


public final class Message {
	private String text;
	private Attachment attachment;
	private long timestamp;
	private ChatEntity sender;
	
	public Message(ChatEntity sender, String text, long timestamp) {
		this.sender = sender;
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Attachment getAttachment() {
		return attachment;
	}
	
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public ChatEntity getSender() {
		return sender;
	}
	
	public void setSender(ChatEntity sender) {
		this.sender = sender;
	}
}
