package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;

class NodeUtilityTest {
	@Test
	void testUtilityCorrect() {
		Variable x = new Variable("x");
		Constant c = new Constant(2);
		assertEquals(new Add(x, c), x.add(c));
		assertEquals(new Subtract(x, c), x.subtract(c));
		assertEquals(new Multiply(x, c), x.multiply(c));
		assertEquals(new Divide(x, c), x.divide(c));
		
		assertEquals(new Multiply(x, x), x.square());
		assertEquals(new Subtract(Constant.ZERO, x), x.negate());
	}
	
	@Test
	void testUtilityCorrectWithLiteral() {
		Variable x = new Variable("x");
		Constant c = new Constant(2);
		assertEquals(new Add(x, c), x.add(2));
		assertEquals(new Subtract(x, c), x.subtract(2));
		assertEquals(new Multiply(x, c), x.multiply(2));
		assertEquals(new Divide(x, c), x.divide(2));
	}
}
