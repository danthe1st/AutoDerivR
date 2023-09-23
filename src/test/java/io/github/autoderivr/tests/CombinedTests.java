package io.github.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Subtract;
import io.github.danthe1st.autoderivr.operations.arithmetic.concrete.Exponentials;
import io.github.danthe1st.autoderivr.operations.arithmetic.concrete.TrigFunctions;

class CombinedTests {
	@Test
	void testSomeComplexDerivative() {
		Variable x = new Variable("x");
		Constant e = new Constant(Math.E);
		assertEquals(
		// @formatter:off
//							(sin(cos(x)*cos(x))
//						*
//									((((x*x)+1.0)
//								*
//									((e^(x*x)*ln(e))*(x+x)))
//							+
//								((x+x)*e^(x*x))))
//					+
//								((cos(cos(x)*cos(x))
//							*
//									((cos(x)*(0.0-sin(x)))
//								+
//									((0.0-sin(x))*cos(x))))
//						*
//							(((x*x)+1.0)*e^(x*x)))
		// @formatter:on
				new Add(
						new Multiply(
								// (sin(cos(x)*cos(x))
								TrigFunctions.sin(new Multiply(TrigFunctions.cos(x), TrigFunctions.cos(x))),
								new Add(
										new Multiply(
												// ((((x*x)+1.0)
												new Add(new Multiply(x, x), Constant.ONE),
												// ((e^(x*x)*ln(e))*(x+x)))
												new Multiply(new Multiply(Exponentials.power(e, new Multiply(x, x)), Exponentials.log(e, e)), new Add(x, x))
										),
										// ((x+x)*e^(x*x))))
										new Multiply(new Add(x, x), Exponentials.power(e, new Multiply(x, x)))
								)
						),
						new Multiply(
								new Multiply(
										// ((cos(cos(x)*cos(x))
										TrigFunctions.cos(new Multiply(TrigFunctions.cos(x), TrigFunctions.cos(x))),
										new Add(
												// ((cos(x)*(0.0-sin(x)))
												new Multiply(TrigFunctions.cos(x), new Subtract(Constant.ZERO, TrigFunctions.sin(x))),
												// ((0.0-sin(x))*cos(x))))
												new Multiply(new Subtract(Constant.ZERO, TrigFunctions.sin(x)), TrigFunctions.cos(x))
										)
								),
								// (((x*x)+1.0)*e^(x*x)))
								new Multiply(new Add(new Multiply(x, x), Constant.ONE), Exponentials.power(e, new Multiply(x, x)))
						)
				).toString(),
				// (sin(cos(x)^2)(x^2+1)e^(x^2))'
				new Multiply(
						TrigFunctions.sin(new Multiply(TrigFunctions.cos(x), TrigFunctions.cos(x))),
						new Multiply(
								new Add(new Multiply(x, x), Constant.ONE),
								Exponentials.power(e, new Multiply(x, x))
						)
				).derivative(x).toString()
		);
	}
}
