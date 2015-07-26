package edu.kit.fsmi.schnupperstudium.chat.server;

import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;

public final class User {
	private final String user;
	private String nick;
	private NetworkChannel channel;
	
	public User(String user) {
		this(user, user);
	}
	
	public User(String user, String nick) {
		if (user == null || user.isEmpty()) {
			throw new IllegalArgumentException("user was null or empty");
		}
		
		this.user = user;
		if (nick == null) {
			this.nick = user;
		} else {
			this.nick = nick;
		}
	}

	public void kick() {
		if (channel == null) {
			return;
		}
		
		// TODO implement.
	}
	
	protected void setChannel(NetworkChannel channel) {
		this.channel = channel;
	}
	
	/**
	 * @return unique name
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @return display name
	 */
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
}
