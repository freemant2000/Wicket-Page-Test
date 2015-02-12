package com.ttdev.wicketpagetest;

import org.testng.annotations.Test;

@Test
public class ByWicketIdPathFastVersionTest {

	public void testToString() {
		ByWicketIdPathFastVersion b = new ByWicketIdPathFastVersion("//a");
		assert b.toString().equals("ByWicketIdPathFastVersion: //a");
	}
}
