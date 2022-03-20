package model;

public class Prototype implements Cloneable{

    public Object clone(){
        Object clone=null;

        try{
            clone=super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        return clone;
    }
}
