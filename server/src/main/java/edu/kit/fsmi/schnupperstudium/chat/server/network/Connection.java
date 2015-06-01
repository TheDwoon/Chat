package edu.kit.fsmi.schnupperstudium.chat.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;

public class Connection {
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;	
	private Consumer<Packet> consumer;
	
	public Connection(Socket socket, Consumer<Packet> consumer) throws IOException {
		this.socket = socket;
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.consumer = consumer;
	}
	
	public void sendPacket(Packet packet) throws IOException {
		if (packet == null) {
			return;
		}
		
		output.writeInt(packet.getId());
		output.writeInt(packet.getData().length);
		output.write(packet.getData());
		output.flush();
	}
	
	
}
