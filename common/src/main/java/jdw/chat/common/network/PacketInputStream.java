package jdw.chat.common.network;

import java.io.IOException;
import java.io.InputStream;

public class PacketInputStream extends InputStream {
	private final byte[] data;
	
	private int pos = 0;
	private int mark = 0;
	private int readlimit = Integer.MAX_VALUE;
	
	public PacketInputStream(Packet packet) {
		if (packet == null) {
			throw new IllegalArgumentException("packet was null");
		}
		
		this.data = packet.getData();
	}
	
	@Override
	public final int read() throws IOException {
		int i = 0;
		
		if (pos < data.length) {
			i = data[pos++];
			
			if ((long) mark + readlimit < pos) {
				mark = 0;
				readlimit = Integer.MAX_VALUE;
			}
		} else {
			i = -1;
		}
		
		return i;
	}
	
	@Override
	public int available() throws IOException {
		return data.length - pos;
	}
	
	@Override
	public synchronized void reset() throws IOException {
		pos = mark;
	}
	
	@Override
	public synchronized void mark(int readlimit) {
		this.mark = pos;
		this.readlimit = readlimit;
	}
	
	@Override
	public boolean markSupported() {
		return true;
	}
}
