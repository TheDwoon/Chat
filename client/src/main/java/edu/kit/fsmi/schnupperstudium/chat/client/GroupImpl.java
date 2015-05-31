package edu.kit.fsmi.schnupperstudium.chat.client;

import java.awt.image.BufferedImage;
import java.util.List;

import edu.kit.fsmi.schnupperstudium.chat.common.Group;
import edu.kit.fsmi.schnupperstudium.chat.common.User;

public class GroupImpl implements Group {

	private final String name;
	private final String displayName;
	private final List<User> members;
	private final BufferedImage picture;

	public GroupImpl(final String name, final String displayName, final List<User> members,
		final BufferedImage picture) {
		this.name = name;
		this.displayName = displayName;
		this.members = members;
		this.picture = picture;
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
	public List<User> getMembers() {
		return members;
	}

	@Override
	public BufferedImage getPicture() {
		return picture;
	}

}
