package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NetworkChannel {
	private static final Logger LOG = LogManager.getLogger();
	
	private final Socket socket;
	private final DataOutputStream output;
	private final DataInputStream input;
	private final List<PacketExecutor> executors;
	private final Network network;
	
	private boolean closed;

	public NetworkChannel(Network network, Socket socket) throws IOException {
		this.network = network;
		this.socket = socket;
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.executors = new ArrayList<>();

		new Thread(new Listener(), "Listener ["
				+ socket.getRemoteSocketAddress().toString() + "]").start();
	}

	public final void sendPacket(Packet packet) throws IOException {
		if (packet == null) {
			return;
		}

		output.writeInt(packet.getId());
		output.writeInt(packet.getData().length);
		output.write(packet.getData());
		output.flush();
	}

	/**
	 * Called whenever a packet is read from this connection.
	 * 
	 * @param packet packet read.
	 */
	private void onPacket(Packet packet) {
		// TODO implement.
	}

	/**
	 * Called when this channel is closed. <code>cause</code> will be set if
	 * this was caused by an expection.
	 * 
	 * @param cause
	 *            exception that caused closing or null.
	 */
	private void onClose(Exception cause) {
		network.removeChannel(this);
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
		
		executors.add(exectuor);
	}
	
	public Network getNetwork() {
		return network;
	}

	private final class Listener implements Runnable {
		@Override
		public void run() {
			while (!socket.isClosed() && socket.isConnected()) {
				Packet packet = null;
				
				try {
					int id = input.readInt();
					int length = input.readInt();
					byte data[] = new byte[length];
					input.readFully(data);		
					
					packet = new Packet(id, data);
				} catch (IOException e) {
					close(e);
				}
				
				try {
					onPacket(packet);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
