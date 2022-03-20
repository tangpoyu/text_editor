package pattern;

import java.util.ArrayList;

public class MementoCaretaker {
    private ArrayList<Memento> arrayList= new ArrayList<>();

    public void add(Memento m){
        arrayList.add(m);
    }
    public Memento get(){
        Memento m= arrayList.get(arrayList.size()-1);
        arrayList.remove(arrayList.size()-1);
        return m;
    }
}
