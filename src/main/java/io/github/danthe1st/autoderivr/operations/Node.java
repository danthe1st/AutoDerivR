package io.github.danthe1st.autoderivr.operations;

import java.util.Map;

public interface Node {
	double calculateDouble(Map<Variable, Double> variableValues);
	
	Node derivative(Variable variable);
	
	default Node reduce() {
		return this;
	}
}
