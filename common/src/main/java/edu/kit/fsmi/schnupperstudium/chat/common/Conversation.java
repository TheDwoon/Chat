package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Conversation {

	String getName();

	String getDisplayName();

	String getDescription();

	BufferedImage getPicture();

	List<ChatEntity> getParticipants();

	List<Message> getMessages();
}
