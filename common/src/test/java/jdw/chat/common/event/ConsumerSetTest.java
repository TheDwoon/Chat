package jdw.chat.common.event;

import static org.junit.Assert.*;

import org.junit.Test;

import jdw.chat.common.event.ConsumerSet.ConsumerPriority;

public class ConsumerSetTest {
	private boolean aCalled = false;
	private boolean bCalled = false;
	
	@Test
	public void test() {
		ConsumerSet set = new ConsumerSet();
		set.addConsumer(A.class, event -> {
			aCalled = true;
			assertFalse(bCalled);
		}, ConsumerPriority.HIGH);
		set.addConsumer(B.class, event -> bCalled = true);
		
		set.pushEvent(new B());
		
		assertTrue(aCalled);
		assertTrue(bCalled);		
	}
	
	public static class A {
		
	}
	
	public static class B extends A {
		
	}
}
