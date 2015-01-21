import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Score> scores = new ArrayList<Score>();
		
		scores.add(new Score(100,"Bot 1"));
		scores.add(new Score(200,"Bot 2"));
		scores.add(new Score(300,"Bot 3"));
		scores.add(new Score(400,"Bot 4"));
		scores.add(new Score(500,"Bot 5"));
		
		Collections.sort(scores);
		
		
		SaveHandler.saveScore(scores);
		
		
		
		/*ArrayList<Score> readscore;
		readscore = SaveHandler.readScore();
		
		for (Score s : readscore)
			System.out.println(s);*/
		
		
	}
	
	
	
}
