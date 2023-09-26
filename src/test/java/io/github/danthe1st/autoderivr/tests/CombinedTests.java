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
//							(sin(cos(x)^2)
//						*
//									((((x^2)+1.0)
//								*
//									((e^(x^2)*ln(e))*(2*x)))
//							+
//								((x+x)*e^(x*x))))
//					+
//								((cos(cos(x)^2)
//							*
//									((cos(x)*(0.0-sin(x)))
//								+
//									((0.0-sin(x))*cos(x))))
//						*
//							(((x*x)+1.0)*e^(x*x)))
		// @formatter:on
				new Add(
						new Multiply(
								// (sin(cos(x)^2)
								TrigFunctions.sin(TrigFunctions.cos(x).square()),
								new Add(
										new Multiply(
												// ((((x^2)+1.0)
												x.square().add(1),
												// ((e^(x^2)*ln(e))*(2*x)))
												Exponentials.exp(e, x.square()).multiply(Exponentials.log(e, e)).multiply(new Constant(2).multiply(x))
										),
										// ((x+x)*e^(x*x))))
										x.add(x).multiply(Exponentials.exp(e, x.square()))
								)
						),
						new Multiply(
								new Multiply(
										// ((cos(cos(x)^2)
										TrigFunctions.cos(TrigFunctions.cos(x).square()),
										new Add(
												// ((cos(x)*(0.0-sin(x)))
												new Multiply(TrigFunctions.cos(x), TrigFunctions.sin(x).negate()),
												// ((0.0-sin(x))*cos(x))))
												new Multiply(TrigFunctions.sin(x).negate(), TrigFunctions.cos(x))
										)
								),
								// (((x*x)+1.0)*e^(x*x)))
								x.square().add(1).multiply(Exponentials.exp(e, x.square()))
						)
				).reduce(),
				// (sin(cos(x)^2)(x^2+1)e^(x^2))'
				new Multiply(
						TrigFunctions.sin(TrigFunctions.cos(x).square()),
						x.square().add(1).multiply(Exponentials.exp(e, x.square()))
				).derivative(x).reduce()
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
								Exponentials.exp(e, x.negate()).multiply(Exponentials.log(e, e)),
								Constant.ONE.negate()// 0-1
						).negate(),
						// (1+e^(0-x))*(1+e^(0-x))
						Constant.ONE.add(Exponentials.exp(e, x.negate())).square()
				),
				// (1/(1+e^(-x)))'
				Constant.ONE.divide(
						Constant.ONE.add(Exponentials.exp(e, x.negate()))
				).derivative(x).reduce()
		);
	}
}
