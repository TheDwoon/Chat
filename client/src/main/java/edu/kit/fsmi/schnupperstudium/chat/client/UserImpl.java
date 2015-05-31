package edu.kit.fsmi.schnupperstudium.chat.client;

import java.awt.image.BufferedImage;

import edu.kit.fsmi.schnupperstudium.chat.common.User;

public class UserImpl implements User {

	private final String name;
	private final String displayName;
	private final String email;
	private final String description;
	private final BufferedImage picture;

	public UserImpl(final String name, final String displayName, final String email,
		final String description, final BufferedImage picture) {
		this.name = name;
		this.displayName = displayName;
		this.email = email;
		this.description = description;
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
	public String getEmail() {
		return email;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public BufferedImage getPicture() {
		return picture;
	}

}
