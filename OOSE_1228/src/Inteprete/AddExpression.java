package Inteprete;

import java.util.HashMap;

public class AddExpression extends SymbolExpression{
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double interpreter(HashMap<String, Double> var) {
        return super.m_left.interpreter(var) + super.m_right.interpreter(var);
    }
}
