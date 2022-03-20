package Inteprete;

public abstract class SymbolExpression extends Expression{
    protected Expression  m_left;
    protected Expression  m_right;

    public SymbolExpression(Expression left,Expression right){
        this. m_left = left;
        this. m_right = right;
    }
}
