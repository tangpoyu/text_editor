package model;

import java.util.HashMap;

public class ProtoMgr {
    private HashMap<String,Prototype> Icons=new HashMap<>();
    private static ProtoMgr protoMgr=new ProtoMgr();

    public static ProtoMgr getInstance(){
        return protoMgr;
    }

    public Prototype getIcon(String size){
        Icon icon=(Icon) Icons.get(size);
        if(icon == null){
            System.out.println("first create");
            Icon icon1=new Icon();
            icon1.setName(size+"_icon");
            icon1.setSize(size);
            Icons.put(size,icon1);
            icon=(Icon) Icons.get(size);
        }
        return icon;
    }
}
