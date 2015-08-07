package jdw.chat.common.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdw.chat.common.config.MemoryConfiguration;


public class NetworkChannel {
	private static final Logger LOG = LogManager.getLogger();
	
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	private final List<PacketExecutor> executors;
	private final Network network;
	private final MemoryConfiguration configuration;
	
	private boolean closed;

	public NetworkChannel(Socket socket) throws IOException {
		this(null, socket);
	}
	
	public NetworkChannel(Network network, Socket socket) throws IOException {
		this.network = network;
		this.socket = socket;
		this.configuration = new MemoryConfiguration();
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.executors = new ArrayList<>();

		new Thread(new Listener(), "Listener ["
				+ socket.getRemoteSocketAddress().toString() + "]").start();
	}

	/** 
	 * Writes a packet.
	 * This method will block until the packet was sent.
	 * 
	 * @param packet packet to send.
	 * @throws IOException if an I/O error occurs.
	 */
	public final boolean sendPacket(Packet packet) {
		if (packet == null) {
			return true;
		}

		try {
			output.writeInt(packet.getId());
			output.writeInt(packet.getData().length);
			output.write(packet.getData());
			output.flush();
		} catch (IOException e) {
			close(e);
			return false;
		}
		
		return true;
	}

	/**
	 * Called whenever a packet is read from this connection.
	 * 
	 * @param packet packet read.
	 */
	private void onPacket(ReceivedPacket packet) {
		if (packet == null) {
			return;
		}
		
		synchronized (executors) {
			for (PacketExecutor executor : executors) {
				try {
					executor.handlePacket(packet);
				} catch (IOException e) {
					LOG.error("Error executing packet "  + packet.getId() + ": " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Called when this channel is closed. <code>cause</code> will be set if
	 * this was caused by an expection.
	 * 
	 * @param cause
	 *            exception that caused closing or null.
	 */
	private void onClose(Exception cause) {
		if (network != null) {
			network.removeChannel(this);
		}
	}

	public final void close() {
		close(null);
	}
	
	private final void close(Exception cause) {
		if (closed) {
			return;
		}

		try {
			socket.close();
			input.close();
			output.close();

			onClose(null);
		} catch (IOException e) {
			onClose(e);
		}

		LOG.info("[" + socket.getRemoteSocketAddress() + "] connection closed: " 
				+ ((cause == null) ? "quit" : cause.getMessage()));
		
		closed = true;
	}

	public void addExecutor(PacketExecutor exectuor) {
		if (exectuor == null) {
			return;
		}
		
		synchronized (executors) {
			executors.add(exectuor);			
		}
	}
	
	/**
	 * Removes all executors and adds the given one.
	 * If <code>executor</code> is null it will only 
	 * remove all executors.
	 * 
	 * @param executor executor or null.
	 */
	public void setExecutor(PacketExecutor executor) {
		synchronized (executors) {
			executors.clear();
			
			if (executor != null) {
				executors.add(executor);
			}			
		}
	}
	
	public void removeExecutor(PacketExecutor executor) {
		if (executor == null) {
			return;
		}
		
		synchronized (executors) {
			executors.remove(executor);
		}
	}
	
	/**
	 * @return associated network or null.
	 */
	public Network getNetwork() {
		return network;
	}

	public MemoryConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String toString() {		
		return "[" + socket.getRemoteSocketAddress() + "]";
	}
	
	private final class Listener implements Runnable {
		@Override
		public void run() {
			while (!socket.isClosed() && socket.isConnected()) {
				ReceivedPacket packet = null;
				
				try {
					int id = input.readInt();
					int length = input.readInt();
					byte data[] = new byte[length];
					input.readFully(data);		
					
					packet = new ReceivedPacket(NetworkChannel.this, id, data);
				} catch (IOException e) {
					close(e);
					break;
				}
				
				try {
					onPacket(packet);
				} catch (RuntimeException e) {
					LOG.catching(Level.ERROR, e);
				}
			}
		}
	}
}
