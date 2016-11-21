package com.awesome;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.awesome.Expression;

public class TestSimplify {
    
    private Expression expression;
    @Before
    public void setUp() throws Exception {
   
    	
    }
    
    @Test
    public void testMonomial1() {
    	
    	Utils.calcVariableCnt("2*a*b");
    	Monomial m1 = new Monomial("2");
    	Monomial m2 = new Monomial("a");
    	Monomial m3 = new Monomial("b");
    	expression = new Expression(m1.multiply(m2).multiply(m3));
    	
    	
    	Utils.calcVariableCnt("2*a*b");
    	Monomial mm1 = new Monomial("2");
    	Monomial mm2 = new Monomial("a");
    	Monomial mm3 = new Monomial("b");
    	Expression expected = new Expression(m1.multiply(m2).multiply(m3));
    	Expression actual = expression.simplify();
    	assertEquals(expected.toString(), actual.toString());
    }
    
    @Test
    public void testMonomial2() {
    	
    	
    	Utils.calcVariableCnt("2*a*b+3*a*b+0*b*c");
    	
    	Monomial m0 = new Monomial("0");
    	Monomial m1 = new Monomial("2");
    	Monomial m2 = new Monomial("3"); 
    	Monomial m3 = new Monomial("a");
    	Monomial m4 = new Monomial("b");
    	Monomial m5 = new Monomial("c");
    	
    
    	expression = new Expression(m1.multiply(m3).multiply(m4));
    	expression.addMonomial(m2.multiply(m3).multiply(m4));
    	expression.addMonomial(m0.multiply(m4).multiply(m5));
    	
    	
    	
    	
    	Monomial mm0 = new Monomial("5"); 
    	Monomial mm1 = new Monomial("a");
    	Monomial mm2 = new Monomial("b");
    	
    	Expression expected = new Expression(mm0.multiply(mm1).multiply(mm2));
    	Expression actual = expression.simplify();
    	assertEquals(expected.toString(), actual.toString());
    }
    
    
    @Test
    public void testMonomial3() {
    	
    	
    	 Utils.calcVariableCnt("2*a*b+3*a*b+0*b*c");
         
         Monomial m0 = new Monomial("2");
         Monomial m1 = new Monomial("3");
         Monomial m2 = new Monomial("4"); 
         Monomial m3 = new Monomial("a");
         Monomial m4 = new Monomial("b");
         Monomial m5 = new Monomial("c");
         Monomial m6 = new Monomial("-1");
         
     
         expression = new Expression(m0.multiply(m3).multiply(m4).multiply(m5));
         expression.addMonomial(m6.multiply(m3).multiply(m4).multiply(m5));
         expression.addMonomial(m6.multiply(m3).multiply(m4).multiply(m5));
         expression.addMonomial(m2.multiply(m4).multiply(m5));
         expression.addMonomial(m1.multiply(m4).multiply(m5));
         
         
        
        Monomial mm0 = new Monomial("7"); 
        Monomial mm1 = new Monomial("b");
        Monomial mm2 = new Monomial("c");
        
        Expression expected = new Expression(mm0.multiply(mm1).multiply(mm2));
        Expression actual = expression.simplify();
        assertEquals(expected.toString(), actual.toString());
    }
    
    
    @Test
    public void testMonomial4() {
    	
    	
    	Utils.calcVariableCnt("");
    	expression = new Expression();
 
    	
    	Utils.calcVariableCnt("0");
    	Expression expected = new Expression(new Monomial("0"));
    	Expression actual = expression.simplify();
    	assertEquals(expected.toString(), actual.toString());
    }
    
    
    @Test
    public void testMonomial5() {
    	
    	Utils.calcVariableCnt("2*a*b+3*a*b+0*b*c");
    	
    	Monomial m0 = new Monomial("a"); 
    	Monomial m1 = new Monomial("b");
    	Monomial m2 = new Monomial("c");
    	  	
    	expression = new Expression().addMonomial(m0).addMonomial(m1).addMonomial(m2);
    	
    	
    	
    	Monomial mm0 = new Monomial("a"); 
    	Monomial mm1 = new Monomial("b");
    	Monomial mm2 = new Monomial("c");
    	
    	Expression expected = new Expression().addMonomial(m0).addMonomial(m1).addMonomial(m2);
    	Expression actual = expression.simplify();
    	assertEquals(expected.toString(), actual.toString());
    }
    


}