package command;
import java.awt.event.ActionEvent;

public class CutCommand implements Command
{
	private Receiver receiver;
	
	public CutCommand(Receiver r)
	{
		receiver = r;
	}

	public void execute(ActionEvent e)
	{
		receiver.cut(e);
	}
}

