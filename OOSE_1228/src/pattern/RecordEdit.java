package pattern;

import javax.swing.*;

public class RecordEdit {
    private JTextPane j;
    private MementoCaretaker mementoCaretaker=new MementoCaretaker();

    public RecordEdit(JTextPane j){
        this.j=j;
    }

    public void save(){
        Memento m= new Memento(j.getText());
        mementoCaretaker.add(m);
    }

    public void restore(){
        Memento m=mementoCaretaker.get();
        j.setText(m.getS());
    }
}
