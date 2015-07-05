package edu.kit.fsmi.schnupperstudium.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import edu.kit.fsmi.schnupperstudium.chat.common.network.NetworkChannel;
import edu.kit.fsmi.schnupperstudium.chat.common.network.Packet;
import edu.kit.fsmi.schnupperstudium.chat.common.network.PacketOutputStream;

public final class Client {
	private final String host;
	private final int port;
	private NetworkChannel channel;
	
	public Client(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
		this.channel = new NetworkChannel(new Socket(host, port));
	}
	
	public static void main(final String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("<host> <port>");
			return;
		}
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		new Client(host, port);
	}

	public void login(String user, String displayName, String password) throws IOException {
		Objects.requireNonNull(user);
		Objects.requireNonNull(password);
		
		PacketOutputStream out = new PacketOutputStream();
		
		out.writeUTF(user);
		if (displayName == null) {
			out.writeUTF(user);
		} else {
			out.writeUTF(displayName);
		}
		out.writeUTF(password);
		out.close();
		
		Packet packet = out.toPacket(Packet.REQ_AUTH);
		channel.sendPacket(packet);
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}
