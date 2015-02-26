import java.awt.Component;
import java.awt.event.*;

public class InputHandler implements KeyListener
{
	public boolean keys[];

	public InputHandler(Component c)
	{
		c.addKeyListener(this);
		keys = new boolean[256]; 
	} // assign newly created Input Handler to a Component

	/**
	* Checks whether a specific key is down
	* @param keyCode the key to check 
	* @return if key is pressed or not
	*/
	public boolean isKeyDown(int keyCode)
	{
		if(keyCode > 0 && keyCode < 256)
			return keys[keyCode];

		return false;
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() > 0 && e.getKeyCode() < 256)
			keys[e.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() > 0 && e.getKeyCode() < 256)
			keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)
	{

	}
}