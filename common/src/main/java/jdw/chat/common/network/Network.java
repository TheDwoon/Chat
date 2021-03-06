package jdw.chat.common.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Network {
	private static Logger LOG = LogManager.getLogger();
	
	private final int port;
	private final ServerSocket server;
	private final NetworkListener listener;
	private final List<NetworkChannel> channels;
	
	private ChannelInitializer initializer;
	
	public Network(ChannelInitializer initializer, int port) throws IOException {
		this.initializer = initializer;
		this.port = port;
		this.channels = new LinkedList<>();
		this.server = new ServerSocket(port);
		this.listener = new NetworkListener();
		this.listener.start();
	}
	
	void removeChannel(NetworkChannel channel) {
		synchronized (channels) {
			channels.remove(channel);
		}
	}
	
	public int getPort() {
		return port;
	}
	
	private final class NetworkListener extends Thread {
		private NetworkListener() {
			setName("Network Listener");
		}
		
		@Override
		public void run() {
			LOG.info("Network bound to port " + port + "!");
			
			while (!isInterrupted() && !server.isClosed()) {
				try {
					server.setSoTimeout(1000);
				} catch (SocketException e1) {
					LOG.fatal("failed to set timeout on server socket");					
				}
				
				Socket socket = null; 
				try {
					socket = server.accept();
				} catch (SocketTimeoutException e) {
					// ignore
					continue;
				} catch (IOException e) {
					LOG.fatal("server socket broke down: " + e.getMessage());
					break;
				}
				
				try {
					NetworkChannel channel = new NetworkChannel(Network.this, socket);
					initializer.initChannel(channel);
					
					synchronized (channels) {
						channels.add(channel);
					}
				} catch (IOException e) {
					LOG.warn("Failed to init channel!");
				}
			}
			
			LOG.info("Network on port " + port + " shutdown!");
		}
	}

	public void close() throws IOException {
		LOG.info("Stopping accepting connections");
		
		listener.interrupt();		
		server.close();
		
		LOG.info("Closing opened connections...");
		synchronized (channels) {
			for (NetworkChannel channel : channels) {
				channel.close();
			}
		}
		
		LOG.info("Network shutdown complete!");
	}
}
