package command;

import java.awt.event.ActionEvent;
import javax.swing.text.DefaultEditorKit;

public class Receiver {

	    public void cut(ActionEvent e)
		{
			new DefaultEditorKit.CutAction().actionPerformed(e);
		}
		
		public void copy(ActionEvent e)
		{
			new DefaultEditorKit.CopyAction().actionPerformed(e);
		}
		
		public void paste(ActionEvent e)
		{
			new DefaultEditorKit.PasteAction().actionPerformed(e);
		}
}
