import java.util.*;

public class Freecell2 {
	public static void command(Board b1) {
		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.println(b1.toString());
		try{
            System.out.print("Command > ");
            String strInput = input.nextLine();
            // Separate the string input by spaces or tabs
            String str[] = null;
			if(strInput.length() != 0){
                 str = strInput.split("\\s+", 0);
            }
            else{
                System.out.println("Try enter with the following format:-");
                System.out.println("FROM WHICH COLUMN<space>WHICH CARD<space>TO WHICH COLUMN/PILE");
                command(b1);
            }
			if(str.length == 1) {
				// r to restart game
                if(str[0].equalsIgnoreCase("r")) {
                    b1.newBoard();
                }
				// x to exit game
                else if(str[0].equalsIgnoreCase("x")){
                    System.out.println("Goodbye");
                    System.exit(0);
                }
                else{
					try{
                        colRotate(b1, str[0]);
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Error: Invalid column entered");
                    }
                    catch(EmptyStackException e){
                        System.out.println("Error: The column is empty");
                    }
                }
                command(b1);
            }
            else if(str.length == 2) {
                if(checkToFrom(b1, str[0], str[1].toLowerCase()))
                    move(b1, str[0], str[1].toLowerCase());
                command(b1);
            }
            else if(str.length == 3){
                if(checkToFrom(b1, str[0], str[2].toLowerCase()))
                    move(b1, str[0], str[1], str[2].toLowerCase());
                command(b1);
            }
            else{
                System.out.println("Error: Invalid instruction entered.");
                System.out.println("Try enter with the following format:-");
                System.out.println("FROM WHICH COLUMN<space>WHICH CARD<space>TO WHICH COLUMN/PILE");
                command(b1);
            }
        }
        catch(NumberFormatException e){
            System.out.println("Error: Invalid instruction entered.");
            System.out.println("Try enter with the following format:-");
            System.out.println("FROM WHICH COLUMN<space>WHICH CARD<space>TO WHICH COLUMN/PILE");
            command(b1);
        }
		input.close();
    }

    public static boolean checkToFrom(Board b1, String from, String to){
        int checkFrom = 1;
        try{
            checkFrom = Integer.parseInt(from.trim());
            if (from.length() > 1 || to.length() > 1){
                System.out.println('\n' + "Error: Enter only 1 character to indicates the pile or column" + '\n');
                return false;
            }
            else if ( checkFrom < 1 || checkFrom > 9 ||
                      !(b1.getBoards().containsKey(to.charAt(0)))){
                System.out.println("Input out of range");
                return false;
            }
            else if( b1.getBoards().get(from.charAt(0)).isEmpty() ) {
                System.out.println("Column is empty");
            }
            return true;
        }
        catch(NumberFormatException e){
            System.out.println("Error: " + e.getMessage());
            System.out.println('\n' + "Enter a column number to move from" + '\n');
            return false;
        }
    }

	// check if the deck of cards is following rules
    public static boolean checkMultMove(Board b1, char from, char to, int searchIndex){
        int current = b1.getBoards().get(from).size() - searchIndex;
        for(int i = current; i < b1.getBoards().get(from).size() - 1; i++){
            if(getPoints(b1.getBoards().get(from).get(i)) - getPoints(b1.getBoards().get(from).get(i + 1)) != 1){
                System.out.println(b1.getBoards().get(from).get(i) + " and " + b1.getBoards().get(from).get(i + 1) + "not following order!");
                return false;
            }
        }
		if(b1.getBoards().get(to).size() == 0){
            return true;
        }
        else if(getPoints(b1.getBoards().get(to).peek()) - getPoints(b1.getBoards().get(from).get(current)) == 1){
            return true;
        }
        else{
            System.out.println("Invalid destination!");
            return false;
        }
    }

    public static void move(Board b1, String from, String to){
        char toKey = to.charAt(0);
        char fromKey = from.charAt(0);

        if(Character.isLetter(to.charAt(0))){
            int prevPoints = 0;
            if(b1.getBoards().get(toKey).isEmpty())
                prevPoints = 0;
            else
                prevPoints = getPoints(b1.getBoards().get(toKey).peek());

            if(prevPoints - getPoints(b1.getBoards().get(fromKey).peek()) == -1 &&
                b1.getBoards().get(fromKey).peek().charAt(0) == toKey){
                    System.out.println("checkMultMove in 2move = true");
                    try{
                        b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
                        System.out.println("Pile\t" + toKey + ": " +  b1.getBoards().get(toKey));
                    }
                    catch(IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
            }
            else{
                System.out.println("Invalid destination!");
            }
        }
        else if(checkMultMove(b1, fromKey, toKey, 1)){
            b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
        }
        else{
            System.out.println("Invalid destination!");
        }
    }

    public static void move(Board b1, String from, String whatCard, String to) {
        if(whatCard.length() != 2 || !isElementOfCards(b1, whatCard) ||
		   !b1.getBoards().get(from.charAt(0)).contains(whatCard)){
            System.out.println("Invalid card entered!");
        }
        else{
            char toKey = to.charAt(0);
            char fromKey = from.charAt(0);
            int fromSearch = b1.getBoards().get(fromKey).search(whatCard);

            if(fromSearch == 1){
                move(b1, from, to);
            }
            else{
                if(Character.isLetter(toKey)){
                    ArrayList<String> temp = new ArrayList<>();
					// the points of to's last element
                    int prevPoints = 0;
                    if(b1.getBoards().get(toKey).isEmpty())
                        prevPoints = 0;
                    else{
                        prevPoints = getPoints(b1.getBoards().get(toKey).peek());
                    }
					// use temp list to check if it follows the move rules
                    for(int i = fromSearch; i>0; i--){
                        temp.add(b1.getBoards().get(fromKey).pop());
                    }
					System.out.println("temp: " + temp);
					boolean validPoints = true, validSuit = true;
					for(int i=0; i<temp.size()-1; i++){
						if( getPoints(temp.get(i)) + 1 != getPoints(temp.get(i+1)) )
							validPoints = false;
						if(temp.get(i).charAt(0) != temp.get(i+1).charAt(0))
							validSuit = false;
					}
                    if(validPoints && validSuit && whatCard.charAt(0) == toKey &&
					   (getPoints(temp.get(0)) == prevPoints + 1) ){
                        for(int i = 0; i<fromSearch; i++){
							try{
								b1.getBoards().get(toKey).push(temp.remove(0));
							}
                            catch(IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
                        }
                    }
                }
                else if(checkMultMove(b1, fromKey, toKey, fromSearch)){
                    Stack<String> temp = new Stack<>();
                    for(int i = fromSearch; i>0; i--){
                        temp.push(b1.getBoards().get(fromKey).pop());
                    }
                    for(int i = fromSearch; i>0; i--){
                        b1.getBoards().get(toKey).push(temp.pop());
                    }
                }
                else{
                    System.out.println("checkMultMove = false");    //change error message
                }

            }
        }
    }

    public static void colRotate(Board b1, String col)
        throws IllegalArgumentException, EmptyStackException{
        char column = col.charAt(0);
        if(Character.isDigit(column)){
			int columnNumber = Integer.parseInt(col);
			if(columnNumber < 1 || columnNumber > 9){
                throw new IllegalArgumentException("Column number out of range");
			}
			else{
                if(b1.getBoards().get(column).isEmpty())
                    throw new EmptyStackException();
                else{
                    int columnSize = b1.getBoards().get(column).size();
                    String removedElement = b1.getBoards().get(column).remove(columnSize-1);
                    b1.getBoards().get(column).add(0, removedElement);
                }
			}
		}
		else if(Character.isLetter(column) && col.length() == 1){
			if(b1.getBoards().containsKey(column))
				throw new IllegalArgumentException("Pile cannot be rotated");
			else
				throw new IllegalArgumentException("Invalid column entered");
		}
		else{
			throw new IllegalArgumentException("Invalid instruction entered");
		}
    }

	public static void checkGameOver(Board b1) {
		boolean gameOver = true;
        char[] pileChar = {'c', 'd', 'h', 's'};
        for(char c : pileChar) {
            Stack<String> temp = b1.getBoards().get(c);
            try{
                for(int i=0; i<12; ++i){
                    if(getPoints(temp.get(i)) != getPoints(temp.get(i+1)) - 1 ){
                        gameOver = false;
                    }
                }
            }
            catch(EmptyStackException e){
                gameOver = false;
            }
            catch(ArrayIndexOutOfBoundsException a){
                gameOver = false;
            }
        }
		if(gameOver)
			gameOverWords();
	}

	public static void gameOverWords(){
        System.out.println("\n\n");
        System.out.println("************************************************************************************************");
        System.out.println("************************************************************************************************\n");
        System.out.println(" ####   ####  #    #  ####  #####   ####  #####  ####   ## ##");
        System.out.println("#    # #    # ##   # #    # #    # #    #   #   #    #  ## ##");
        System.out.println("#      #    # # #  # #      #    # #    #   #    ##     ## ##");
        System.out.println("#      #    # #  # # #  ### #####  ######   #      ##   ## ##");
        System.out.println("#    # #    # #   ## #    # # #    #    #   #   #    #       ");
        System.out.println(" ####   ####  #    #  ####  #  ### #    #   #    ####   ## ##");
        System.out.println("\n************************************************************************************************");
        System.out.println("************************************************************************************************");
        System.out.println("\n\n");
        System.out.println("You have finished the game!");
        System.out.println("Goodbye");
        System.exit(0);
	}

	public static boolean isElementOfCards(Board b1, String c) {
        if(b1.getCards().contains(c)) return true;
        else return false;
    }

	public static int getPoints(String card) {
        int points = 0;
        char face = card.charAt(1);
        switch (face) {
            case 'A': points = 1; break;
            case 'X': points = 10; break;
            case 'J': points = 11; break;
            case 'Q': points = 12; break;
            case 'K': points = 13; break;
            default: points = Character.getNumericValue(face);
        }
        return points;
    }

	public static void main(String[] args) {
        Board b1 = new Board();
		command(b1);
	}
}

class Board {
	private ArrayList<String> cards = new ArrayList<>();
	private ArrayList<Stack<String>> stacks = new ArrayList<>();
	private Map<Character, Stack<String>> boards = new LinkedHashMap<>();

	public Board() {
		newBoard();
	}
	public void newBoard() {
		// make sure all is empty
		cards.clear();
		for(Stack<String> s : stacks)
        	s.clear();
		boards.clear();

		// create cards
		// suit: spades(s), hearts (H), clubs (c), diamonds (d)
		String[] suit = {"c","d","h","s"};
		// face: number on the card
		String[] face = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
		for(int i=0; i<4; ++i){
			for(int j=0; j<13; ++j){
				String s = suit[i] + face[j];
				cards.add(s);
			}
		}

		Collections.shuffle(cards); //shuffle the order of cards

		for(int i = 0; i<4; i++){
			Stack<String> addOStack = new OrderedStack<>();
			stacks.add(addOStack);
		}
		for(int i = 0; i<9; i++){
			Stack<String> addStack = new Stack<>();
			stacks.add(addStack);
		}
		int columnCards = 7, icards = 0;
		for(int colNum = 4; colNum < 12; colNum++){
			if(colNum >= 8)
			columnCards = 6;
			for(int j = 1; j <= columnCards; ++j){
				stacks.get(colNum).push(cards.get(icards++));
			}
		}

		// put into map
		boards.put('c', stacks.get(0)); // pile C
		boards.put('d', stacks.get(1)); // pile D
		boards.put('h', stacks.get(2)); // pile H
		boards.put('s', stacks.get(3)); // pile S
		boards.put('1', stacks.get(4)); // column 1
		boards.put('2', stacks.get(5)); // column 2
		boards.put('3', stacks.get(6)); // column 3
		boards.put('4', stacks.get(7)); // column 4
		boards.put('5', stacks.get(8)); // column 5
		boards.put('6', stacks.get(9)); // column 6
		boards.put('7', stacks.get(10)); // column 7
		boards.put('8', stacks.get(11)); // column 8
		boards.put('9', stacks.get(12)); // column 9
	}

	public Map<Character, Stack<String>> getBoards(){
        return boards;
	}

	public ArrayList<String> getCards() {
        return cards;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("");
        int num = 1;
        for(Character key: getBoards().keySet()) {
            if(num >= 5) {
                sb.append("Column\t");
            }
            else{
                sb.append("Pile\t");
				num++;
			}
			sb.append(key);
			sb.append(" : [");
			for(int i=0; i<boards.get(key).size(); i++){
				sb.append(boards.get(key).get(i));
				if(i<boards.get(key).size()-1)  //not last element
                	sb.append(", ");
			}
			sb.append("]\n");
        }
        return sb + "";
    }
}

class OrderedStack<E> extends Stack<E>{
	@Override
	public E push(E card) throws IllegalArgumentException{
		int currentPoints = getPoints(card);
		int prevPoints = 0;
		if(size() == 0)
			prevPoints = 0;
		else{
			prevPoints = getPoints(get(size()-1));
		}
		//check if element add in is one point larger
		if(currentPoints - prevPoints == 1) {
			add(card);
		}
		else{
			throw new IllegalArgumentException("Card is not one points larger");
		}
		return card;
	}

	private int getPoints(E o) {
		int points = 0;
        char face = o.toString().toUpperCase().charAt(1);
        switch (face) {
            case 'A': points = 1; break;
            case 'X': points = 10; break;
            case 'J': points = 11; break;
            case 'Q': points = 12; break;
            case 'K': points = 13; break;
            default: points = Character.getNumericValue(face);break;
		}
        return points;
	}
}
