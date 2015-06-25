package edu.kit.fsmi.schnupperstudium.chat.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.network.ExecutorSet;

public class Server {
	private static final Logger LOG = LogManager.getLogger();
	
	private static final int PORT = 8965;
	
	public static void main(final String[] args) {
		ExecutorSet authStage = new ExecutorSet();
		
		authStage.setDefaultExecutor((channel, packet) -> {
			LOG.info("Dropped packet " + packet.getId());
			return true;
		});
		
		authStage.addExecutor(98, (channel, packet) -> {
			
			return true;
		});
	}

}
