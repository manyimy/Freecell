package game;

import java.util.*;

// /**
// * The Board class represents a Freecell game board
// * @author Wong Man Yi
// * @author Lee Ming Xuan
// * @param <E> Type of element stored in a list
// * @see Freecell
// * @see #Board
// */
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
