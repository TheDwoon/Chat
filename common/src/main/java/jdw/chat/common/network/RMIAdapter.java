package jdw.chat.common.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RMIAdapter implements Consumer<ReceivedPacket> {
	private static final HashMap<Class<?>, BinaryDeserializer<?>> PARSERS = new HashMap<>();
	private static final Logger LOG = LogManager.getLogger();
	
	static {
		addParser(int.class, (input) -> {
			return input.readInt();
		});
		
		addParser(float.class, (input) -> {
			return input.readFloat();
		}); 
		
		addParser(double.class, (input) -> {
			return input.readDouble();
		});
		
		addParser(boolean.class, (input) -> {
			return input.readBoolean();
		});
		
		addParser(String.class, (input) -> {
			return input.readUTF();
		});
	}
	
	private final Object target;
	private final Method method;
	private final Class<?>[] parameterTypes;
	
	public RMIAdapter(Method method, Object target) {
		this.target = target;
		this.method = method;
		this.parameterTypes = method.getParameterTypes();		
	}

	@Override
	public void accept(ReceivedPacket packet) {
		Object[] parameters = new Object[parameterTypes.length];	
		int i = 0;
		if (NetworkChannel.class.isAssignableFrom(parameterTypes[0])) {
			parameters[i++] = packet.getChannel();
		} 
		
		DataInputStream input = new DataInputStream(packet.getInputStream());
		while (i < parameters.length) {
			BinaryDeserializer<?> deserializer = PARSERS.get(parameterTypes[i]);
			try {
				deserializer.deserialize(input);
			} catch (IOException e) {
				LOG.error("Caught exception during RMI decoding: ", e);
				return;
			}
			
			i++;
		}
		
		try {
			input.close();
		} catch (IOException e) {
			LOG.catching(e);			
		}
		
		try {
			method.invoke(target, parameters);
		} catch (Exception e) {
			LOG.catching(e);
			return;
		} 
		
		return;
	}

	public static <T> void addParser(Class<T> clazz, BinaryDeserializer<T> deserializer) {
		PARSERS.put(clazz, deserializer);
	}
}
