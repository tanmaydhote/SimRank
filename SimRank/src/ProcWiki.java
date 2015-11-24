import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ProcWiki {
	static int no_users = 7115;
	//static int no_movies = 3;
	//static int no_most_rated = 2;
	static int no_most_rated = 100;
	//HashMap<Integer, Integer> hash = new HashMap<>();
	static String outfile = "proc_inp.data";
	public ArrayList<Integer> process_file(String file, HashMap<Integer, Integer> hash){
		BufferedReader br = null;
		String currline;
		int no_nodes = 0;
		int temp1, temp2;
		String[] strings;
		ArrayList<Node> list = new ArrayList<Node>();
		//ArrayList<Integer> userlist = new ArrayList<>();
		int[] movies = new int[no_users];
		Arrays.fill(movies, 0);
		try{
			br = new BufferedReader(new FileReader(file));
			while ((currline = br.readLine()) != null) {
				strings = currline.split("\t");
				temp1 = Integer.parseInt(strings[1]);
				temp2 = Integer.parseInt(strings[0]);
				if(!hash.containsKey(temp1)){
					hash.put(temp1, no_nodes);
					no_nodes++; 
				}
				movies[hash.get(temp1)] += 1;
				if(!hash.containsKey(temp2)){
					hash.put(temp2, no_nodes);
					no_nodes++; 
				}
				movies[hash.get(temp2)] += 1;
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < no_users; i++){
			list.add(new Node(i, movies[i]));
		}
//		Map<Integer, Integer> rev = new HashMap<>();
//		for(Entry<Integer, Integer> entry : hash.entrySet()){
//		    rev.put(entry.getValue(), entry.getKey());
//		}
		Collections.sort(list, new NodeComparator());
		ArrayList<Integer> most_rated = new ArrayList<Integer>();
		for(int index = 0; index < no_most_rated; index++){
			most_rated.add(list.get(index).id);
			
		}
			//System.out.println(list.get(index).id + " " + list.get(index).count);
		
		System.out.println("processing done");
		return most_rated;
	}
}




