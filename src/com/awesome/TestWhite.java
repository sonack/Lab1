package com.awesome;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.awesome.Expression;

public class TestWhite {

	private Expression expression;

	@Before
	public void setUp() throws Exception {
		BoundProcessor.calcVariableCnt("2*a*b+3*c");
		Monomial m1 = new Monomial("2");
		Monomial m2 = new Monomial("3");
		Monomial m3 = new Monomial("a");
		Monomial m4 = new Monomial("b");
		Monomial m5 = new Monomial("c");

		expression = new Expression(m1.multiply(m3).multiply(m4));
		expression.addMonomial(m2.multiply(m5));
	}

	@Test
	public void testMonomial() {
		BoundProcessor.calcVariableCnt("2*b+0");
		Monomial m1 = new Monomial("2");
		Monomial m2 = new Monomial("b");
		Monomial m3 = new Monomial("0");
		Expression expected = new Expression(m1.multiply(m2)).addMonomial(m3);
		Expression actual = expression.derivative("a");
		assertEquals(expected.toString(), actual.toString());
	}

}