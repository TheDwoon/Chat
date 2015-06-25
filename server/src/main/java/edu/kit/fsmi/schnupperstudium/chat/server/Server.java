package edu.kit.fsmi.schnupperstudium.chat.server;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.User;
import edu.kit.fsmi.schnupperstudium.chat.common.network.ExecutorSet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Network;

public class Server {
	private static final Logger LOG = LogManager.getLogger();
	private static final int PORT = 8965;
	
	private final Network network;
	private final HashMap<String, User> users = new HashMap<>();
	private final ExecutorSet authStage;
	
	public Server(int port) throws IOException {
		this.authStage = new ExecutorSet();
		this.network = new Network((channel) -> { channel.setExecutor(authStage); }, port);
	}
	
	public static void main(final String[] args) {
		ExecutorSet authStage = new ExecutorSet();
		
		authStage.setDefaultExecutor((channel, packet) -> {
			LOG.info("Dropped packet " + packet.getId());
			return true;
		});
	}

}
