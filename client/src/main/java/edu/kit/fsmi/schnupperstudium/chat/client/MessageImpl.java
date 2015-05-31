package edu.kit.fsmi.schnupperstudium.chat.client;

import edu.kit.fsmi.schnupperstudium.chat.common.ChatEntity;
import edu.kit.fsmi.schnupperstudium.chat.common.Message;

public class MessageImpl implements Message {

	private final String text;
	private final long timestamp;
	private final ChatEntity sender;
	private final ChatEntity receiver;

	public MessageImpl(final String text, final long timestamp, final ChatEntity sender,
		final ChatEntity receiver) {
		this.text = text;
		this.timestamp = timestamp;
		this.sender = sender;
		this.receiver = receiver;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public ChatEntity getSender() {
		return sender;
	}

	@Override
	public ChatEntity getReceiver() {
		return receiver;
	}

}
