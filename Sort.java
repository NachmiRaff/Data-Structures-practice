public class Sort{

	public static void main(String[] args){
		int size = (int) Math.pow(2,15)-1;
		long start;
		long end;

		Integer[] sort = newRandom(size);

		start = System.currentTimeMillis();
		System.out.println(isSorted(heapSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		System.out.print("Heap Sort took ");
		System.out.println(end-start);

		start = System.currentTimeMillis();
		System.out.println(isSorted(quickSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		//print(mergeSort(java.util.Arrays.copyOf(sort,size)));
		System.out.print("Quick Sort took ");
		System.out.println(end-start);

		start = System.currentTimeMillis();
		System.out.println(isSorted(mergeSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		System.out.print("Merge Sort took ");
		System.out.println(end-start);

		start = System.currentTimeMillis();
		System.out.println(isSorted(shellSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		System.out.print("Shell Sort took ");
		System.out.println(end-start);

		start = System.currentTimeMillis();
		System.out.println(isSorted(insertionSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		System.out.print("Insertion Sort took ");
		System.out.println(end-start);

		start = System.currentTimeMillis();
		System.out.println(isSorted(selectionSort(java.util.Arrays.copyOf(sort,size))));
		end = System.currentTimeMillis();
		System.out.print("Selection Sort took ");
		System.out.println(end-start);

		//print(sort);
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(sort);
		
		Integer[] thing = new Integer[size];
		for(int i = size-1; i>=0; i--){
			thing[i] = q.delMax();
		}
		//print(thing);
		System.out.println(isSorted(thing));
	}

	public static Comparable[] heapSort(Comparable[] array){		
		int end = array.length-1;
		for(int i = array.length/2-1; i>=0; i--){
			sink(array, i, end);
		}
		for(int i = 0; i<array.length; i++){
			swap(array,0,end--);
			sink(array, 0, end);
		}
		return(array);
	}
	private static void sink(Comparable[] array, int index, int end){
		int next = index*2+1;
		while(next<=end){
			if(next+1<=end && less(array[next], array[next+1])) next++;

			if(less(array[index],array[next])) swap(array, index, next);
			else break;

			index = next;
			next = index*2+1;
		}
	}

	public static Comparable[] quickSort(Comparable[] array){
		quickSort(array,0,array.length-1);
		return array;
	}
	private static void quickSort(Comparable[] array, int lo, int hi){
		if(hi<=lo){
			return;
		}else{
			Comparable pivot = array[(hi+lo)/2];
			int lo2 = lo;
			int hi2 = hi;
			while(true){
				while(lo2<=hi && less(array[lo2],pivot)) lo2++;
				while(lo2>=lo && less(pivot,array[hi2])) hi2--;

				if(lo2<=hi2) swap(array,lo2++,hi2--);
				else break;
			}
			quickSort(array, lo, hi2);
			quickSort(array, lo2, hi);
		}
	}

	public static Comparable[] mergeSort(Comparable[] array){
		Comparable[] hold = new Comparable[array.length];
		mergeSort(array, 0, array.length, hold);
		return array;
	}
	private static void mergeSort(Comparable[] array, int i, int j, Comparable[] hold){
		if(i+1>=j){
			return;
		}else if(j-i<15){
			insertionSortPart(array,i,j);
		}else{
			int half = i+(j-i)/2;
			mergeSort(array, i, half,hold);
			mergeSort(array, half, j,hold);
			int i2 = i, half2 = half, l = 0; 
			while(i2<half && half2<j){
				if(less(array[half2],array[i2])){
					hold[l++] = array[half2++];
				}else{
					hold[l++] = array[i2++];
				}
			}
			while(half2<j){
				hold[l++] = array[half2++];
			}
			while(i2<half){
				hold[l++] = array[i2++];
			}
			for(int k = i; k<j; k++){
				array[k] = hold[k-i];
			}
		}
		return;
	}
	private static Comparable[] insertionSortPart(Comparable[] array, int start, int end){
		for(int i = start+1; i<end; i++){
			for(int j = i; j>start && less(array[j],array[j-1]); j--) swap(array,j-1,j);
		}
		return array;
	}

	public static Comparable[] shellSort(Comparable[] array){
		long k = Math.round(Math.log(array.length+1)/Math.log(2));
		int add = (int) Math.pow(2,k)-1;
		if (add <= 0) add = 1;
		for (; add>=1; add = (int) Math.pow(2,k)-1){
			for(int i = add; i<array.length; i+=add){
				for(int j = i; j>0 && less(array[j],array[j-add]); j-=add) swap(array,j-add,j);
			}
			k--;			
		}
		return array;
	}
	

	//Insertionsort
	public static Comparable[] insertionSort(Comparable[] array){
		for(int i = 1; i<array.length; i++){
			for(int j = i; j>0 && less(array[j],array[j-1]); j--) swap(array,j-1,j);
		}
		return array;
	}

	//SelectionSort
	public static Comparable[] selectionSort(Comparable[] array){
		int min;
		for(int i = 0; i<array.length; i++){
			min = i;
			for(int j = i; j<array.length; j++){
				if( less(array[j], array[min]) ) min = j;
			}
			swap(array,i,min);
		}
		return array;
	}





	private static boolean less(Comparable i, Comparable j){
		return (i.compareTo(j) < 0);
	}
	private static Comparable[] swap(Comparable[] array, int i, int j){
		Comparable temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}
	private static boolean isSorted(Comparable[] array){
		/*System.out.print("[");
		for(int i = 0; i<array.length-1; i++)System.out.print(array[i] +", ");
		System.out.println(array[array.length-1]+"]");*/

		for(int i = 0; i<array.length-1; i++){
			if(array[i].compareTo(array[i+1])>0) return false;
		}
		return true;
	}
	private static Integer[] newRandom(int size){
		java.util.Random r = new  java.util.Random();
		Integer[] list = new Integer[size];
		for(int i = 0; i<size; i++){
			list[i] =  r.nextInt();
		}
		return list;
	}
	private static void print(Object[] array){
		System.out.print("[");
		for(int i = 0; i<array.length-1; i++){
			System.out.print(array[i]);
			System.out.print(", ");
		}
		System.out.print(array[array.length-1]);
		System.out.println("]");
	}
}