package model;

public class Facade {
    private ProtoMgr protoMgr;

    private Facade(ProtoMgr protoMgr){
        this.protoMgr=protoMgr;
    }

    public void getIcon( String size ){
        Prototype prototype = protoMgr.getIcon(size);

    }


}
