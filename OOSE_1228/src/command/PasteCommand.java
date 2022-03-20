package command;
import java.awt.event.ActionEvent;

public class PasteCommand implements Command{

	private Receiver receiver;
	
	public PasteCommand(Receiver r)
	{
		receiver = r;
	}

	public void execute(ActionEvent e)
	{
		receiver.paste(e);
	}
}
