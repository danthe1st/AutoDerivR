package io.github.danthe1st.autoderivr.operations.piecewise;

import java.util.Objects;

import io.github.danthe1st.autoderivr.operations.Node;

public record Comparison(Node left, Node right) {
	public Comparison {
		Objects.requireNonNull(left);
		Objects.requireNonNull(right);
	}
}
