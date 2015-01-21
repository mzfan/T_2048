import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;

/* Game Board - Handles the actual tiles */
public class Board extends JPanel{
	
	private Grid grid;
	
	private BufferedImage fieldIMG;
	private BufferedImage[] tiles = new BufferedImage[11];
	private int[] tileX = {14,135,256,377};
	private int[] tileY = {15,136,258,379};
	
	public Board(){
		super(new GridLayout(4,4));
		grid = new Grid();
		
		setPreferredSize(new Dimension(500,500));
		setVisible(true);
		setDoubleBuffered(true);
		setFocusable(false);
		//load bg image, tiles
		try { 
			fieldIMG = ImageIO.read(new File("imgs/field.png"));
			for (int i=0;i<=10;i++){
				String fileDir = "imgs/"+((int)(Math.pow(2, i+1)))+".jpg";
				tiles[i] = ImageIO.read(new File(fileDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private int lg(int a){
		return (int)(Math.log(a)/Math.log(2));
	}
	
	// 4 - right, 5 - down, 6 - left , 7 - up
	public void merge(int dir){
		grid.rotate(dir);
		if(grid.combine())
			grid.addNum();
		grid.unrotate();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(fieldIMG, 0, 0, this.getWidth(), this.getHeight(), null); //draw the field
		
		for (int i=0;i<grid.getMap().length;i++) // draw the tiles by reading the grid
			for (int j=0;j<grid.getMap()[0].length;j++)
				if (grid.getMap()[i][j]!=0){
					int x =tileX[j]; int y = tileY[i];
					g.drawImage(tiles[lg(grid.getMap()[i][j])-1], x, y, 106,106,null);
				}
		
	}
	
	public int getScore(){
		return grid.getPoints();
	}
	
	public void resetBoard(){
		grid = new Grid();
		
	}
	
	public int statusUpdate(){ // 0-fine, 1-lost game , 2-won game
		int[] stat = grid.status();
		if (stat[1]==1) // 2048 tile exists
			return 2; 
		else if (stat[0]==0) //no more possible moves
			return 1;
		return 0; //continue playing
	}

}
