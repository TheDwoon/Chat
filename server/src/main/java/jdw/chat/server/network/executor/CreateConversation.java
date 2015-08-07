package jdw.chat.server.network.executor;

import java.io.DataInputStream;
import java.io.IOException;

import jdw.chat.common.network.PacketExecutor;
import jdw.chat.common.network.ReceivedPacket;
import jdw.chat.server.Conversation;
import jdw.chat.server.Core;

public final class CreateConversation implements PacketExecutor {
	private final Core core;
	
	public CreateConversation(Core core) {
		this.core = core;
	}
	
	@Override
	public void handlePacket(ReceivedPacket packet) throws IOException {
		DataInputStream input = new DataInputStream(packet.getInputStream());
		
		String conversationId = input.readUTF();
		String conversationName = input.readUTF();
		String password = null;
		if (input.available() > 0) {
			password = input.readUTF();
		}
		
		if (core.hasConversation(conversationId)) {
			return;
		}
		
		// TODO maybe check password :D
		Conversation conversation = new Conversation(conversationId, conversationName);		
		core.addConversation(conversation);
		
		input.close();
	}



}
