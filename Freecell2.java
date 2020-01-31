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
