import java.util.*;

/**
* A simple Freecell card game using java
*@author Wong Man Yi
*@author Lee Ming Xuan
*/
public class Freecell {
	/**
	* Prompt, check and process input command from user
	*
	* Method to prompt input from user and check if they are valid command.
	* Proceeds by processing the command if is valid.
	*
	* @param b1 Game board
	*/
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
                System.out.println("\nTry enter with the following format:-");
                System.out.println("FROM WHICH COLUMN<space>WHICH CARD" +
									"<space>TO WHICH COLUMN/PILE");
                command(b1);
            }
			if(str.length == 1) {
				// r to restart game
                if(str[0].equalsIgnoreCase("r")) {
                    b1.newBoard();
                }
				// x to exit game
                else if(str[0].equalsIgnoreCase("x")){
					System.out.println("\nThank you and see you again");
                    System.out.println("\nGoodbye\n");
                    System.exit(0);
                }
                else{
					// column rotation and exception handling if is thrown
					try{
                        colRotate(b1, str[0]);
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("\nError: " + e.getMessage());
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("\nError: Invalid column entered");
                    }
                    catch(EmptyStackException e){
                        System.out.println("\nError: The column is empty");
                    }
                }
                command(b1);
            }
			// move last card from 'from' if following rules
            else if(str.length == 2) {
                if(checkToFrom(b1, str[0], str[1].toLowerCase()))
                    move(b1, str[0], str[1].toLowerCase());
                command(b1);
            }
			// move whole deck cards start from specific card if following rules
            else if(str.length == 3){
                if(checkToFrom(b1, str[0], str[2].toLowerCase()))
                    move(b1, str[0], str[1], str[2].toLowerCase());
                command(b1);
            }
            else{
                System.out.println("\nError: Invalid instruction entered.");
                System.out.println("Try enter with the following format:-");
                System.out.println("FROM WHICH COLUMN<space>WHICH CARD" +
									"<space>TO WHICH COLUMN/PILE");
                command(b1);
            }
        }
        catch(NumberFormatException e){
            System.out.println("\nError: Invalid instruction entered.");
            System.out.println("Try enter with the following format:-");
            System.out.println("FROM WHICH COLUMN<space>WHICH CARD" +
							   "<space>TO WHICH COLUMN/PILE");
            command(b1);
        }
		input.close();
    }

	/**
	* Check source and destination
	*
	* Returns true only if source and destination of card movement is valid.
	* Source can only be from column 1 to column 9.
	* Nothing can be move from any pile.
	* Destination should be valid pile or column.
	*
	* @param b1 Game board
	* @param from Source of card movement
	* @param to Destination of card movement
	* @return true if source is from column 1 to column 9 or column is empty
	*/
	// check if to and from is valid command for existing stacks
    public static boolean checkToFrom(Board b1, String from, String to){
        int checkFrom = 1;
		// try catch exception to check if from is integer
        try{
            checkFrom = Integer.parseInt(from.trim());
			// check 'from' is 1 to 9 and 'to' is existing stacks in Map boards
            if ( checkFrom < 1 || checkFrom > 9 ||
			     !(b1.getBoards().containsKey(to.charAt(0)))){
                System.out.println("\nError: Input out of range");
                return false;
            }
			// if moving from empty stacks
            else if( b1.getBoards().get(from.charAt(0)).isEmpty() ) {
                System.out.println("\nError: Column is empty");
            }
            return true;
        }
		// ensure valid command entered and prevent moving from piles
        catch(NumberFormatException e){
            System.out.println("\nError: " + e.getMessage());
            System.out.println("Error: Enter a column number to move from");
            return false;
        }
    }

	/**
	* Check if multiple cards movement follows rules
	*
	* Check if deck of cards starting from the specified card is one
	* points larger than the next card or when destination column is empty.
	*
	* @param b1 Game board
	* @param from Source of card movement
	* @param to Destination of card movement
	* @param searchIndex 1-based position where an specified card is on source stack
	* @return true if deck of cards follows order or destination column is empty
	*/
	// check if the deck of cards is following rules
    public static boolean checkMultMove(Board b1, char from, char to, int searchIndex){
        int current = b1.getBoards().get(from).size() - searchIndex;	// current index of card in stack
        for(int i = current; i < b1.getBoards().get(from).size() - 1; i++){
			// if next card is not one point bigger
            if(getPoints(b1.getBoards().get(from).get(i)) -
				getPoints(b1.getBoards().get(from).get(i + 1)) != 1){
                System.out.println(b1.getBoards().get(from).get(i) + " and " +
				b1.getBoards().get(from).get(i + 1) + "not following order!");
                return false;
            }
        }
		// true if 'to' stack is empty
		if(b1.getBoards().get(to).size() == 0){
            return true;
        }
		// check 'to' last card is one points larger than the card moving in
        else if(getPoints(b1.getBoards().get(to).peek()) -
				getPoints(b1.getBoards().get(from).get(current)) == 1){
            return true;
        }
        else{
            System.out.println("\nError: Invalid destination!");
            return false;
        }
    }

	/**
	* Move last card from source stack to destination stack
	*
	* Method moves the last card from source column to destination if the points
	* of card is one point smaller and the suit matches the pile's suit when
	* destination is a pile. When destination is a column.
	*
	* @param b1 Game board
	* @param from Source of card movement
	* @param to Destination of card movement
	*/
	// move last element from 'from' to 'to' without specifying the card
    public static void move(Board b1, String from, String to){
        char toKey = to.charAt(0);
        char fromKey = from.charAt(0);

        if(Character.isLetter(to.charAt(0))){
			// get 'to' last card's points, 0 if is empty
            int prevPoints = 0;
            if(b1.getBoards().get(toKey).isEmpty())
                prevPoints = 0;
            else
                prevPoints = getPoints(b1.getBoards().get(toKey).peek());

			// check if 'to' last card is one point smaller and is suit its match
            if(prevPoints - getPoints(b1.getBoards().get(fromKey).peek()) == -1 &&
                b1.getBoards().get(fromKey).peek().charAt(0) == toKey){
                    try{
                        b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
                    }
                    catch(IllegalArgumentException e) {
                        System.out.println('\n' + e.getMessage() + '\n');
                    }
            }
            else{
                System.out.println("\nError: Invalid destination!");
            }
        }
        else if(checkMultMove(b1, fromKey, toKey, 1)){
            b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
        }
        else{
            System.out.println("\nError: Invalid destination!");
        }
    }

	/**
	* Move deck of cards from source stack to destination stack
	*
	* Method moves the deck of cards starting from the specified card till the
	* end of the stack if the rules is followed. IfWhen destination is pile,
	* deck of cards is reversed to add into pile if they follows rules
	*
	* @param b1 Game board
	* @param from Source of card movement
	* @param whatCard Specific card starting to move from
	* @param to Destination of card movement
	*/
	// move card(s) with specified card to move from
    public static void move(Board b1, String from, String whatCard, String to) {
		whatCard = whatCard.toLowerCase().substring(0,1) +
				   whatCard.toUpperCase().substring(1,2);
        if(whatCard.length() != 2 || !isElementOfCards(b1, whatCard) ||
		   !b1.getBoards().get(from.charAt(0)).contains(whatCard)){
            System.out.println("\nError: Invalid card entered!");
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
                    for(int i = fromSearch; i>0; i--){	// add card into temp with reverse order
                        temp.add(b1.getBoards().get(fromKey).pop());
                    }
					// check if deck of cards is one points larger than previous and same suit
					boolean validPoints = true;
					boolean validSuit = true;
					for(int i=0; i<temp.size()-1; i++){
						if( getPoints(temp.get(i)) + 1 != getPoints(temp.get(i+1)) )
							validPoints = false;
						if(temp.get(i).charAt(0) != temp.get(i+1).charAt(0))
							validSuit = false;
					}
					// push into pile if follows rules
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
                    System.out.println( "\nError: Move is not following rules");
                }

            }
        }
    }

	/**
	* Rotate column
	*
	* Remove last card and insert into the top of the stack
	*
	* @param b1 Game board
	* @param col Column to be rotated
	* @throws IllegalArgumentException Invalid or inpropriate input
	*								   is entered without folloring the rules.
	* @throws EmptyStackException Stack is empty
	*/
	// rotate column
    public static void colRotate(Board b1, String col)
        throws IllegalArgumentException, EmptyStackException{
        char column = col.charAt(0);
        if(Character.isDigit(column)){
			int columnNumber = Integer.parseInt(col);
			if(columnNumber < 1 || columnNumber > 9){
                throw new IllegalArgumentException("\nError: Column number out of range");
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
				throw new IllegalArgumentException("\nError: Pile cannot be rotated");
			else
				throw new IllegalArgumentException("\nError: Invalid column entered");
		}
		else{
			throw new IllegalArgumentException("\nError: Invalid instruction entered");
		}
    }

	/**
	* Check card's validity
	*
	* Check if card is a valid card
	*
	* @param b1 Game board
	* @param card card's face
	* @return true if card is an element of cards in Board class
	*/
	// check card entered is a valid card
	public static boolean isElementOfCards(Board b1, String card) {
		String s = card.toLowerCase().substring(0,1) + card.toUpperCase().substring(1,2);
		System.out.println("s" + s);
        if(b1.getCards().contains(s)) return true;
        else return false;
    }

	/**
	* Get points of the card
	*
	* @param card card's face
	* @return integer points of the card
	*/
	// check the points of card
	public static int getPoints(String card) {
        int points = 0;
        char face = card.toString().toUpperCase().charAt(1);
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

	/**
	* Exit if game is completed
	*
	* Check if every pile is filled with 12 cards. If yes, the game will end.
	*
	* @param b1 Game board
	*/
	// check the points of card
	// check if game is completed
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
		if(gameOver){
			System.out.println("\n\n");
			System.out.println("************************************************************************************************");
			System.out.println("************************************************************************************************\n");
			System.out.println("			 ####   ####  #    #  ####  #####   ####  #####  ####   ## ##");
			System.out.println("			#    # #    # ##   # #    # #    # #    #   #   #    #  ## ##");
			System.out.println("			#      #    # # #  # #      #    # #    #   #    ##     ## ##");
			System.out.println("			#      #    # #  # # #  ### #####  ######   #      ##   ## ##");
			System.out.println("			#    # #    # #   ## #    # #  #   #    #   #   #    #       ");
			System.out.println(" 			 ####   ####  #    #  ####  #   ## #    #   #    ####   ## ##");
			System.out.println("\n************************************************************************************************");
			System.out.println("************************************************************************************************");
			System.out.println("\n\n");
			System.out.println("You have finished the game!");
			System.out.println("Goodbye");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
        Board b1 = new Board();
		command(b1);
	}
}

/**
* The Board class represents a Freecell game board
* @author Wong Man Yi
* @author Lee Ming Xuan
*/
class Board {
	private ArrayList<String> cards = new ArrayList<>();
	private ArrayList<Stack<String>> stacks = new ArrayList<>();
	private Map<Character, Stack<String>> boards = new LinkedHashMap<>();

	/**
	* Constructs a game board and creates a new game board
	*/
	public Board() {
		newBoard();
	}

	/**
	* Clears every stacks and list and creates a new game board
	*/
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

	/**
	* Returns the map of the character as key for stack of all piles and columns
	*
	* @return map where pile suit character as key for corresponding stack for all piles and columns
	*/
	public Map<Character, Stack<String>> getBoards(){
        return boards;
	}

	/**
	* Returns the card list containing 52 cards
	*
	* @return card list containing 52 cards
	*/
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

/**
* The Board class represents a Freecell game board
* @author Wong Man Yi
* @author Lee Ming Xuan
* @param <E> Type of element stored in a list
*/
class OrderedStack<E> extends Stack<E>{
	/**
	* Get points of the card
	*
	* @param card card
	* @return integer points of the card
	*/
	// check card's points
	private int getPoints(E card) {
		int points = 0;
        char face = card.toString().toUpperCase().charAt(1);
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

	/**
	* Pushes an item onto the top of this stack.
	*
	* Pushes an item onto the top of this stack.
	* This has exactly the same effect as addElement(item)
	*
	* @param card card
	* @throws IllegalArgumentException Card is not one point larger than the previous.
	* @return the card argument
	*/
	// overriding push function of java Stack class to check if following rules
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
}
