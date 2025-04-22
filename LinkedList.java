import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
//import java.util.Stack;
public class LinkedList<T> implements List<T>, Iterable<T>, Queue<T>, Stack<T>{

	public static void main(String[] args){
		LinkedList<Integer> a = new LinkedList<>();
		for(int i = 0; i<10; i++){
			a.add(i);
		}
		//System.out.println("\n");
		a.add(0,55);
		//a.add(8,55);
		for(Integer i: a){
			System.out.println(a.get(i));
		}
		System.out.println(a.lastIndexOf(0));

		LinkedList<Integer> b = new LinkedList<>();
		b.add(10);
		b.add(0,5);
		System.out.println(b);
	}

	private Node first;
	private int size;
	private class Node{
		private Node pr;
		private T thing;
		private Node next;
		private Node(T item){
			thing = item;
			pr  = this;
			next = this;
		}

		private Node(Node n, T thing, Node l){
			pr = n;
			next = l;
			this.thing = thing;
		}
	}

	public LinkedList(){
		first = null;
		size = 0;
	}

	public LinkedList(T item){
		first = new Node(item);
		size = 1;
	}

	public LinkedList(Collection<? extends T> c){
		this();
		addAll(c);
	}

	public boolean add(T thing){
		if(size>0){
			first.pr.next = new Node(first.pr, thing, first);
			first.pr = first.pr.next;
		}else{
			first = new Node(thing);
		}
		size++;
		return first.pr.thing == thing;
	}

	public void add(int i, T thing){
		if(size == 0){
			this.add(thing);
		}else if (i == 0){
				first.pr.next = new Node(first.pr,thing,first.next);
				first.pr = first.pr.next;
				first = first.pr;
				size++;
		}else if(i<=size){
			Node use = getNode(i-1);
			use.next.pr = new Node(use, thing, use.next);
			use.next = use.next.pr;
			size++;
		}
	}

	public boolean addAll(Collection<? extends T> c){
		boolean a = true;
		for(T t: c){
			a = add(t) && a;
		}
		return a;
	}

	public boolean addAll(int index, Collection<? extends T> c){
		boolean a = true;
		for(T t: c){
			add(index++,t);
		}
		return a;
	}

	public void clear(){
		first = null;
		size = 0;
	}

	public T get(int i){
		if(i < size && i>=0){
			if(size/2>=i){
				Node use = first;
				for(int j = 1; j<=i; j++){
					use = use.next;
				}
				return use.thing;
			}else{	
				Node use = first.pr;
				for(int j = size-2; j>=i; j--){
					use = use.pr;
				}
				return use.thing;
			}
		}else return null;
	}

	private Node getNode(int i){
		if(i < size && i>=0){
			if(size/2>=i){
				Node use = first;
				for(int j = 1; j<=i; j++){
					use = use.next;
				}
				return use;
			}else{	
				Node use = first.pr;
				for(int j = size-2; j>=i; j--){
					use = use.pr;
				}
				return use;
			}
		}else return null;
	}

	public int size(){
		return size;
	}

	public boolean contains(Object o){
		boolean b  = false;
		for(T t: this){
			if(t.equals(o)){
				b = true;
				break;
			}
		}
		return b;
	}

	public boolean containsAll(Collection<?> c){
		for(Object o: c){
			if(!this.contains(o)){
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o){
		if(this == o) return true;

		List l;
		if(o instanceof List){
			l = (List) o;
			if (this.size()!=l.size()) return false;
		} else return false;

		Iterator i = l.iterator();
		Object other;
		for(T t: this){

			if (i.hasNext()) other = i.next();
			else return false;

			if(!(t==other||(t.equals(other)))) return false;
		}

		return true;
	}

	private class MyListIterator implements ListIterator<T>{
		int i;
		Node n;
		int dropped = -1;

		private MyListIterator(){
			i = 0;
			n = first;
		}

		private MyListIterator(int i){
			this.i = i;
			n = getNode(i);
		}

		public boolean hasNext(){
			return i < size;
		}

		public T next(){			
			this.dropped = i++;
			n = n.next;
			return n.pr.thing;
		}

		public boolean hasPrevious(){
			return i>0;
		}

		public T previous(){
			this.dropped = i-1;
			i--;
			n = n.pr;
			return n.next.thing;
		}

		public int nextIndex(){
			return i;
		}

		public int previousIndex(){
			return i-1;
		}

		public void add(T e){
			LinkedList.this.add(i++,e);
			dropped = -1;
		}

		public void set(T e){
			if (dropped != -1){
				if(dropped == i-1) n.pr.thing = e;
				else if (dropped == i) n.thing = e;
			}		
		}

		public void remove(){
			if (dropped != -1){
				if(dropped == i-1) LinkedList.this.remove(n.pr);
				else if (dropped == i) LinkedList.this.remove(n);
			}
		}
	}

	public Iterator iterator(){
		return new MyListIterator();
	}

	public ListIterator listIterator(int i){
		return new MyListIterator(i);
	}


	public ListIterator listIterator(){
		return new MyListIterator();
	}

	public T remove(int i){
		Node n = getNode(i);
		T t = n.thing;
		remove(n);
		return t;
	}

	private boolean remove(Node n){
		n.pr.next = n.next;
		n.next.pr = n.pr;
		if (first == n) first = first.pr.next;
		size--;
		return true;
	}

	public boolean remove(Object o){
		Node n = first;
		boolean b = false;
		for(int i = 0; i<size; i++){
			if(n.thing.equals(o)){
				b = remove(n);
				break;
			}
			n = n.next;
		}
		return b;
	}

	public boolean removeAll(Collection<?> c){
		boolean b = false;
		for(Object o: c){
			b = remove(o) || b;
		}
		return b;
	}

	public boolean retainAll(Collection<?> c){
		boolean b = false;
		Iterator<T> i = this.iterator();
		T thing;
		int index = -1;
		while(i.hasNext()){
			thing = i.next();
			index++;
			if(!c.contains(thing)){
				this.remove(index);
				b = true;
			}
		}
		return true;
	}

	@Override
	public int hashCode(){
		return java.util.Arrays.hashCode(this.toArray());
	}

	public int indexOf(Object o){
		Node n = first;
		for(int i = 0; i<size; i++){
			if(n.thing.equals(o)){
				return i;
			}
			n = n.next;
		}
		return -1;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public int lastIndexOf(Object o){
		Node n = first.pr;
		for(int i = size-2; i>=0; i--){
			if(n.thing.equals(o)){
				return i;
			}
			n = n.pr;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray(){
		T[] a = (T[]) new Object[size];
		Iterator<T> put = this.iterator();
		for(int i = 0; i<size; i++){
			a[i] = put.next();
		}
		return(a);
	}

	public T set(int index, T element){
		Node n = getNode(index);
		T thing = n.thing;
		n.thing = element;
		return thing;
	}

	public List<T> subList(int start, int end){
		//soon           
		return new LinkedList();
	}

	@SuppressWarnings("unchecked")
	public <E> E[] toArray(E[] a){
		if(a.length < size){
			a = (E[]) new Object[size];
		}
		Iterator<T> put = this.iterator();
		for(int i = 0; i<a.length; i++){
			if(put.hasNext()) a[i] = (E) put.next();
			else a[i] = null;
		}
		return a;
	}

	@Override
	public String toString(){
		String s = "[";
		Iterator i = this.iterator();
		if(i.hasNext()) s+=i.next();
		while(i.hasNext()){
			s+=", " + i.next();
		}
		return s+"]";
	}

	public T element(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException("Qeue is empty");
		}
		return get(0);
	}

	public T remove(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException("Qeue is empty");
		}
		return remove(0);
	}

	public boolean offer(T t){
		return add(t);
	}

	public T poll(){
		if(isEmpty()){
			return null;
		}
		return remove(0);
	}

	public T peek(){
		if(isEmpty()){
			return null;
		}
		return get(0);
	}

	public boolean empty(){
		return isEmpty();
	}

	public T pop(){
		return remove(0);
	}

	public T push(T thing){
		add(0,thing);
		return thing;
	}

	public int search(Object o){
		return indexOf(o)+1;
	}

}