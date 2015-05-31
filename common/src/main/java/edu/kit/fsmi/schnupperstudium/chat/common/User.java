package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;

public interface User extends MessageSender, MessageReceiver {

	@Override
	String getName();

	@Override
	String getDisplayName();

	String getEmail();

	String getDescription();

	@Override
	BufferedImage getPicture();
}
