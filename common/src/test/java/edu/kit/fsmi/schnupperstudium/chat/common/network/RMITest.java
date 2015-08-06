package edu.kit.fsmi.schnupperstudium.chat.common.network;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jdw.chat.common.network.ExecutorSet;
import jdw.chat.common.network.Network;
import jdw.chat.common.network.NetworkChannel;
import jdw.chat.common.network.Packet;
import jdw.chat.common.network.RMIAdapter;

public final class RMITest {
	private static final int PORT = 6184;
	
	private volatile boolean called = false;
	
	private volatile Network network;
	private volatile NetworkChannel client;
	private volatile NetworkChannel server;
	
	@Before
	public void setUp() throws IOException, InterruptedException {
		network = new Network((channel) -> {			
			try {
				ExecutorSet executors = new ExecutorSet();
				executors.addExecutor(1, new RMIAdapter(getClass().getMethod("voidCall"), this));
				executors.addExecutor(2, new RMIAdapter(getClass().getMethod("simpleCall", int.class), this));
				
				channel.addExecutor(executors);
			} catch (Exception e) {				
				e.printStackTrace();	
			}			
			
			server = channel;
		}, PORT);
		
		client = new NetworkChannel(new Socket("127.0.0.1", PORT));
		
		while (server == null) {
			Thread.sleep(1);
		}
	}

	@Test (timeout = 1000)
	public void voidCheck() throws InterruptedException {
		Packet packet = new Packet(1, new byte[0]);
		client.sendPacket(packet);
		
		while (!called) {
			Thread.sleep(1);
		}
		
		assertTrue(called);
	}
	
	@Test (timeout = 1000)
	public void simpleCheck() throws InterruptedException {
		
		while (!called) {
			Thread.sleep(1);
		}
		
		assertTrue(called);
	}
	
	@After
	public void tearDown() throws IOException {
		network.close();
	}
	
	public void voidCall() {
		called = true;
	}
	
	public void simpleCall(int magicValue) {
		called = true;
	}
}
