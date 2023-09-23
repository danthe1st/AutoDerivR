package io.github.danthe1st.autoderivr.tests.arithmetic.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;
import io.github.danthe1st.autoderivr.operations.arithmetic.concrete.TrigFunctions;

/**
 * @see TrigFunctions
 */
class TrigTests {
	@Test
	void testStringRepresentation() {
		assertEquals("sin(0.0)", TrigFunctions.sin(new Constant(0)).toString());
		assertEquals("cos(0.0)", TrigFunctions.cos(new Constant(0)).toString());
		assertEquals("tan(0.0)", TrigFunctions.tan(new Constant(0)).toString());
		
		assertEquals("arcsin(0.0)", TrigFunctions.arcsin(new Constant(0)).toString());
		assertEquals("arccos(0.0)", TrigFunctions.arccos(new Constant(0)).toString());
		assertEquals("arctan(0.0)", TrigFunctions.arctan(new Constant(0)).toString());
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
				TrigFunctions.cos(x).toString(),
				// sin(x)'
				TrigFunctions.sin(x).derivative(x).toString()
		);
		assertEquals(
				// -sin(x)
				new Subtract(Constant.ZERO, TrigFunctions.sin(x)).toString(),
				// cos(x)'
				TrigFunctions.cos(x).derivative(x).toString()
		);
		assertEquals(
				// tan(x)^2+1
				new Add(
						new Multiply(TrigFunctions.tan(x), TrigFunctions.tan(x)),
						Constant.ONE
				).toString(),
				// tan(x)'
				TrigFunctions.tan(x).derivative(x).toString()
		);
	}
	
	@Test
	void testDoubleDerivative() {
		Variable x = new Variable("x");
		assertEquals(
				// -sin(x)
				new Subtract(Constant.ZERO, TrigFunctions.sin(x)).toString(),
				// sin(x)''
				TrigFunctions.sin(x).derivative(x).derivative(x).toString()
		);
		assertEquals(
				// -cos(x)
				new Subtract(Constant.ZERO, TrigFunctions.cos(x)).toString(),
				// cos(x)''
				TrigFunctions.cos(x).derivative(x).derivative(x).toString()
		);
		assertEquals(
				// 2*tan(x)*(tan(x^2)+1) =
				// tan(x)*((tan(x)*tan(x))+1.0)+((tan(x)*tan(x))+1.0)*tan(x)
				new Add(
						new Multiply(TrigFunctions.tan(x), new Add(new Multiply(TrigFunctions.tan(x), TrigFunctions.tan(x)), Constant.ONE)),
						new Multiply(new Add(new Multiply(TrigFunctions.tan(x), TrigFunctions.tan(x)), Constant.ONE), TrigFunctions.tan(x))
				).toString(),
				// tan(x)''
				TrigFunctions.tan(x).derivative(x).derivative(x).toString()
		);
	}
	
	@Test
	void testInverseDerivative() {
		Variable x = new Variable("x");
		assertEquals(
				// 1/cos(asin(x))
				new Divide(
						Constant.ONE,
						TrigFunctions.cos(TrigFunctions.arcsin(x))
				).toString(),
				// asin(x)'
				TrigFunctions.arcsin(x).derivative(x).toString()
		);
		assertEquals(
				// 1/-sin(acos(x))
				new Divide(
						Constant.ONE,
						new Subtract(Constant.ZERO, TrigFunctions.sin(TrigFunctions.arccos(x)))
				).toString(),
				// acos(x)'
				TrigFunctions.arccos(x).derivative(x).toString()
		);
		assertEquals(
				// 1/((x^2)+1)=1/((tan(atan(x))^2)+1)
				new Divide(
						Constant.ONE,
						new Add(
								new Multiply(
										TrigFunctions.tan(TrigFunctions.arctan(x)),
										TrigFunctions.tan(TrigFunctions.arctan(x))
								), Constant.ONE
						)
				).toString(),
				// atan(x)'
				TrigFunctions.arctan(x).derivative(x).toString()
		);
	}
	
	@Test
	void testWithInnerFunction() {
		Variable x = new Variable("x");
		assertEquals(
				// cos(2x)*2
				new Multiply(TrigFunctions.cos(new Multiply(x, new Constant(2))), new Constant(2)),
				// sin(2x)'
				TrigFunctions.sin(new Multiply(x, new Constant(2))).derivative(x)
		);
		assertEquals(
				// -sin(2x)*2
				new Multiply(new Subtract(Constant.ZERO, TrigFunctions.sin(new Multiply(x, new Constant(2)))), new Constant(2)),
				// cos(2x)'
				TrigFunctions.cos(new Multiply(x, new Constant(2))).derivative(x)
		);
		assertEquals(
				// ((tan(2x)^2)+1)*2
				new Multiply(
						new Add(
								new Multiply(
										TrigFunctions.tan(new Multiply(x, new Constant(2))),
										TrigFunctions.tan(new Multiply(x, new Constant(2)))
								),
								Constant.ONE
						), new Constant(2)
				),
				// tan(2x)'
				TrigFunctions.tan(new Multiply(x, new Constant(2))).derivative(x)
		);
	}
}
