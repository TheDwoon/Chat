package edu.kit.fsmi.schnupperstudium.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.User;
import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.PacketExecutor;
import edu.kit.fsmi.schnupperstudium.chat.server.Server;

public class LoginExecutor implements PacketExecutor {	
	private static final Logger LOG = LogManager.getLogger();
	
	private final Server server;
	
	public LoginExecutor(Server server) {
		this.server = server;
	}
	
	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) {		
		DataInputStream input = packet.getInputStream();
			
		String name, displayName, password;
		try {
			name = input.readUTF();
			displayName = input.readUTF();
			password = input.readUTF();
		} catch (IOException e) {
			LogManager.getLogger().error(channel + " missing nick and/or password: " + packet.getId());
			return true;
		}
		
		if (name.isEmpty() || password.isEmpty()) {
			channel.sendPacket(new Packet(Packet.RPL_AUTH, 0));
			return true;
		}
		
		LOG.info(String.format("name=%s, displayName=%s, password=%s", name, displayName, password));
		
		// FIXME check password
		User user = server.getUser(name);		
		user.setDisplayName(displayName);
		 
		channel.getConfiguration().set("user", user);
		
		channel.sendPacket(new Packet(Packet.RPL_AUTH, 1));
	
		return true;
	}

}
