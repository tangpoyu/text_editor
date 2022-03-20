package command;
import java.awt.event.ActionEvent;

public class CopyCommand implements Command{

	private Receiver receiver;
	
	public CopyCommand(Receiver r)
	{
		receiver = r;
	}
	
	public void execute(ActionEvent e)
	{
		receiver.copy(e);
	}
}
