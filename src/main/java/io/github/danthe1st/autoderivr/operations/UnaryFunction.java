package io.github.danthe1st.autoderivr.operations;

import java.util.Map;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import io.github.danthe1st.autoderivr.operations.arithmetic.basic.Multiply;

/**
 * A unary function applied on some argument.
 * Functions must not depend on any other non-constant arguments except the node passed to {@code argument}.
 * @apiNote If {@code doubleCalculator} is implemented as a lambda expression, all constant state used in it must somehow be expressed in {@code name}.
 * If two {@code UnaryFunction}s are using the same lambda expression for {@code DoubleUnaryOperator} but with different information passed in the lambda, {@code name} must be different.
 * @param name the name of the function.
 * @param argument the single argument to the function.
 * @param doubleCalculator evaluates the function given the argument has already been evaluated
 * @param atomicDeriver calculates the derivative of this function <b>with respect to {@code argument}</b>
 */
public record UnaryFunction(String name,
		Node argument,
		DoubleUnaryOperator doubleCalculator,
		UnaryOperator<Node> atomicDeriver) implements Node {
	
	public UnaryFunction {
		Objects.requireNonNull(name);
		Objects.requireNonNull(argument);
		Objects.requireNonNull(doubleCalculator);
		Objects.requireNonNull(atomicDeriver);
	}
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(argument, extractEqualityObjectFromFunction(doubleCalculator), name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof UnaryFunction(String otherName, Node otherArg, DoubleUnaryOperator otherCalcFunction, UnaryOperator<Node> otherAtomicDeriver))){
			return false;
		}
		
		return Objects.equals(this.name(), otherName) &&
				Objects.equals(this.argument(), otherArg) &&
				Objects.equals(extractEqualityObjectFromFunction(this.doubleCalculator()), extractEqualityObjectFromFunction(otherCalcFunction));
	}
	
	private static Object extractEqualityObjectFromFunction(DoubleUnaryOperator op) {
		if(op.getClass().isSynthetic()){
			return op.getClass();
		}
		return op;
	}
	
}
