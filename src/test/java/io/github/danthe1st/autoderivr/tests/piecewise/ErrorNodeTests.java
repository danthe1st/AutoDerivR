package io.github.danthe1st.autoderivr.tests.piecewise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.piecewise.ErrorNode;

class ErrorNodeTests {
	@Test
	void testEvaluate() {
		Map<Variable, Double> emptyMap = Collections.emptyMap();
		assertThrows(UnsupportedOperationException.class, () -> ErrorNode.INSTANCE.evaluate(emptyMap));
	}
	
	@Test
	void testStringRepresentation() {
		assertEquals("<ERROR>", ErrorNode.INSTANCE.toString());
	}
	
	@Test
	void testDerivative() {
		assertEquals(ErrorNode.INSTANCE, ErrorNode.INSTANCE.derivative(new Variable("x")));
	}
}
