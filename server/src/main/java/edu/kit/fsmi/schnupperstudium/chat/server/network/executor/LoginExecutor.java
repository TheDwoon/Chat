package edu.kit.fsmi.schnupperstudium.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.ReceivedPacket;
import edu.kit.fsmi.schnupperstudium.chat.server.Core;
import edu.kit.fsmi.schnupperstudium.chat.server.User;

public class LoginExecutor implements Consumer<ReceivedPacket> {	
	private static final Logger LOG = LogManager.getLogger();
	
	private final Core server;
	
	public LoginExecutor(Core server) {
		this.server = server;
	}
	
	@Override
	public void accept(ReceivedPacket packet) {		
		DataInputStream input = packet.getInputStream();
			
		String name, displayName, password;
		
		try {
			name = input.readUTF();			
			displayName = input.readUTF();
			password = input.readUTF();
		} catch (IOException e) {
			LOG.warn("Malformed packet received: " + packet.getChannel());
			return;
		}

		
		if (name.isEmpty() || password.isEmpty()) {
			packet.getChannel().sendPacket(new Packet(Packet.RPL_AUTH, 0));
			return;
		}
		
		LOG.info(String.format("name=%s, displayName=%s, password=%s", name, displayName, password));
		
		// FIXME check password
		User user = server.getUser(name);		
		user.setNick(displayName);
		 
		packet.getChannel().getConfiguration().set("user", user);
		
		packet.getChannel().sendPacket(new Packet(Packet.RPL_AUTH, 1));
	}

}
