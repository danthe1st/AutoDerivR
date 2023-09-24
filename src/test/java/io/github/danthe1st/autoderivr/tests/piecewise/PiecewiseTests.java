package io.github.danthe1st.autoderivr.tests.piecewise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Node;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;
import io.github.danthe1st.autoderivr.operations.piecewise.Comparison;
import io.github.danthe1st.autoderivr.operations.piecewise.ErrorNode;
import io.github.danthe1st.autoderivr.operations.piecewise.Piecewise;

class PiecewiseTests {
	private Piecewise signum = new Piecewise(
			new Comparison(new Variable("x"), Constant.ZERO), // x ~ 0
			new Constant(-1), // <
			new Constant(1), // >
			new Constant(0) // =
	);
	
	@Test
	void testPiecewiseEvaluation() {
		Variable x = new Variable("x");
		assertEquals(-1, signum.evaluate(Map.of(x, -1.)));
		assertEquals(-1, signum.evaluate(Map.of(x, -2.)));
		assertEquals(-1, signum.evaluate(Map.of(x, -0.01)));
		assertEquals(0, signum.evaluate(Map.of(x, 0.)));
		assertEquals(1, signum.evaluate(Map.of(x, 1.)));
		assertEquals(1, signum.evaluate(Map.of(x, 2.)));
		assertEquals(1, signum.evaluate(Map.of(x, 0.01)));
		
		Map<Variable, Double> emptyMap = Collections.emptyMap();
		assertThrows(IllegalStateException.class, () -> signum.evaluate(emptyMap));
	}
	
	@Test
	void testDerivativeEvaluation() {
		Variable x = new Variable("x");
		Node signumd = signum.derivative(x);
		assertEquals(
				new Piecewise(signum.condition(), new Constant(0), new Constant(0), ErrorNode.INSTANCE),
				signumd
		);
		assertEquals(0, signumd.evaluate(Map.of(x, -1.)));
		assertEquals(0, signumd.evaluate(Map.of(x, -2.)));
		assertEquals(0, signumd.evaluate(Map.of(x, -0.01)));
		assertEquals(0, signumd.evaluate(Map.of(x, 1.)));
		assertEquals(0, signumd.evaluate(Map.of(x, 2.)));
		assertEquals(0, signumd.evaluate(Map.of(x, 0.01)));
		
		Map<Variable, Double> zeroValue = Map.of(x, 0.);
		assertThrows(UnsupportedOperationException.class, () -> signumd.evaluate(zeroValue));
		Map<Variable, Double> emptyMap = Collections.emptyMap();
		assertThrows(IllegalStateException.class, () -> signum.evaluate(emptyMap));
	}
	
	@Test
	void testDerivativeOfAbs() {
		Variable x = new Variable("x");
		Node abs = new Piecewise(new Comparison(x, new Constant(0)), new Subtract(new Constant(0), x), x, x);
		assertEquals(
				new Piecewise(
						new Comparison(new Variable("x"), Constant.ZERO), // x ~ 0
						new Subtract(Constant.ZERO, new Constant(1)), // <
						new Constant(1), // >
						ErrorNode.INSTANCE
				), abs.derivative(x)
		);
	}
	
	@Test
	void testStringRepresentation() {
		assertEquals("{if x<0.0 then -1.0 else if x>0.0 then 1.0 else 0.0}", signum.toString());
	}
}
