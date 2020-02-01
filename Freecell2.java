import java.util.*;

public class Freecell2 {
	public static void main (String[] args) {
		ArrayList<Stack<String>> column = new ArrayList<>();
		Stack<String> stck1 = new Stack<>();
	    column.add (stck1);
	    Stack<String> stck2 = new Stack<>();
	    column.add (stck2);
	    Stack<String> stck3 = new Stack<>();
	    column.add (stck3);
	    column.get(0).add ("d2");
	    column.get(1).add ("h3");
	    column.get(2).add ("s4");
	    column.get(1).add ("d2");
	    column.get(2).add ("h3");
	    column.get(0).add ("s4");
	    column.get(2).add ("d2");
	    column.get(0).add ("h3");
	    column.get(1).add ("s4");
	    System.out.println(column.get(0).toString());
	    System.out.println(column.get(1).toString());
	    System.out.println(column.get(2).toString());
	}
}

// class ColumnRotation {
//   public static void main (String[] args) {
//     Stack<String> column = new Stack<>();
//     column.add ("cA");
//     column.add ("d2");
//     column.add ("h3");
//     column.add ("s4");

//     Scanner input = new Scanner (System.in);
//     String cmd;
//     do {
//       System.out.println ("Column 1: " + column.toString());
//       System.out.print   ("Command > ");
//       // Get first letter.
//       cmd = input.next().substring(0,1);
//       // If a column name is entered, rotate
//       // the column by moving last element to front.
//       if (cmd.equals("1")){
//       	column.add(0, column.remove(column.size()-1));
//       }
//     } while (cmd.equals("1"));
//     System.out.println ("Column 1: " + column);
//   }
// }

// import java.util.*;

// public class Freecell2.java {
//   public static void main(String[] args) {
//   	Stack<String> stack = new Stack<>();

//   }
// }

// class Board {
// 	Map<Character, String> map
// }

// class Column {
// 	private ArrayList<String> col = new ArrayList<>();
// 	public ArrayList<String> getCol() {
// 		return col;
// 	}

// }
// class Pile {
// 	private Stack<String> pile = new Stack<>();
// 	public Stack<String> getPile() {
// 		return pile;
// 	}
// }


//==========================================================================================
//==========================================================================================


// class Stack<E> extends Vector<E>{
// 	private static final long serialVersionUID = 4861409159567933289L;
// 	private java.util.ArrayList<E> list = new java.util.ArrayList<>();
  
//     public int getSize() {
//       return list.size();
//     }

//     public E get(int index){
//         return list.get(index);
//     }
  
//     public E peek() {
//       return list.get(getSize() - 1);
//     }
  
//     public void push(E o) {
//       list.add(o);
//     }
  
//     public E pop() {
//       E o = list.get(getSize() - 1);
//       list.remove(getSize() - 1);
//       return o;
//     }
  
//     public boolean isEmpty() {
//       return list.isEmpty();
//     }

//     public int search(E o){	//problem
//         boolean exist = false;
//         int i=0;
//         for( ; i<list.size(); i++){
//             if(list.get(i) == o){
//                 exist=true;
//                 break;
//             }
//         }
        
//         if(exist) {
//             return i;
//         } 
//         else {
//             return -1;
//         }
//     }

//     public void clear(){
//         list.clear();
//     }

//     @Override
//     public String toString() {
//       return list.toString();
//     }
//   }

// class OrderedStack<E> extends Stack<E> {
// 	private static final long serialVersionUID = 1789530492784494673L;
// 	private ArrayList<E> ordStack = new ArrayList<>();
//     @Override
//     public void push(E o){
//         if(ordStack.size() == 0 && getPoints(o) == 1){
//             ordStack.add(o);
//         }
//         else if(getPoints(o) - getPoints(ordStack.get(ordStack.size()-1)) == 1){
//             ordStack.add(o);
//         }
//         else{
//             System.out.println("Invalid insertion!");
//         }
//     }
//     public int getPoints(E card){
//         int points = 0;
//         String c = (String)card;
//         char face = c.charAt(1);
//         switch (face) {
//             case 'A': points = 1; break;
//             case 'X': points = 10; break;
//             case 'J': points = 11; break;
//             case 'Q': points = 12; break;
//             case 'K': points = 13; break;
//             default: points = Character.getNumericValue(face);
//         }
//         return points;
//     }
// }