package game;

import java.util.*;

// /**
// * The Board class represents a Freecell game board
// * @author Wong Man Yi
// * @author Lee Ming Xuan
// * @see Freecell
// * @see OrderdStack
// */
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
