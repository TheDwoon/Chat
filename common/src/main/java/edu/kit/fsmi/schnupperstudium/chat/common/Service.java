package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;

public interface Service extends MessageSender, MessageReceiver {

	@Override
	String getName();

	@Override
	String getDisplayName();

	String getDescription();

	@Override
	BufferedImage getPicture();

}
