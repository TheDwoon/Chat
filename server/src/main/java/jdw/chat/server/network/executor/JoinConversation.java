package jdw.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdw.chat.common.network.PacketExecutor;
import jdw.chat.common.network.ReceivedPacket;
import jdw.chat.server.Conversation;
import jdw.chat.server.Server;
import jdw.chat.server.User;

public final class JoinConversation implements PacketExecutor {
	private final Server server;
	
	public JoinConversation(Server server) {
		this.server = server;
	}
	
	@Override
	public void handlePacket(ReceivedPacket packet) throws IOException {
		User user = packet.getChannel().getConfiguration().get(User.class, "user");
		if (user == null) {
			return;
		}
		
		DataInputStream input = new DataInputStream(packet.getInputStream());
		
		String conversationId = input.readUTF();
		String password = null;
		if (input.available() > 0) {
			password = input.readUTF();
		}
		
		Conversation conversation = server.getConversation(conversationId);
		
		if (conversation.hasPassword() && password == null) {
			return;
		} else if (conversation.hasPassword() && !conversation.getPassword().equals(password)) {
			return;
		}
		
		conversation.addUser(user);
		
		input.close();
		
		Server.LOG.info(user.getNick() + " [" + user.getUser() + "] joined " 
				+ conversation.getDisplayName() + " [" + conversationId + "]");
	}
}
