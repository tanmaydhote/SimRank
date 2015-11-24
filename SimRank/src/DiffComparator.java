import java.util.Comparator;


class DiffComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.sim < o2.sim)
			return 1;
		else if(o1.sim > o2.sim)
			return -1;
		else
			return 0;
	}
	
}
