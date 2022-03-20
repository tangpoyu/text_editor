package Inteprete;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    private Expression expression;

    public boolean build(String StrExp){
        String strExp=StrExp;
        Expression left =  null;
        Expression right =  null;
        Stack<Expression> stack =  new Stack<Expression>();
        char[] chars = strExp.toCharArray();
        char[] chars1=new char[100];

        for (int i=0;i<chars.length;i++){
            chars1[i]=chars[i];
        }

        for ( int i =  0; i < chars. length; i++) {
            switch (chars1[i]){
                case  '+':
                    try {
                        left = stack.pop();
                        right = new VarExpression(String.valueOf(chars1[++i]));
                        stack.push(new AddExpression(left, right));
                    }catch (Exception E){}
                    break;
                case  '-':
                    try {
                        left = stack.pop();
                        right =  new VarExpression(String.valueOf(chars1[++i]));
                        stack.push( new SubExpression(left,right));
                    }catch (Exception E){}
                    break;
                case  '*':
                    try {
                        left = stack.pop();
                        right =  new VarExpression(String. valueOf(chars1[++i]));
                        stack.push( new MultiplyExpression(left,right));
                    }catch(Exception E){}
                    break;
                case  '/':
                    try {
                        left = stack.pop();
                        right =  new VarExpression(String. valueOf(chars1[++i]));
                        stack.push( new DivisionExpression(left,right));
                    }catch (Exception E){}
                    break;
                case '=':
                    try {
                        left = stack.pop();
                        right =  new VarExpression(String. valueOf(chars1[++i]));
                        stack.push( new EqualExpression(left,right));
                    }catch (Exception E){}
                    break;
                default:
                    stack.push( new VarExpression(String. valueOf(chars1[i])));
                    break;
            }
        }
        try {
            this. expression = stack.pop();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public double compute(){
        try {
            return expression.interpreter(creteHashMap());
        }catch (Exception E){

        }
        return 0;
    }

    public HashMap<String,Double> creteHashMap(){
        HashMap<String,Double> hashMap =  new HashMap<String,Double>();
        String[] number={"0","1","2","3","4","5","6","7","8","9"};

        for(double i=0;i<10;i++){
            hashMap.put(number[(int) i],i);
        }
        return hashMap;
    }
}
