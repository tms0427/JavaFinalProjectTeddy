import javax.swing.JFrame;

import javax.swing.ImageIcon;

public class GameFrame extends JFrame {

   ImageIcon icon;
   private JFrame frame;
   
 
	public GameFrame(){
			
		this.add(new GamePanel());
		this.setTitle("Hungry Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
      this.icon = new ImageIcon("snakeface.jpg");
      setIconImage(icon.getImage());   
      
      
  }
      
      
}
