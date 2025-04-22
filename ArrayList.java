//package data;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Collection;
public class ArrayList<E> implements Iterable<E>,List<E>{
	private E[] list;
	private int size = 0;

	public ArrayList(Collection<? extends E> c) {
		this(c.size());
		addAll(c);
	}

	public ArrayList(){
		this(2);
	}

	@SuppressWarnings("unchecked")
	public ArrayList(int i){
		this.list = (E[]) new Object[i];
	}

	//@Override
	public boolean add(E thing){
		if (size == list.length) change(size*2);

		this.list[size++] = thing;

		return true;
	}

	//@Override
	public void add(int i, E thing){
		if(i >= size+1 || i<0) return;

		if (size == list.length) change(size*2);

		E temp1 = thing;
		E temp2;
		size+=1;
		for(int j = i; j<size; j++){
			temp2 = list[j];
			list[j] = temp1;
			temp1 = temp2;
		}
	}

	@SuppressWarnings("unchecked")
	private void change(int i){
		E[] make = (E[]) new Object[i];
		for(int j = 0; j < this.size; j++){
			make[j] = this.list[j];
		}
		this.list = make;
	}

	//@Override
	public boolean addAll(Collection<? extends E> c){
		for (E t: c) {
			if(!this.add(t)) return false;
		}
		return true;
	}

	//@Override
	public boolean addAll(int i, Collection<? extends E> c){
		for (E t: c) {
			this.add(i++,t);
		}
		return true;
	}

	//@Override
	public void clear(){
		this.list = (E[]) new Object[size];
		size = 0;
	}

	//@Override
	public boolean contains(Object o){
		for(E t: list){
			if(o.equals(t)){
				return true;
			}
		}
		return false;
	}

	//@Override
	public boolean containsAll(Collection c){
		for(Object o: c){
			if(!contains(o)) return false;
		}

		return true;
	}

	//@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o){
		if(this == o) return true;

		List l;
		if(o instanceof List){
			l = (List) o;
			if (this.size()!=l.size()) return false;
		} else return false;

		for(int i = 0; i<this.size(); i++){
			if(!(list[i]==l.get(i)||(list[i].equals(l.get(i))))) return false;
		}

		return true;
	}

	//@Override
	public E get(int i){
		return i>=0 && i<size? list[i]: null;
	}

	//@Override
	public int indexOf(Object o){
		int i = -1;
		for (int j = 0; j<size; j++){
			if(o.equals(list[j])){
				i = j;
				break;
			}
		}
		return i;
	}

	//@Override
	public int lastIndexOf(Object o){
		int i = -1;
		for (int j = 0; j<size; j++){
			if(o.equals(list[j])){
				i = j;
			}
		}
		return i;
	}

	//@Override
	public boolean isEmpty(){
		return size == 0;
	}

	private class MyIterator implements Iterator{
		int i = 0;

		public boolean hasNext(){
			return i < size;
		}

		public E next(){
			return list[i++];
		}
	}

	//@Override
	public Iterator iterator(){
		return new MyIterator();
	}

	public int size(){
		return size;
	}

	public List<E> subList(int i, int j){
		ArrayList output = new ArrayList(j-i);
		for(int k = 0; k<j; k++){
			output.add(list[k+i]);
		}

		return output;
	}

	@Override
	public int hashCode(){
		return java.util.Arrays.hashCode(this.toArray());
	}

	private class MyListIterator implements ListIterator<E>{
		int i;
		int droped = -1;

		private MyListIterator(){
			i = 0;
		}

		private MyListIterator(int i){
			this.i = i;
		}

		public boolean hasNext(){
			return i < size;
		}

		public E next(){			
			this.droped = i;
			return list[i++];
		}

		public boolean hasPrevious(){
			return i>0;
		}

		public E previous(){
			this.droped = i-1;
			return list[--i];
		}

		public int nextIndex(){
			return i;
		}

		public int previousIndex(){
			return i-1;
		}

		public void add(E e){
			ArrayList.this.add(i++,e);
			droped = -1;
		}

		public void set(E e){
			if (droped != -1) list[droped] = e;			
		}

		public void remove(){
			if (droped != -1) ArrayList.this.remove(i);
		}
	}

	public ListIterator<E> listIterator(){
		return new MyListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int i){
		return new MyListIterator(i);
	}

	public E remove(int i){
		E thing = null;
		if(i<size && i>=0){
			thing = get(i);
			for (int j = i+1; j<size;j++){
				list[j-1] = list[j];
			}

			list[--size] = null;

			if (size <= list.length/4) change(list.length/2);
		}
		return thing;
	}

	public boolean remove(Object o){
		int p = this.indexOf(o);
		if (p == -1) return false;
		return remove(p) == o;
	}

	public boolean removeAll(Collection<?> c){
		boolean change = false;
		for(Object o: c){
			if (remove(o)) change = true;
		}

		return change;
	}

	public boolean retainAll(Collection<?> c){
		boolean changed = false;
		for(E e: this){
			if(!c.contains(e)){
				remove(e);
				changed = true;
			}
		}
		return changed;
	}

	public E set(int i, E e){
		E old = get(i);
		if(i<size && i>=0) list[i] = e;
		return old; 
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a){
		T[] output;
		if(a.length >= size){
			output = a;
		}else output = (T[]) new Object[size];

		for(int i = 0; i<output.length; i++){
			if(i<size) output[i] = (T) list[i];
			else output[i] = null;
		}

		return output;
	}

	public Object[] toArray(){
		Object[] output = new Object[size];
		for(int i = 0; i < size; i++) output[i] = list[i];

		return output;
	}

	public String toString(){
		String output = "[";
		Iterator<E> i = this.iterator();
		output += i.hasNext()? i.next().toString():"";
		while(i.hasNext()){
			E e = i.next();
			output += e == null? "null, ": ", "+e.toString();
		}
		return output+"]";
	}

}