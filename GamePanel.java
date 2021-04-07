import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 750;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 155;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 1;
	int ballsEaten;
	int ballX;
	int ballY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
   static boolean restartOn = false;
   ImageIcon icon;
   Image backgroundImage;
   
  
	//creation
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(new Color(20,150,20));
 
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
     
   //Created the background but having issues..  
    /*backgroundImage = new ImageIcon("grass1.jpg").getImage();
      */
   }
   
   public void paint(Graphics g) {
   
      super.paint(g); //paint background
      Graphics2D g2D = (Graphics2D) g;
      
      g2D.drawImage(backgroundImage, 0, 0, null);
    
   }     
	public void startGame() {
		newBall();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
   public void pause(){
      GamePanel.restartOn = true;
      timer.stop();
   }
   public void resume(){
      GamePanel.restartOn = false;
      timer.start();
   }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
                 
         g.setColor(Color.red);
			g.fillOval(ballX, ballY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(150,150,150));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(new Color(50,50,50));
			g.setFont( new Font("Showcard Gothic",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+ballsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ballsEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void newBall(){
		ballX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		ballY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void checkBall() {
		if((x[0] == ballX) && (y[0] == ballY)) {
			bodyParts++;
			ballsEaten++;
			newBall();
		}
	}
	public void checkCollisions() {
		//ball collides with body
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		//ball touches left border
		if(x[0] < 0) {
			running = false;
		}
		//ball touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//ball touches top border
		if(y[0] < 0) {
			running = false;
		}
		//ball touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
   
   //Text for Score and Ending
   
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.black);
		g.setFont( new Font("Showcard Gotchic",Font.BOLD, 50));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ballsEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+ballsEaten))/2, g.getFont().getSize());
      
		//Finished
		g.setColor(Color.black);
		g.setFont( new Font("Showcard Gothic",Font.BOLD, 180));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER!!", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER!!"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkBall();
			checkCollisions();
		}
		repaint();
	}
	
   //Movement of Snake
   
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
         case KeyEvent.VK_SPACE:
            if(GamePanel.restartOn){
               resume();
            }else{
               pause();
            }
            break;
			}
		}
	}
}
