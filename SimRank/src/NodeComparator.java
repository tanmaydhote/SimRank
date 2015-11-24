import java.util.Comparator;


class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.count < o2.count)
			return 1;
		else
			return -1;
	}
	
}
