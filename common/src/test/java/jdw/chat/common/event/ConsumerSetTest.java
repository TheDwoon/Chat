package jdw.chat.common.event;

import static org.junit.Assert.*;

import org.junit.Test;

import jdw.chat.common.event.ConsumerSet.ConsumerPriority;

public class ConsumerSetTest {
	private boolean aCalled = false;
	private boolean bCalled = false;
	private boolean oCalled = false;
	
	@Test
	public void test() {
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
	
	public static class A {
		
	}
	
	public static class B extends A {
		
	}
}
