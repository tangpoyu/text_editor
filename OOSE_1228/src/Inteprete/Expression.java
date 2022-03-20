package Inteprete;

import java.util.HashMap;

public abstract class Expression {
    public abstract double interpreter(HashMap<String,Double> var);
}
