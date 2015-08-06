package jdw.chat.server;

import jdw.chat.common.network.NetworkChannel;

public final class User {
	private final String user;
	
	private String nick;
	private String password;
	
	private NetworkChannel channel;
	
	public User(String user) {
		this(user, user);
	}
	
	public User(String user, String nick) {
		this(user, nick, null);
	}
	
	public User(String user, String nick, String password) {
		if (user == null || user.isEmpty()) {
			throw new IllegalArgumentException("user was null or empty");
		}
		
		this.user = user;
		if (nick == null) {
			this.nick = user;
		} else {
			this.nick = nick;
		}
		this.password = password;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasPassword() {
		return password != null && !password.isEmpty();
	}
}
