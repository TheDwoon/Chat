package jdw.chat.common.event;

import static org.junit.Assert.*;

import java.util.function.Consumer;

import org.junit.Test;

import jdw.chat.common.event.ConsumerSet.ConsumerPriority;

public class ConsumerSetTest {
	private boolean aCalled = false;
	private boolean bCalled = false;
	private boolean oCalled = false;
	
	private boolean called = false;
	@Test
	public void generalTest() {
		ConsumerSet set = new ConsumerSet();
		set.addConsumer(A.class, event -> {
			aCalled = true;
			assertFalse(bCalled);
		}, ConsumerPriority.HIGH);
		set.addConsumer(B.class, event -> bCalled = true);
		set.addConsumer(Object.class, event -> oCalled = true);
		
		set.pushEvent(new B());
		
		assertTrue(aCalled);
		assertTrue(bCalled);		
		assertTrue(oCalled);
	}
	
	@Test
	public void addAndRemove() {
		Consumer<Object> consumer = new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				called = true;
			}
		};
		
		ConsumerSet set = new ConsumerSet();
		set.addConsumer(Object.class, consumer);
		set.pushEvent(new Object());
		
		assertTrue(called);
		called = false;
		
		set.removeConsumer(Object.class, consumer);
		set.pushEvent(new Object());
		assertFalse(called);
	}
	
	public static class A {
		
	}
	
	public static class B extends A {
		
	}
}
