import java.util.*;
import java.io.*;

public class Main {
	static int no_close = 100;
	static int max_iterations = 10;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		BufferedWriter bw = null;
		String file = "/home/tanmay/workspace/SimRank//Wiki.txt";
		HashMap<Integer, Integer> hash1 = new HashMap<Integer, Integer>();
		ArrayList<Integer> userslist = new ProcWiki().process_file("/home/tanmay/workspace/SimRank//Wiki.txt", hash1);
		//String file = "test.data";
		String[] strings;
		double c = 0.8;
		int total = 7115;
		//int total = 5;
		double[][] mat = new double[total][total];
		double[][] sim = new double[total][total];
		double[][] temp_sim = new double[total][total];
		ArrayList<ArrayList<Integer>> neighbours = new ArrayList<ArrayList<Integer>>();
		int[] num = new int[total];
		for(int i = 0; i < total; i++){
			neighbours.add(new ArrayList<Integer>());
			Arrays.fill(sim[i], 0);
			Arrays.fill(temp_sim[i], 0);
			sim[i][i] = 1;
			temp_sim[i][i] = 1;
		}
		int user;
		int movie;
		int floor = 0;
		//int floor = 2;
		try{
			String currline;
			br = new BufferedReader(new FileReader(file));
			while((currline = br.readLine()) != null) {
				strings = currline.split("\t");
				user = hash1.get(Integer.parseInt(strings[0]));
				movie = hash1.get(Integer.parseInt(strings[1])+floor);
				neighbours.get(movie).add(user);
				neighbours.get(user).add(movie);
				num[movie] += 1;
				num[user] += 1;
				mat[user][movie] = 1;
				mat[movie][user] = 1;
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		int i, j, x, y;
		double max;
		boolean converge;
		double temp1 = 0, temp2 = 0;
		int n1, n2;
		int count = 0;
//		for(i = 0; i < total; i++){
//			for(j = 0; j < total; j++){
//				System.out.print(mat[i][j] + " " + sim[i][j] + "\t");
//			}
//			System.out.println();
//		}
		while(true){
			count++;
			System.out.println("count " + count);
			for(i = 0; i < total; i++){
				for(j = i+1; j < total; j++){
					temp_sim[i][j] = 0;
					for(x = 0; x < neighbours.get(i).size(); x++){
						n1 = neighbours.get(i).get(x);
						for(y = 0; y < neighbours.get(j).size(); y++){
							n2 = neighbours.get(j).get(y);
							temp_sim[i][j] += sim[n1][n2];
						}
					}
					temp_sim[i][j] = c/(num[i]*num[j]) * temp_sim[i][j];
					temp_sim[j][i] = temp_sim[i][j];
				}
			}
			converge = true;
			for(i = 0; i < total; i++){
				for(j = 0; j < total; j++){
					if(sim[i][j] != temp_sim[i][j]){
						sim[i][j] = temp_sim[i][j];
						converge = false;
					}
				}
			}
			if(converge || count == max_iterations){
				break;
			}
		}
//		for(i = 0; i < total; i++){
//			for(j = 0; j < total; j++){
//				System.out.print(sim[i][j] + "\t");
//			}
//			System.out.println();
//		}
		System.out.println("execution done");
		try {
			bw = new BufferedWriter(new FileWriter("../gensim_wiki.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Node> close = new ArrayList<Node>();
		int dummy = 0;
		for(i = 0; i < total; i++){
			if(userslist.contains(i)){
				close.clear();
				dummy = 0;
				for(j = 0; j < total; j++)
					close.add(new Node(j, sim[i][j]));
				Collections.sort(close, new DiffComparator());
				try {
					for(j = 0; j < total; j++){
						if(close.get(j).id >= floor){
							bw.write(close.get(j).id + "\t");
							dummy++;
						}
						if(dummy == 100)
							break;
					}
					bw.write("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


