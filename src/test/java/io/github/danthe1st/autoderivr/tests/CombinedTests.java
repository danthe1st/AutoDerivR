package io.github.danthe1st.autoderivr.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.danthe1st.autoderivr.operations.Constant;
import io.github.danthe1st.autoderivr.operations.Variable;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Add;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Divide;
import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;
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
												x.square().add(1),
												// ((e^(x*x)*ln(e))*(x+x)))
												Exponentials.power(e, x.square()).multiply(Exponentials.log(e, e)).multiply(x.add(x))
										),
										// ((x+x)*e^(x*x))))
										x.add(x).multiply(Exponentials.power(e, x.square()))
								)
						),
						new Multiply(
								new Multiply(
										// ((cos(cos(x)*cos(x))
										TrigFunctions.cos(new Multiply(TrigFunctions.cos(x), TrigFunctions.cos(x))),
										new Add(
												// ((cos(x)*(0.0-sin(x)))
												new Multiply(TrigFunctions.cos(x), TrigFunctions.sin(x).negate()),
												// ((0.0-sin(x))*cos(x))))
												new Multiply(TrigFunctions.sin(x).negate(), TrigFunctions.cos(x))
										)
								),
								// (((x*x)+1.0)*e^(x*x)))
								x.square().add(1).multiply(Exponentials.power(e, x.square()))
						)
				).toString(),
				// (sin(cos(x)^2)(x^2+1)e^(x^2))'
				new Multiply(
						TrigFunctions.sin(TrigFunctions.cos(x).square()),
						x.square().add(1).multiply(Exponentials.power(e, x.square()))
				).derivative(x).toString()
		);
	}
	
	@Test
	void testSigmoid() {
		Variable x = new Variable("x");
		Constant e = new Constant(Math.E);
		assertEquals(
				// (-((e^(-x)*ln(e))*(0-1)))/((1+e^(0-x))*(1+e^(-x)))
				new Divide(
						// -((e^(0-x)*ln(e))*(0-1))
						new Multiply(
								// e^(-x)*ln(e)
								Exponentials.power(e, x.negate()).multiply(Exponentials.log(e, e)),
								Constant.ONE.negate()// 0-1
						).negate(),
						// (1+e^(0-x))*(1+e^(0-x))
						Constant.ONE.add(Exponentials.power(e, x.negate())).square()
				).toString(),
				// (1/(1+e^(-x)))'
				Constant.ONE.divide(
						Constant.ONE.add(Exponentials.power(e, x.negate()))
				).derivative(x).toString()
		);
	}
}
