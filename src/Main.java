
import javax.swing.JFrame;


public class Main {
	
	public static void main(String[] args) {
		
		JFrame gameframe = new JFrame("2048 ‚É‚±‚Ü‚«-edition");
		gameframe.setVisible(true);
		gameframe.setResizable(false);
		
		Game game = new Game();
		
		gameframe.add(game);
		gameframe.pack();
		
	}

}
