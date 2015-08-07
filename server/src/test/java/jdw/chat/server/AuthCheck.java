package jdw.chat.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jdw.chat.common.network.NetworkChannel;
import jdw.chat.common.network.Packet;
import jdw.chat.common.network.PacketOutputStream;
import jdw.chat.server.Server;
import jdw.chat.server.User;

public class AuthCheck {	
	private Server server;
	
	@Before
	public void launchServer() throws IOException {
		server = new Server();
	}
	
	@Test
	public void checkAuth() throws IOException, InterruptedException {
		Socket socket = new Socket("127.0.0.1", Server.PORT);
		NetworkChannel channel = new NetworkChannel(socket);
		
		PacketOutputStream pos = new PacketOutputStream();
		
		pos.writeUTF("daniel");
		pos.writeUTF("TheDwoon");
		pos.writeUTF("complexPassword");
		pos.flush();
		
		Packet packet = pos.toPacket(Packet.REQ_AUTH);
		pos.close();
		
		channel.sendPacket(packet);
		
		Thread.sleep(10);
		
		User user = server.getUser("daniel");
		
		assertNotNull(user);
			
		assertEquals("daniel", user.getUser());
		assertEquals("TheDwoon", user.getNick());
	}
	
	@After
	public void stopServer() throws IOException {
		server.close();
	}
}
