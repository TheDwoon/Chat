package jdw.chat.server;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdw.chat.common.network.ExecutorSet;
import jdw.chat.common.network.Network;
import jdw.chat.common.network.Packet;
import jdw.chat.server.network.executor.LoginExecutor;

public class Core {
	private static final Logger LOG = LogManager.getLogger();

	public static final int PORT = 8965;
	
	private final Network network;
	private final HashMap<String, User> users = new HashMap<>();
	private final HashMap<String, Conversation> conversations = new HashMap<>();
	
	private final ExecutorSet executors = new ExecutorSet();
	
	public Core() throws IOException {
		this(PORT);
	}
	
	public Core(int port) throws IOException {
		this.network = new Network((channel) -> { channel.setExecutor(executors); }, port);
				
		executors.addExecutor(Packet.REQ_AUTH, new LoginExecutor(this));
		
		executors.setDefaultExecutor((packet) -> {
			LOG.info("Dropped packet " + packet.getId());
		});
	}
	
	public static void main(final String[] args) throws IOException {
		new Core();
	}
	
	public void close() throws IOException {
		network.close();
	}

	public User getUser(String nick) {
		User user = null;
		synchronized (users) {
			user = users.get(nick);

			if (user == null) {
				// TODO load user from file
				user = new User(nick);
				users.put(nick, user);
			}
		}
		
		
		return user;
	}

	public boolean hasConversation(String name) {
		return getConversation(name) != null;
	}
	
	public Conversation getConversation(String name) {
		Conversation conversation = null;
		synchronized (conversations) {
			conversation = conversations.get(name);
			// TODO load from file if null
			
			if (conversation == null) {
				conversation = new Conversation(name, name);
				conversations.put(name, conversation);
			}
		}
		
		return conversation;
	}
}