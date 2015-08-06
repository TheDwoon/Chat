package jdw.chat.common.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerSet {
	private final HashMap<Class<?>, List<Entry<?>>> consumers = new HashMap<>();
	
	public ConsumerSet() {
		
	}
	
	public <T> void addConsumer(Class<T> clazz, Consumer<T> consumer) {
		addConsumer(clazz, consumer, ConsumerPriority.NORMAL);
	}
	
	public <T> void addConsumer(Class<T> clazz, Consumer<T> consumer, ConsumerPriority priority) {		
		synchronized (consumers) {
			List<Entry<?>> entries = consumers.get(clazz);
			if (entries == null) {
				entries = new ArrayList<>();
				consumers.put(clazz, entries);
			}
			
			synchronized (entries) {
				entries.add(new Entry<>(clazz, consumer, priority));
				Collections.sort(entries);
			}
		}
	}
	
	public <T> void removeConsumer(Class<T> clazz, Consumer<T> consumer) {
		synchronized (consumers) {
			List<Entry<?>> entries = consumers.get(clazz);
			if (entries != null) {
				synchronized (entries) {
					Iterator<Entry<?>> it = entries.iterator();
					while (it.hasNext()) {
						Entry<?> entry = it.next();
						if (entry.consumer.equals(consumer)) {
							it.remove();
						}
					}
				}
			}
		}
	}
	
	public void pushEvent(Object event) {
		if (event == null) {
			return;
		}
		
		Class<?> clazz = event.getClass();
		
		List<Entry<?>> entries = new ArrayList<>(16);
		collectEntries(entries, clazz);
		while (!clazz.equals(Object.class)) {
			clazz = clazz.getSuperclass();
			collectEntries(entries, clazz);
		}
		
		Collections.sort(entries);
		entries.forEach(consumer -> consumer.accept(event));
	}
	
	private void collectEntries(List<Entry<?>> entries, Class<?> clazz) {
		synchronized (consumers) {
			List<Entry<?>> localEntries = consumers.get(clazz);
			
			if (localEntries != null) {
				synchronized (localEntries) {
					entries.addAll(localEntries);
				}
			}
		}
	}
	
	public static enum ConsumerPriority {
		HIGHEST,
		HIGH,
		NORMAL,
		LOW,
		MONITOR;
	}
	
	private static final class Entry<T> implements Comparable<Entry<T>> {
		private final Class<T> clazz;
		private final Consumer<T> consumer;
		private final ConsumerPriority priority;
		
		private Entry(Class<T> clazz, Consumer<T> consumer, ConsumerPriority priority) {
			this.clazz = clazz;
			this.consumer = consumer;
			this.priority = priority;
		}
				
		public void accept(Object event) {			
			consumer.accept(clazz.cast(event));
		}

		@Override
		public int compareTo(Entry<T> other) {
			return Integer.compare(this.priority.ordinal(), other.priority.ordinal());
		}
	}
}
