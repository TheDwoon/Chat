package edu.kit.fsmi.schnupperstudium.chat.server;

import edu.kit.fsmi.schnupperstudium.chat.common.network.Stage;

public class Server {
	private static final int PORT = 8965;
	
	public static void main(final String[] args) {
		Stage authStage = new Stage();
		
		authStage.addExecutor(98, (channel, packet) -> {
			
			return true;
		});
	}

}
