package edu.kit.fsmi.schnupperstudium.chat.server.network;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.PacketExecutor;

public class LoginExecutor implements PacketExecutor {	
	
	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) {
		// FIXME check password
		
		DataInputStream input = packet.getInputStream();
			
		String nick, password;
		try {
			nick = input.readUTF();
			password = input.readUTF();
		} catch (IOException e) {
			LogManager.getLogger().error(channel + " missing nick and/or password: " + packet.getId());
			return true;
		}
		
		// TODO auth
		
		return true;
	}

}
