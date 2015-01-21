import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;


public class Grid {
	
	private int[][] map;
	private int rotations;
	
	private int points;
	
	public Grid(){
		map = new int[4][4];
		rotations=0;
		points = 0;
		
		int x1= (int)(Math.random()*4);
		int y1= (int)(Math.random()*4);
		int x2,y2;
		do{
			x2 = (int)(Math.random()*4);
			y2 = (int)(Math.random()*4);
		}while (x1==x2 && y1==y2);
		
		double four = Math.random();
		if (four>0.75)
			map[x1][y1]=4;
		else
			map[x1][y1]=2;
		map[x2][y2]=2;
	}
	
	// r = # of rotations
	public void rotate(int r){ // rotates the 4x4 grid counter-clockwise
		for (int k=0;k<r%4;k++){
			int[][] temp = new int[4][4];
			
			for (int i=0;i<4;i++)
				for (int j=0;j<4;j++)
					temp[3-j][i]=map[i][j];
			map=temp;
		}
		rotations+=r%4;
	}
	public void unrotate(){ // rotates clockwise until back to original state.
		for (int k=0;k<rotations%4;k++){
			int[][] temp = new int[4][4];
			
			for (int i=0;i<4;i++)
				for (int j=0;j<4;j++)
					temp[j][3-i]=map[i][j];
			map=temp;
		}
		rotations=0;
	}
	
	public boolean addNum(){ //adds new number to a random coordinate on the map (can be 2 or 4),returns true if adding a num is possible
		//make a list of possible empty spots to add to, then randomize from these existing coordinates
		ArrayList<int[]> empty = new ArrayList<int[]>();
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				if (map[i][j]==0){
					int[] coord = {i,j};
					empty.add(coord);
				}
		if (!empty.isEmpty()){ // if there are possible coordinates to add to...
			int randCoord = (int)(Math.random()*empty.size()); //picks index of a random coordinate from list of empty coordinates
			if (Math.random()>0.75){ // add a 4
				map[empty.get(randCoord)[0]][empty.get(randCoord)[1]]=4;
			}else{ // add a 2
				map[empty.get(randCoord)[0]][empty.get(randCoord)[1]]=2;
			}
			return true;
		}
		return false;
	}
	
	public boolean combine(){ // merges similar numbers one step to the right (use with rotate to merge in different directions)
		// need a temp haveMerged boolean to make sure double merging doesnt occur
		boolean combined=false;
		for (int r=0;r<4;r++){
			boolean merged=false; // if the previous number has already merged
			Deque<Integer> merger = new LinkedList<Integer>();
			int[] newRow = new int[4];
			
			for (int c=3;c>=0;c--){
				//merge each row to the right and store in the merger stack
				if (map[r][c]!=0){
					if (merger.isEmpty() || merger.peek()!=map[r][c] || merged){
						merger.push(map[r][c]);
						merged=false;
					}else{
						merger.pop();
						merger.push(map[r][c]*2);
						points+=map[r][c]*2;
						merged=true;
					}
				}	
			}
			for (int i=3;i>=0;i--){ //empty the stack into the new row
				if (!merger.isEmpty())
					newRow[i]=merger.pollLast();
				else
					break;
			}
			if (!Arrays.equals(map[r], newRow)){	
				map[r]=newRow; //replace the map's row with the new merged row
				combined=true;
			}
		}
		return combined;
	}
	
	public int[] status(){ // returns a 2 number array (num1: if there are possible moves on the grid, num2: if 2048 exists, num3:isFull)
		int[] state = new int[3]; //possibly 3 if need a isFull condition
		state[2]=1;
		for (int r=0;r<4;r++){ //check for 2048 tile, if the board is full
			for (int c=0;c<4;c++){
				if (map[r][c]==2048)
					state[1]=1;
				if (map[r][c]==0)
					state[2]=0;
			}
		}
		if (state[2]==1){ //if the board is full, check for possible moves
			rotating : for (int i=0;i<=1;i++){ //check horizontally, then vertically
				rotate(i);
				for (int r=0;r<4;r++)
					for (int c=0;c<3;c++)
						if (map[r][c]==map[r][c+1]){
							state[0]=1;
							break rotating;
						}		
			}
			unrotate();
		}else
			state[0]=1;
			
		return state;
	}
	
	public int[][] getMap(){
		return this.map;
	}
	public void resetPoints(){
		this.points=0;
	}
	public int getPoints(){
		return this.points;
	}
	
	public String toString(){
		String output="";
		for (int i=0;i<map.length;i++){
			for (int j=0;j<map[0].length;j++)
				output+=map[i][j];
			output+="\n";
		}
		return output;
	}

}
