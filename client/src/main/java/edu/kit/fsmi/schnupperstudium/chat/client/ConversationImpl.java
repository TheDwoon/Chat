package edu.kit.fsmi.schnupperstudium.chat.client;

import java.awt.image.BufferedImage;
import java.util.List;

import edu.kit.fsmi.schnupperstudium.chat.common.ChatEntity;
import edu.kit.fsmi.schnupperstudium.chat.common.Conversation;
import edu.kit.fsmi.schnupperstudium.chat.common.Message;

public class ConversationImpl implements Conversation {

	private final String name;
	private final String displayName;
	private final String description;
	private final BufferedImage picture;
	private final List<ChatEntity> participants;
	private final List<Message> messages;

	public ConversationImpl(final String name, final String displayName, final String description,
		final BufferedImage picture, final List<ChatEntity> participants,
		final List<Message> messages) {
		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.picture = picture;
		this.participants = participants;
		this.messages = messages;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public BufferedImage getPicture() {
		return picture;
	}

	@Override
	public List<ChatEntity> getParticipants() {
		return participants;
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}

}
