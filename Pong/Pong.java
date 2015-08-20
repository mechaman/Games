/** Author: Julien Hoachuck
* GAME: Simple Pong
**/

import java.awt.*;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Pong extends JFrame
{
	// Background
	boolean isRunning;
	int fps;
	BufferedImage backBuffer;
	Insets insets;
	InputHandler input;

	// Ball Speed Components
	int travelX;
	int travelY;

	// Dimensions
	int windowWidth;
	int windowHeight;
	int ballRadius;
	int paddleW;
	int paddleH;

	// Positions
	int ballX, ballY;
	int paddleX1, paddleY1, paddleX2, paddleY2;


	boolean hit;
	int score1;
	int score2;


	public static void main(String[] args)
	{
		Pong pong = new Pong();
		pong.run();
		System.exit(0);

	}

	public void run()
	{	
		// Initiliaze Window & Parameters
		init(); 

		while(isRunning)
		{
			long time = System.currentTimeMillis();
			
		
			// Paddle Input
			if(input.isKeyDown(KeyEvent.VK_DOWN) && paddleY1 < (windowHeight-paddleH))
				paddleY2+=5;
			if(input.isKeyDown(KeyEvent.VK_UP) && paddleY1 > 0)
				paddleY2-=5;

			if(input.isKeyDown(KeyEvent.VK_S) && paddleY2 < (windowHeight-paddleH))
				paddleY1+=5;
			if(input.isKeyDown(KeyEvent.VK_W) && paddleY2 > 0)
				paddleY1-=5;

			

			// Ball Hit Left Paddle Check
			if((ballY > (paddleY1)) && (ballY < (paddleY1 + paddleH)) && !hit)
			{
				if(ballX == paddleW)
				{
					travelY = travelY;
					travelX = -travelX;
					hit = true;
				}
			} 
			else
			{
				hit = false;
			}

			// Ball Hit Right Paddle Check
			if((ballY > (paddleY2)) && (ballY < (paddleY2+paddleH)) && !hit)
			{
				if(((ballX+ballRadius == (paddleX2))))
				{
					travelY = travelY;
					travelX = -travelX;
					hit = true;
				}
			} 
			else
			{
				hit = false;
			}


			//Ball Hit Side Wall (Goal)
			if(ballX > (paddleX2+5))
			{
				score1++;
				ballX = 200;
				ballY = 200;
				travelX = -travelX;
			}
			else if(ballX < paddleW-10)
			{
				score2++;
				ballX = 200;
				ballY = 200;
				travelX = -travelX;
			}

			// Ball Hit Up Wall
			if((ballY > windowHeight-10))
				travelY = -travelY;
			else if(ballY < 10)
				travelY = -travelY;
						
			// Update Position of Ball
			ballY+=travelY;
			ballX+=travelX;

			//Update The Screen
			update();

			time = (1000/fps)-(System.currentTimeMillis()-time);

			if(time > 0)
			{
				try
				{
					Thread.sleep(time);
				}
				catch(Exception e){}
			}

		}

		setVisible(false);
	}

	public void init()
	{	
		// Parameter Initialization
		isRunning = true;
		fps = 30;

		ballX= 100;
		ballY = 100;
		ballRadius = 20;

		windowWidth = 500;
		windowHeight = 500;

		paddleW = 10;
		paddleH = 100;
		paddleX2 = windowWidth-paddleW;

		travelX = 5;
		travelY = 1;

		score1 = score2 =  0;
		
		// Initialize Window
		setTitle("Pong");
		setSize(windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// Make sure that there is a border so that graphic doesn't show up in title bar
		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right, insets.top + windowHeight + insets.bottom);

		// To save the image and reload it
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		hit = false;

		// Initialize InputHandler for keyboard
		input = new InputHandler(this);



	}

	public void update()
	{
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();

		///// Draw to backBuffer /////

		// Draw  Window
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		// Draw Ball
		bbg.setColor(Color.WHITE);
		bbg.fillOval(ballX, ballY, ballRadius, ballRadius);

		// Draw Paddles
		bbg.fillRect(paddleX1, paddleY1, paddleW, paddleH);
		bbg.fillRect(paddleX2, paddleY2, paddleW, paddleH);

		// Draw Score
		String scoreStr = score1 + " : " + score2;
		bbg.drawString(scoreStr, 250, 25);

		//// Draw backBuffer to window ///
		g.drawImage(backBuffer,insets.left,insets.top,this);

	}

}
