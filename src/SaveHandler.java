import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class SaveHandler {
	
	public static void saveScore(ArrayList<Score> scores){
	 
		   try{
	 
			FileOutputStream fout = new FileOutputStream("scores.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(scores);
			oos.close();
			System.out.println("saved.");
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
		   }
	}
	
	public static ArrayList<Score> readScore(){
		 
		   ArrayList<Score> scores;
	 
		   try{
	 
			   FileInputStream fin = new FileInputStream("scores.sav");
			   ObjectInputStream ois = new ObjectInputStream(fin);
			   scores = (ArrayList<Score>) ois.readObject();
			   ois.close();
	 
			   return scores;
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
	} 

}
