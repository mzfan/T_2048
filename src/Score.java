import java.io.Serializable;


public class Score implements Comparable<Score>, Serializable{
	
	private int score;
	private String name;
	
	public Score(int s, String n){
		if (n.equals(""))
			n="unknown";
		score = s;
		name = n;
	}
	public Score(int s){
		this(s,"unknown");
	}
	
	public int getScore(){
		return this.score;
	}
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		String label= String.format("%-8d  %8s", score,name);
		return label;
	}
	
	@Override
	public int compareTo(Score other) {
		if (score!=other.score)
			return other.score - score;
		return name.compareTo(other.name);
	}

}
