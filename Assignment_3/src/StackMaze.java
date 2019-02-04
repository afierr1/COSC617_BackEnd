import java.io.File;
import java.util.Scanner;
/*
 * TO-DO: start working on solving the maze
 * Task finished: 
 *  - make linked list 
 *  - implement stack w/ linked list
 *  - import text file
 *   - let user input starting point 
 *  
 *  Task to accomplish: 
 *  - traverse through maze starting from left and clockwise
 *  - use stack to keep track of potential paths
 *  - mark paths already traveled
 */
/*
 * INSTRUCTIONS: 
 * to type in a coordinate, user type in a number for the x coordinate and then
 * click enter to type in the y coordinate.
 */
public class StackMaze {
	
	public static void main(String[] args) {
		int xCoord;
		int yCoord;
		File file = new File("maze.txt");
		Scanner userScan = new Scanner(System.in);	
		ImportMaze myMaze = new ImportMaze(file);
		
	
		char[][] maze = myMaze.getMaze();	
		NavigateMaze firstMaze = new NavigateMaze(maze);
		myMaze.printMaze();
		/*
		 * For loop allows user to retype a new coordinate to find an exit.
		 */
		while(!firstMaze.hasEscaped) {
		
			System.out.println("Please enter a starting position(x,y)");
		
			xCoord = userScan.nextInt();
			yCoord = userScan.nextInt();
		
		
			MyCoordinates userCoord = new MyCoordinates(xCoord,yCoord);
		
			userCoord.setDisplay(maze[xCoord][yCoord]);
				
			//check if start point is 1 or E
			while(!firstMaze.isWall(userCoord)) {
				System.out.println("You cannot start from this coordinate. Please type in another starting position (x,y): ");
				xCoord = userScan.nextInt();
				yCoord = userScan.nextInt();
			
				userCoord = new MyCoordinates(xCoord,yCoord);
			
				if (userCoord.isOutOfBound()) {
				continue;
				}
				userCoord.setDisplay(maze[xCoord][yCoord]);
				System.out.println(userCoord.getDisplay());
			}
		
			firstMaze.startNavigation(userCoord);
	
			firstMaze.printMaze();
			if(!firstMaze.hasEscaped) {
			System.out.println("Please try again.");
			}
		} // end for loop
	}// end main class
} // end stackMaze class
/*
 * This class imports the maze from a text file
 */

class NavigateMaze {
	private char[][] maze;
	MyStack stack;
	boolean hasEscaped = false;
	
	NavigateMaze(char[][] maze) {
		this.maze = maze;	
		stack = new MyStack();
	}
boolean hasescaped() {
	return hasEscaped;
}
	boolean isWall (MyCoordinates a) {
		
		if (a.getDisplay() == '1') {
			return false;
		} else
			return true;
	}
	void startNavigation(MyCoordinates coord) {
		if (this.maze[coord.getRow()][coord.getColumn()] == 'E') {
			System.out.println("You typed the exit!");
			return;
		}
		
		stack.push(coord);
		this.maze[coord.getRow()][coord.getColumn()] = 'S';
		
		while(!stack.isEmpty()) {
			

			MyCoordinates currentPos = stack.top();
			
			if (this.maze[currentPos.getRow()][currentPos.getColumn()] == 'E') {
				hasEscaped = true;
				break;
			}
			
			stack.pop();
			if (this.maze[currentPos.getRow()][currentPos.getColumn()] != 'S')
				this.maze[currentPos.getRow()][currentPos.getColumn()] = '+';

			if(currentPos.getColumn() > 0) {   //check north
				if(maze[currentPos.getRow()][currentPos.getColumn()-1] == '0'|| maze[currentPos.getRow()][currentPos.getColumn()-1] == 'E') {
					stack.push(new MyCoordinates(currentPos.getRow(),currentPos.getColumn()-1));			
				}
			}
			if(currentPos.getRow() > 0) {   //check west
				if(maze[currentPos.getRow()-1][currentPos.getColumn()] == '0' || maze[currentPos.getRow()-1][currentPos.getColumn()] == 'E') {
					stack.push(new MyCoordinates(currentPos.getRow()-1,currentPos.getColumn()));					
				}
			}
			if(currentPos.getRow() <= 18) {   //check east
				if(maze[currentPos.getRow()+1][currentPos.getColumn()] == '0' || maze[currentPos.getRow()+1][currentPos.getColumn()] == 'E') {
					stack.push(new MyCoordinates(currentPos.getRow()+1,currentPos.getColumn()));				
				}
			}
			if(currentPos.getColumn() <= 18) {   //check south
				if(maze[currentPos.getRow()][currentPos.getColumn()+1] == '0' || maze[currentPos.getRow()][currentPos.getColumn()+1] == 'E') {
					stack.push(new MyCoordinates(currentPos.getRow(),currentPos.getColumn()+1));
				}
			}
		} // end while loop
		if (hasEscaped) {
			System.out.println("I am free!");
		} else
			System.out.println("Help, I am trapped!");
	}
	
	void printMaze() {
		System.out.print("   ");
		for (int i = 0; i < 20; i++) {
			if (i < 10)
				System.out.print(i + "  ");
			else 
				System.out.print(i+ " ");
		}
		
		System.out.println();
		for (int r = 0; r < 20; r++) { // colum
			if (r < 10)
				System.out.print(r + "  ");
			else 
				System.out.print(r+ " ");
			for (int m = 0; m < 20; m++) { // row
				System.out.print(maze[m][r] + "  ");
			}
			System.out.println();
		}
	}
	 
}
class ImportMaze {
	private char[][] maze;
	
	ImportMaze(File file) {
		this.maze = new char[20][20];
		createMaze(file);		
	}
	
	private void createMaze(File file) {
		
		try {
			Scanner scan = new Scanner(file);
			int k = 0; // represents row index
			
			while (scan.hasNext()) {
				String input = scan.next();
				
				for (int i = 0; i < 20; i++) { // imports the entire row
					this.maze[i][k] = input.charAt(i);
				}
				k++; // move to next column
			} 
		} catch (Exception e) {
				System.out.println("File not read.");
		}
	}
	
	char[][] getMaze() {
		return maze;
	}
	void printMaze() {
		System.out.print("   ");
		for (int i = 0; i < 20; i++) {
			if (i < 10)
				System.out.print(i + "  ");
			else 
				System.out.print(i+ " ");
		}
		
		System.out.println();
		for (int r = 0; r < 20; r++) { // colum
			if (r < 10)
				System.out.print(r + "  ");
			else 
				System.out.print(r+ " ");
			for (int m = 0; m < 20; m++) { // row
				System.out.print(maze[m][r] + "  ");
			}
			System.out.println();
		}
	}
	
} // end import class
/*
 * This is the stack data structure that will use a linked list.
 */
class MyStack extends MyLinkedList{
	
	void push(MyCoordinates a) {
		insertLast(a);
	}
	void pop() {
		deleteLast();
	}
	MyCoordinates top() {
		return getTail();
	}
	
	boolean isEmpty() {
		if (getHead() == null)
			return true;
		else 
			return false;
	}
} // end stack class
/*
 * This class will hold the coordinates that will be organized
 * in a stack data structure.
 */
class MyCoordinates {
	private char display;
	private int row;
	private int column;
	
	MyCoordinates() {
		
	}
	MyCoordinates(int row, int column) {
		this.row = row;
		this.column = column;
	}

	void setRow(int x) {
		this.row = x;	
	}
	void setColumn(int y) {
		this.column = y;
	}
	void setDisplay(char display) {
		this.display = display;
	}
	int getRow() {
		return this.row;
	}
	int getColumn() {
		return this.column;
	}
	char getDisplay() {
		return this.display;
	}	
	boolean isOutOfBound() {
		if (this.column > 19 || this.column < 0) {
			return true;
		} else if (this.row < 19 || this.row < 0) {
			return true;
		} else 
			return false;	
	}
	
} // end MyCoordinates

/*
 * These classes set up the linked list that will be used to create a stack.
 */

class MyLinkedList {
	private Node head;
	private Node tail;
	
	MyLinkedList() {
		this.head = null;
	}
	
	void insertLast(MyCoordinates a) {
		if (head == null) {
			head = new Node(a);
			tail = head;
			head.previous = null;
		} else {
			tail.next = new Node(a);
			tail.next.previous = tail;
			tail = tail.next;
			tail.next = null;
		}	
	}
	
	void deleteLast() {
		if (tail == head) {
			head = null;
		} else {
		tail.previous.next = null;
		tail = tail.previous;	
		}
	}

	void printList() {
		Node temp = head;
		while ( temp!= null) {
			System.out.println(temp.getCoordinates().getRow() + " " + temp.getCoordinates().getColumn());
			temp = temp.next;
		}
	}
	
	MyCoordinates getHead() {
		if (head == null)
			return null;
		else 
		return head.a;
	}
	MyCoordinates getTail() {
		return tail.a;
	}
	//Only MyLinkedList can see this 
	private class Node {
		private MyCoordinates a;
		private Node next;
		private Node previous;
		
		Node(MyCoordinates a) {
			this.a = a;
		}
		MyCoordinates getCoordinates() {
			return a;
		}
	}
} // end MyLinkedList