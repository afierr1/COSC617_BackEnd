import java.util.Scanner;
/*
 * TO-DO: 
 * 
 */

public class MyQueueMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sizeScan = new Scanner(System.in);
		Scanner nameScan = new Scanner(System.in);
			
		System.out.println("How many soldiers? ");
		int size = sizeScan.nextInt();
		
		MyStringQueue firstQueue = new MyStringQueue(size);
		System.out.println("Type " + size + " soldier names.");
		
		for (int i = 1; i <=size; i++) {
			String input = nameScan.nextLine();
			
			NameList name = new NameList(input,i);
			firstQueue.enqueue(name);
		}		
		
		System.out.print("Enter the position => ");
		int position = sizeScan.nextInt(); // user input
		System.out.println("Elimination order: ");
		
		for (int j = 1; j <size; j++) {
			for (int i = 1; i < position; i++) {		
			NameList name = firstQueue.front();
			firstQueue.dequeue();
			firstQueue.enqueue(name);			
		}
		//kill the soldier!
			System.out.println(j + ". " + firstQueue.front().getName()+ "(" + firstQueue.front().getPosition() + ")");
		firstQueue.dequeue();
		}
System.out.println();
System.out.println("The survivor is: "+ firstQueue.front().getName() + "(" + firstQueue.front().getPosition() + ")");
	
	}	
}

//this class implements the queue using a circular array

class NameList {
	private String name;
	private int position;
	
	
	NameList(String name, int position){
		this.name = name;
		this.position = position;
	}
	String getName() {
		return name;
	}
	int getPosition() {
		return position;
	}
}

class MyStringQueue {
	private NameList[] nameList;
	private int size;
	 private int head;
	private int tail;
	private int filler;
	
	MyStringQueue(int size) {
		this.nameList = new NameList[size];
		this.size = size;
		this.head = -1;
		this.tail = -1;
	}
	
	void enqueue(NameList name) {		
		if (tail < size) {
			if (head == -1)  {
				head = 0;
				tail = 0;
				this.nameList[tail] = name;
			} else { 	
			this.tail = (tail+1)%size;
			this.nameList[tail] = name;
			}
		}			
		else 
			System.out.println("This list is full.");
	}
	
	void dequeue() {
		this.head = (head+1)%size;
	}
	
	NameList front() {
		return this.nameList[head];
	}
	
	int getHead() {
		return head;
	}
	
	int getPosition(int i) {
		return nameList[i].getPosition();
	}
}

