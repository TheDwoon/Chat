package edu.kit.fsmi.schnupperstudium.chat.client;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import edu.kit.fsmi.schnupperstudium.chat.common.Message;
import edu.kit.fsmi.schnupperstudium.chat.common.MessageReceiver;
import edu.kit.fsmi.schnupperstudium.chat.common.MessageSender;

public class MessageImpl implements Message {

	private final String text;
	private final long timestamp;
	private final MessageSender sender;
	private final MessageReceiver receiver;

	public MessageImpl(final String text, final long timestamp, final MessageSender sender,
		final MessageReceiver receiver) {
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

	public LocalDateTime getTimestampLocalDateTime() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
	}

	@Override
	public MessageSender getSender() {
		return sender;
	}

	@Override
	public MessageReceiver getReceiver() {
		return receiver;
	}

}
