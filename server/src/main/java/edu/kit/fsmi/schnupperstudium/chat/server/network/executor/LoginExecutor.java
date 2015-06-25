package edu.kit.fsmi.schnupperstudium.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

import edu.kit.fsmi.schnupperstudium.chat.common.User;
import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.PacketExecutor;

public class LoginExecutor implements PacketExecutor {	
	
	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) {		
		DataInputStream input = packet.getInputStream();
			
		String user, nick, password;
		try {
			user = input.readUTF();
			nick = input.readUTF();
			password = input.readUTF();
		} catch (IOException e) {
			LogManager.getLogger().error(channel + " missing nick and/or password: " + packet.getId());
			return true;
		}
		
		// FIXME check password
		channel.getConfiguration().set("user", new User(user, nick));
		
		// TODO change stage to authed.
		
		return true;
	}

}
