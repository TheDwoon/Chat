package edu.kit.fsmi.schnupperstudium.chat.common.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Deprecated
public final class RMIAdapter implements PacketExecutor {
	private static final HashMap<Class<?>, Function<DataInputStream, Object>> parsers = new HashMap<>();
	private static final Logger LOG = LogManager.getLogger();
	
	static {
		parsers.put(Integer.class, (input) -> { 
			try {
				return input.readInt();
			} catch (IOException e) {
				throw new RuntimeException("failed to parse integer");
			} 
		});		
		parsers.put(int.class, parsers.get(Integer.class));
		
		parsers.put(Float.class, (input) -> {
			try {
				return input.readFloat();
			} catch (IOException e) {
				throw new RuntimeException("failed to parse float");
			}
		});
		parsers.put(float.class, parsers.get(Float.class));
		
		parsers.put(Double.class, (input) -> {
			try {
				return input.readDouble();
			} catch (IOException e) {
				throw new RuntimeException("failed to parse double");
			}
		});
		parsers.put(double.class, parsers.get(Double.class));
		
		parsers.put(Boolean.class, (input) -> {
			try {
				return input.readBoolean();				
			} catch (IOException e) {
				throw new RuntimeException("failed to parse boolean");
			}
		});
		parsers.put(boolean.class, parsers.get(Boolean.class));
		
		parsers.put(String.class, (input) -> {
			try {
				return input.readUTF();				
			} catch (IOException e) {
				throw new RuntimeException("failed to parse string");
			}
		});
	}
	
	private final Method method;
	private final Class<?>[] parameterTypes;
	
	public RMIAdapter(Method method) {
		this.method = method;
		this.parameterTypes = method.getParameterTypes();
		
		if (parameterTypes.length < 1 || !NetworkChannel.class.isAssignableFrom(parameterTypes[0])) {
			throw new IllegalArgumentException("The first parameter of a RMI Method must be the channel!");
		}			
	}

	@Override
	public boolean executePacket(NetworkChannel channel, Packet packet) {
		Object[] parameters = new Object[parameterTypes.length];		
		parameters[0] = channel;
		
		DataInputStream input = packet.getInputStream();
		for (int i = 1; i < parameters.length; i++) {
			
		}
		
		return true;
	}

}
