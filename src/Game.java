import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;


public class Game extends JPanel implements ActionListener{
	
	private JButton newGame,highScores,quit;
	private JLabel scorelabel;
	
	private Board board;
	
	private int arrowKey;
	
	private ArrayList<Score> scores;
	private String topScoreLabel;
	
	public Game(){
		super(new BorderLayout());
		setVisible(true);
		setDoubleBuffered(true);
		
		board = new Board();
		arrowKey = 0;
		newGame = new JButton("New Game");
		
		highScores = new JButton("Highscores");
		
		quit = new JButton("Quit");
		
		scores = SaveHandler.readScore();
		topScoreLabel = scores.get(0).getName()+" with "+scores.get(0).getScore();
		scorelabel = new JLabel("Score: "+board.getScore()+"   Top Score: "+topScoreLabel);
		
		newGame.addActionListener(this);
		highScores.addActionListener(this);
		quit.addActionListener(this);
		newGame.setFocusable(false);
		highScores.setFocusable(false);
		quit.setFocusable(false);
		
		JPanel buttons = new JPanel(new GridLayout(1,3));
		
		buttons.add(newGame);
		buttons.add(highScores);
		buttons.add(quit);
		
		
		add(buttons,BorderLayout.SOUTH);
		add(board,BorderLayout.CENTER);
		add(scorelabel,BorderLayout.NORTH);
		
		buttons.setFocusable(false);
		
		
		setFocusable(true);
		setKeyBindings();
		requestFocusInWindow();
	}
	// ------------------- KEY HANDLING USING KEY BINDINGS ---------------------
	private void setKeyBindings() {
	      ActionMap actionMap = getActionMap();
	      int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
	      InputMap inputMap = getInputMap(condition );

	      String vkLeft = "VK_LEFT";
	      String vkRight = "VK_RIGHT";
	      String vkUp = "VK_UP";
	      String vkDown = "VK_DOWN";
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);

	      actionMap.put(vkLeft, new KeyAction(vkLeft));
	      actionMap.put(vkRight, new KeyAction(vkRight));
	      actionMap.put(vkUp, new KeyAction(vkUp));
	      actionMap.put(vkDown, new KeyAction(vkDown));

	   }


	   private class KeyAction extends AbstractAction {
	      public KeyAction(String actionCommand) {
	         putValue(ACTION_COMMAND_KEY, actionCommand);
	      }

	      @Override
	      public void actionPerformed(ActionEvent actionEvt) {
	         //System.out.println(actionEvt.getActionCommand() + " pressed");
	      // 4 - right, 5 - down, 6 - left , 7 - up
	 		if (actionEvt.getActionCommand().equals("VK_LEFT"))
	 			arrowKey = 6;
	 		else if (actionEvt.getActionCommand().equals("VK_RIGHT"))
	 			arrowKey = 4;
	 		else if (actionEvt.getActionCommand().equals("VK_UP"))
	 			arrowKey = 7;
	 		else if (actionEvt.getActionCommand().equals("VK_DOWN"))
	 			arrowKey = 5;
	 		
	 		//do something with the arrowKey
	 		
	 		board.merge(arrowKey);
	 		scorelabel.setText("Score: "+board.getScore()+"   Top Score: "+topScoreLabel);
	 		board.repaint();
	 		
	 		gameState(); // check for whats going on (end game if necessary)	
	 		
	      }
	   }
	   
	 // ---------------- KEY HANDLING END --------------------
	   
	@Override
	public void actionPerformed(ActionEvent evt) { // button handling
		JButton b = (JButton)evt.getSource();
		if (b.getText().equals("New Game")){
			resetGame();
		}else if (b.getText().equals("Highscores")){
			displayHighScores();
		}else if (b.getText().equals("Quit")){
			System.exit(0);
		}
	}
	
	private void displayHighScores(){ // only displays the top 10 high scores
		
		JPanel panel;
		if (scores.size()<10){ // if less than 10, display all scores
			panel = new JPanel(new GridLayout(scores.size(),2,10,5));
			for (Score s : scores){
				JLabel sc = new JLabel(""+s.getScore());
				JLabel sc1 = new JLabel(s.getName());
				panel.add(sc); panel.add(sc1);
				sc.setPreferredSize(new Dimension(50,20));
				sc.setHorizontalAlignment(SwingConstants.RIGHT);
			}
		}else{ // if 10 or higher, only display top 10
			panel = new JPanel(new GridLayout(10,2,10,5));
			for (int i=0;i<10;i++){
				JLabel sc = new JLabel(""+scores.get(i).getScore());
				JLabel sc1 = new JLabel(scores.get(i).getName());
				panel.add(sc); panel.add(sc1);
				sc.setPreferredSize(new Dimension(50,20));
				sc.setHorizontalAlignment(SwingConstants.RIGHT);
			}
		}
		JOptionPane.showMessageDialog(null, panel, "Top 10 Highscores", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	private void gameState(){ // also handles saving score
		int stat = board.statusUpdate();
		if (stat!=0){
			String message="";
			if (stat==1){ //game over
				message = "Game Over!";
			}else if (stat==2){ //you win
				message = "Congratulations! You got 2048!"; 
			}
			String name = JOptionPane.showInputDialog(null, message+"\nEnter your name (8 chars or less).", "Save Score", JOptionPane.PLAIN_MESSAGE);
			if (name==null) name = "";
			else if (name.length()>8)
				name = name.substring(0, 9);
			scores.add(new Score(board.getScore(),name));
			Collections.sort(scores);
			SaveHandler.saveScore(scores);
			
			resetGame();
		}
	}
	
	private void resetGame(){
		board.resetBoard();
		topScoreLabel = scores.get(0).getName()+" with "+scores.get(0).getScore();
		scorelabel.setText("Score: "+board.getScore()+"   Top Score: "+topScoreLabel);
		board.repaint();
	}

	/*@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("key pressed");
		// 4 - right, 5 - down, 6 - left , 7 - up
		if (key.getKeyCode()==KeyEvent.VK_LEFT)
			arrowKey = 6;
		else if (key.getKeyCode()==KeyEvent.VK_RIGHT)
			arrowKey = 4;
		else if (key.getKeyCode()==KeyEvent.VK_UP)
			arrowKey = 7;
		else if (key.getKeyCode()==KeyEvent.VK_DOWN)
			arrowKey = 5;
		
		//do something with the arrowKey
		
		board.merge(arrowKey);
		board.repaint();
	}

	@Override
	public void keyReleased(KeyEvent key) {
		int k = key.getKeyCode();
		if (k==KeyEvent.VK_LEFT || k==KeyEvent.VK_RIGHT || k==KeyEvent.VK_UP || k==KeyEvent.VK_DOWN)
			arrowKey=0;
		System.out.println(arrowKey);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/


}
