package jdw.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdw.chat.common.network.Packet;
import jdw.chat.common.network.ReceivedPacket;
import jdw.chat.server.Core;
import jdw.chat.server.User;

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
		
		User user = server.getUser(name);	
		if (user.hasPassword()) {
			if (!user.getPassword().equals(password)) {
				LOG.info("Failed login for: " + user.getUser());
				return;
			}
		} else {
			user.setPassword(password);
		}
		
		user.setNick(displayName);
		 
		packet.getChannel().getConfiguration().set("user", user);
		
		packet.getChannel().sendPacket(new Packet(Packet.RPL_AUTH, 1));
		
		LOG.info(user.getUser() + " logged in as " + user.getNick());
	}

}
