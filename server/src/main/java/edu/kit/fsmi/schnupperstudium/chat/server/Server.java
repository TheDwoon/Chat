package edu.kit.fsmi.schnupperstudium.chat.server;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.Conversation;
import edu.kit.fsmi.schnupperstudium.chat.common.User;
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
	private final ExecutorSet authStage = new ExecutorSet();
	private final ExecutorSet userStage = new ExecutorSet();
	
	public Server() throws IOException {
		this(PORT);
	}
	
	public Server(int port) throws IOException {
		this.network = new Network((channel) -> { channel.setExecutor(authStage); }, port);
				
		authStage.addExecutor(Packet.REQ_AUTH, new LoginExecutor(this));
		
		authStage.setDefaultExecutor((channel, packet) -> {
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

	public Conversation getConversation(String name) {
		Conversation conversation = null;
		synchronized (conversations) {
			conversation = conversations.get(name);
			// TODO load from file if null
		}
		
		return conversation;
	}
	
	public Conversation createConversation(String name) {
		Conversation conversation = new Conversation(name, name);
		
		synchronized (conversations) {
			conversations.put(name, conversation);
		}
		
		return conversation;
	}
}
