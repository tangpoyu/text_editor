package Inteprete;

import java.util.HashMap;

public class VarExpression extends Expression{
    private String  m_key;

    public VarExpression(String key){
        this. m_key = key;
    }
    @Override
    public double interpreter(HashMap<String, Double> var) {
        return var.get(m_key);
    }
}
