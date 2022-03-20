package Inteprete;

import java.util.HashMap;

public class EqualExpression extends SymbolExpression{
    public EqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double interpreter(HashMap<String, Double> var) {
        double result;
        if(super.m_left.interpreter(var)==super.m_right.interpreter(var)){
            result=-1;
        }else{
            result=-2;
        }
        return result;
    }
}
