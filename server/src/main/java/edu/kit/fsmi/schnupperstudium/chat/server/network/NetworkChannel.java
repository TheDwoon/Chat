package edu.kit.fsmi.schnupperstudium.chat.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NetworkChannel {
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;

	private boolean closed;

	public NetworkChannel(Socket socket) throws IOException {
		this.socket = socket;
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());

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
		// TODO implement.
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

		closed = true;
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
