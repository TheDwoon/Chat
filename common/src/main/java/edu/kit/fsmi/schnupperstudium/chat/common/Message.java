package edu.kit.fsmi.schnupperstudium.chat.common;


public interface Message {

	String getText();

	long getTimestamp();

	MessageSender getSender();

	MessageReceiver getReceiver();
}
