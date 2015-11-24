import java.io.*;
import java.util.*;

public class Process {
	static int no_movies = 1682;
	static String outfile = "proc_inp.data";
	public ArrayList<Integer> process_file(String file){
		BufferedReader br = null;
		BufferedWriter bw = null;
		String currline;
		String[] strings;
		ArrayList<Node> list = new ArrayList<Node>();
		ArrayList<Integer> userlist = new ArrayList<Integer>();
		int[] movies = new int[no_movies];
		Arrays.fill(movies, 0);
		try{
			br = new BufferedReader(new FileReader(file));
			while ((currline = br.readLine()) != null) {
				strings = currline.split("\t");
				movies[Integer.parseInt(strings[1])-1] += 1;
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < no_movies; i++){
			list.add(new Node(i, movies[i]));
		}
		Collections.sort(list, new NodeComparator());
		ArrayList<Integer> most_rated = new ArrayList<Integer>();
		for(int index = 0; index < 100; index++)
			most_rated.add(list.get(index).id);
			//System.out.println(list.get(index).id + " " + list.get(index).count);
		
		System.out.println("processing done");
		return most_rated;
	}
}




