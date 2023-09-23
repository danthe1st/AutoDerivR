package io.github.danthe1st.autoderivr.operations;

import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;

public record UnaryFunction(String name,
		Node argument,
		DoubleUnaryOperator doubleCalculator,
		UnaryOperator<Node> atomicDeriver) implements Node {
	
	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return doubleCalculator.applyAsDouble(argument.evaluate(variableValues));
	}
	
	@Override
	public Node derivative(Variable variable) {
		return new Multiply(
				atomicDeriver.apply(argument),
				argument.derivative(variable)
		).reduce();
	}
	
	@Override
	public String toString() {
		return name() + "(" + argument + ")";
	}
	
	public static Function<Node, UnaryFunction> factory(String name, DoubleUnaryOperator doubleCalculator, UnaryOperator<Node> atomicDeriver) {
		return argument -> new UnaryFunction(name, argument, doubleCalculator, atomicDeriver);
	}
	
	@Override
	public Node reduce() {
		return new UnaryFunction(name, argument.reduce(), doubleCalculator, atomicDeriver);
	}
}
