public class PriorityQueue<E extends Comparable<E>>{
	//A queue where the next item removed is the one with highest Priority, uses a heap, insert and delMax take O(log(n)) (unless changing array size)
	E[] list;
	//list[0] is empty for convenience
	int last = 1;

	public PriorityQueue(){
		list = (E[]) new Comparable[4];
	}

	public PriorityQueue(Iterable<E> i){
		this();
		for(E e: i){
			if(last>=list.length) resize(2*list.length);
			list[last++] = e;
		}
		for(int j = last/2; j>0; j--) sink(j);
	}

	public PriorityQueue(java.util.Collection<E> c){
		this((E[])c.toArray());
	}

	public PriorityQueue(E[] l){
		list = (E[]) new Comparable[l.length+1];
		System.arraycopy(l, 0, list, 1, l.length);
		last = list.length;
		for(int i = last/2; i>0; i--) sink(i);
	}

	public PriorityQueue(int i){
		list = (E[]) new Comparable[i];
	}

	public void insert(E thing){
		if(last>=list.length) resize(list.length*2);

		list[last] = thing;
		int index = last++;
		int root = index/2;
		while(index>1){
			if(list[index].compareTo(list[root])>0){
				swap(index,root);
				index = root;
				root = index/2;
			}else break;
		}
		return;
	}

	public E max(){
		return list[1];
	}

	public E delMax(){
		E output = list[1];
		if(!isEmpty()){
			swap(1,--last);
			list[last] = null;
			sink(1);
			if(list.length/4>last) resize(list.length/2);
		}
		return output;
	}

	private void sink(int i){
		int index = i;
		int child = 1;
		while(index*2<last){
			child = index*2;

			if(child + 1 <last && list[child].compareTo(list[child+1])<0) child ++;

			if(list[child].compareTo(list[index])>0) swap(child, index);
			else break;

			index = child;
		}
	}

	private void swap(int i, int j){
		E temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}

	public boolean isEmpty(){
		return last<=1;
	}

	public int size(){
		return last-1;
	}

	private void resize(int i){
		E[] temp = (E[]) new Comparable[i];
		System.arraycopy(list, 0, temp, 0, last);
		list = temp;
	}
}