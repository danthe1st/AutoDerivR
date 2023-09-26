package io.github.danthe1st.autoderivr.tests.arithmetic.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;
import io.github.danthe1st.autoderivr.operations.arithmetic.concrete.TrigFunctions;

/**
 * @see TrigFunctions
 */
class TrigTests {
	@Test
	void testStringRepresentation() {
		assertEquals("sin(0.0)", TrigFunctions.sin(Constant.ZERO).toString());
		assertEquals("cos(0.0)", TrigFunctions.cos(Constant.ZERO).toString());
		assertEquals("tan(0.0)", TrigFunctions.tan(Constant.ZERO).toString());
		
		assertEquals("arcsin(0.0)", TrigFunctions.arcsin(Constant.ZERO).toString());
		assertEquals("arccos(0.0)", TrigFunctions.arccos(Constant.ZERO).toString());
		assertEquals("arctan(0.0)", TrigFunctions.arctan(Constant.ZERO).toString());
	}
	
	@Test
	void testCalculate() {
		Variable x = new Variable("x");
		
		assertEquals(1, TrigFunctions.sin(new Variable("x")).evaluate(Map.of(x, Math.PI / 2)), 0.0000001);
		assertEquals(0, TrigFunctions.sin(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
		assertEquals(0, TrigFunctions.cos(new Variable("x")).evaluate(Map.of(x, Math.PI / 2)), 0.0000001);
		assertEquals(1, TrigFunctions.cos(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
		assertEquals(0, TrigFunctions.tan(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
		
		assertEquals(Math.PI / 2, TrigFunctions.arcsin(new Variable("x")).evaluate(Map.of(x, 1.)), 0.0000001);
		assertEquals(0, TrigFunctions.arcsin(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
		assertEquals(Math.PI / 2, TrigFunctions.arccos(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
		assertEquals(0, TrigFunctions.arccos(new Variable("x")).evaluate(Map.of(x, 1.)), 0.0000001);
		assertEquals(0, TrigFunctions.arctan(new Variable("x")).evaluate(Map.of(x, 0.)), 0.0000001);
	}
	
	@Test
	void testSimpleDerivative() {
		Variable x = new Variable("x");
		assertEquals(
				// cos(x)
				TrigFunctions.cos(x),
				// sin(x)'
				TrigFunctions.sin(x).derivative(x)
		);
		assertEquals(
				// -sin(x)
				Constant.ZERO.subtract(TrigFunctions.sin(x)),
				// cos(x)'
				TrigFunctions.cos(x).derivative(x)
		);
		assertEquals(
				// tan(x)^2+1
				TrigFunctions.tan(x).square().add(1),
				// tan(x)'
				TrigFunctions.tan(x).derivative(x)
		);
	}
	
	@Test
	void testDoubleDerivative() {
		Variable x = new Variable("x");
		assertEquals(
				// -sin(x)
				TrigFunctions.sin(x).negate(),
				// sin(x)''
				TrigFunctions.sin(x).derivative(x).derivative(x)
		);
		assertEquals(
				// -cos(x)
				TrigFunctions.cos(x).negate(),
				// cos(x)''
				TrigFunctions.cos(x).derivative(x).derivative(x)
		);
		assertEquals(
				// 2*tan(x)*(tan(x^2)+1) =
				// tan(x)*((tan(x)*tan(x))+1.0) + ((tan(x)*tan(x))+1.0)*tan(x)
				new Add(
						TrigFunctions.tan(x).multiply(TrigFunctions.tan(x).square().add(1)),
						TrigFunctions.tan(x).square().add(1).multiply(TrigFunctions.tan(x))
				),
				// tan(x)''
				TrigFunctions.tan(x).derivative(x).derivative(x).reduce()
		);
	}
	
	@Test
	void testInverseDerivative() {
		Variable x = new Variable("x");
		assertEquals(
				// 1/cos(asin(x))
				Constant.ONE
					.divide(TrigFunctions.cos(TrigFunctions.arcsin(x))),
				// asin(x)'
				TrigFunctions.arcsin(x).derivative(x)
		);
		assertEquals(
				// 1/-sin(acos(x))
				Constant.ONE
					.divide(new Subtract(Constant.ZERO, TrigFunctions.sin(TrigFunctions.arccos(x)))),
				// acos(x)'
				TrigFunctions.arccos(x).derivative(x)
		);
		assertEquals(
				// 1/((x^2)+1) = 1/((tan(atan(x))^2)+1)
				Constant.ONE
					.divide(TrigFunctions.tan(TrigFunctions.arctan(x)).square().add(1)),
				// atan(x)'
				TrigFunctions.arctan(x).derivative(x)
		);
	}
	
	@Test
	void testWithInnerFunction() {
		Variable x = new Variable("x");
		assertEquals(
				// cos(2x)*2
				TrigFunctions.cos(x.multiply(2)).multiply(2),
				// sin(2x)'
				TrigFunctions.sin(x.multiply(2)).derivative(x)
		);
		assertEquals(
				// -sin(2x)*2
				TrigFunctions.sin(x.multiply(2)).negate().multiply(2),
				// cos(2x)'
				TrigFunctions.cos(x.multiply(2)).derivative(x)
		);
		assertEquals(
				// ((tan(2x)^2)+1)*2
				TrigFunctions.tan(x.multiply(2)).square().add(1).multiply(2),
				// tan(2x)'
				TrigFunctions.tan(x.multiply(2)).derivative(x)
		);
	}
}
