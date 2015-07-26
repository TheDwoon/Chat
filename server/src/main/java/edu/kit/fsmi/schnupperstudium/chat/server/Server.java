package edu.kit.fsmi.schnupperstudium.chat.server;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.Conversation;
import edu.kit.fsmi.schnupperstudium.chat.common.network.ExecutorSet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Network;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.server.network.executor.LoginExecutor;

public class Server {
	private static final Logger LOG = LogManager.getLogger();

	public static final int PORT = 8965;
	
	private final Network network;
	private final HashMap<String, User> users = new HashMap<>();
	private final HashMap<String, Conversation> conversations = new HashMap<>();
	private final ExecutorSet executors = new ExecutorSet();
	
	public Server() throws IOException {
		this(PORT);
	}
	
	public Server(int port) throws IOException {
		this.network = new Network((channel) -> { channel.setExecutor(executors); }, port);
				
		executors.addExecutor(Packet.REQ_AUTH, new LoginExecutor(this));
		
		executors.setDefaultExecutor((channel, packet) -> {
			LOG.info("Dropped packet " + packet.getId());
			return true;
		});
	}
	
	public static void main(final String[] args) throws IOException {
		new Server();
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
