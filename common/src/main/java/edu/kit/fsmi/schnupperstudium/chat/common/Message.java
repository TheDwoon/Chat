package edu.kit.fsmi.schnupperstudium.chat.common;

public interface Message {

	String getText();

	Attachement getAttachement();

	long getTimestamp();

	ChatEntity getSender();

	ChatEntity getReceiver();
}
