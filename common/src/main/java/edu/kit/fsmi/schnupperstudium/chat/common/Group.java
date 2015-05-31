package edu.kit.fsmi.schnupperstudium.chat.common;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Group extends MessageReceiver {

	@Override
	String getName();

	@Override
	String getDisplayName();

	List<User> getMembers();

	BufferedImage getPicture();

}
