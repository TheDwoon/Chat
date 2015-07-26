package edu.kit.fsmi.schnupperstudium.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.PacketExecutor;
import edu.kit.fsmi.schnupperstudium.chat.server.Server;
import edu.kit.fsmi.schnupperstudium.chat.server.User;

public class LoginExecutor implements PacketExecutor {	
	private static final Logger LOG = LogManager.getLogger();
	
	private final Server server;
	
	public LoginExecutor(Server server) {
		this.server = server;
	}
	
	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) throws IOException {		
		DataInputStream input = packet.getInputStream();
			
		String name = input.readUTF();
		String displayName = input.readUTF();
		String password = input.readUTF();

		
		if (name.isEmpty() || password.isEmpty()) {
			channel.sendPacket(new Packet(Packet.RPL_AUTH, 0));
			return true;
		}
		
		LOG.info(String.format("name=%s, displayName=%s, password=%s", name, displayName, password));
		
		// FIXME check password
		User user = server.getUser(name);		
		user.setNick(displayName);
		 
		channel.getConfiguration().set("user", user);
		
		channel.sendPacket(new Packet(Packet.RPL_AUTH, 1));
	
		return true;
	}

}
