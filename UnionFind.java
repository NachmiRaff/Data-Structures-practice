public class UnionFind{
	int[] list;
	int[] size;
	int count;
	public UnionFind(int n){
		list = new int[n];
		size = new int[n];
		count = n;
		for(int i = 0; i<n; i++){
			list[i] = i;
			size[i] = 1;
		}
	}

	public void union(int p, int q){
		int p2 = find(p);
		int q2 = find(q);
		if(p2 == q2) return;
		if(size[q2]<size[p2]){
			list[q2] = p2;
			size[p2] += size[q2];
		}else{
			list[p2] = q2;
			size[q2] += size[p2];
		}
		count--;
	}

	public int find(int q){
		int index = q;
		while(index != list[index]) index = list[index];
		int next = q;
		while(index != list[q]){
			next = list[q];
			list[q] = index;
			q = next;
		}
		return index;
	}

	public boolean connected(int p, int q){
		return find(p) == find(q);
	}

	public int getSize(){
		return count;
	}

}