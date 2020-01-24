//Todo: multiplemove checked points, 
//      try moving multiple cards from 1 col to another,
//      line: 102


import java.util.*;

public class Freecell {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Board b1 = new Board();
    System.out.println("boards.get(1)                : " + b1.getBoards().get('1'));
    System.out.println("boards.get(1).get(4)         : " + b1.getBoards().get('1').get(4));

    //while(true){
    choice(b1);
    //}

    System.out.println(b1.toString());
  }

  public static void choice(Board b1) {
    Scanner input = new Scanner(System.in);
    System.out.println();
    System.out.println(b1.toString());  //print current board
    System.out.print("Command > ");
    String strInput = input.nextLine();
    // Separate the string input according to the spaces
    String str[] = strInput.split(" ", 0);
    if(str.length == 1) {
      if(str[0].equals("r")) {
        b1.newBoard();
      }
      else if(str[0].equals("x")){
        System.exit(0);
      }
    }
    else if(str.length == 2) {
      move(b1, str[0], str[1]);
    }
    else{
      move(b1, str[0], str[1], str[2].toLowerCase());
    }
    System.out.println();
  }

  public static boolean checkToFrom(Board b1, String to, String from){
    boolean isNum = true;
    int checkFrom = 1;
    try{
      checkFrom = Integer.parseInt(from);
    }
    catch(NumberFormatException e){
      isNum = false;
    }
    if(!isNum){
      System.out.println('\n' + "Enter a column number to move from" + '\n');
      return false;
    }
    else if (from.length() > 1 || to.length() > 1){
      System.out.println('\n' + "Enter only 1 character to indicates the pile or column" + '\n');
      return false;
    }
    else if (checkFrom < 1 || checkFrom > 9 || !(b1.getBoards().containsKey(to.charAt(0)))){
      System.out.println("Input out of range");
      return false;
    }
    return true;
  }

  public static void move(Board b1, String from, String to){
    if(!checkToFrom(b1, to, from)){
      choice(b1);
    }
    else{
      char toKey = to.charAt(0);
      char fromKey = from.charAt(0);
      b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
    }
    choice(b1);
  }

  public static void move(Board b1, String from, String whatCard, String to){
    if(!checkToFrom(b1, to, from)){
      choice(b1);
    }
    else if(whatCard.length() != 2 || !isElementOfCards(b1, whatCard)){
      System.out.println("Invalid card entered!");
    }
    else{
      char toKey = to.charAt(0);
      char fromKey = from.charAt(0);
      int fromlength = b1.getBoards().get(fromKey).size();
      int fromSearch = b1.getBoards().get(fromKey).search(whatCard);
      if( fromSearch != -1){
        System.out.println(whatCard + " is found on index " + fromSearch);
        checkMultMove(b1, fromKey, whatCard, toKey, fromSearch-1);
      }
    }
  }

  public static void checkMultMove(Board b1, char from, String card, char to, int searchIndex){
    int cardIndex = b1.getBoards().get(from).size() - searchIndex - 1;
    if(b1.getPoints(b1.getBoards().get(from).get(cardIndex)) - 1 == b1.getPoints(b1.getBoards().get(from).get(cardIndex + 1))){
      System.out.println(b1.getBoards().get(from).get(cardIndex) + " is 1 point more than " + b1.getBoards().get(from).get(cardIndex+1));
    }
    else{
     System.out.println(b1.getBoards().get(from).get(cardIndex) + " is not 1 point more than " + b1.getBoards().get(from).get(cardIndex+1)); 
    }
  }

  public static boolean isElementOfCards(Board b1, String c) {
    boolean isCards = false;
    System.out.println(c);
    if(b1.getCards().contains(c)){
      isCards = true;
      System.out.println("cards contains " + c);
    }
    return isCards;
  }
}

class Board {
  private ArrayList<String> cards = new ArrayList<>(52);
  private Map<Character, Stack<String>> boards = new LinkedHashMap<>();
  private ArrayList<Stack<String>> stacks = new ArrayList<Stack<String>>();
  private int points = 0;

  public Board(){
    newBoard();
    toString();
  }

  public ArrayList<String> getCards() {
    return cards;
  }

  public void newBoard() {
    boards.clear();
    for(Stack<String> s : stacks)
      s.clear();

    if(boards.isEmpty()){
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

      for(int i = 0; i<13; i++){
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
  }

  public Map<Character, Stack<String>> getBoards(){
    return boards;
  }

  public ArrayList<Stack<String>> getStacks(){
    return stacks;
  }

  public String toString() {
    String p = new String();
    String pre = "Pile\t";
    int i = 1;
    for(Character key: getBoards().keySet()) {
      if(i == 4) {
        pre = "Column\t";
      }
      else
        i++;
      p = p + pre + key + " : " + getBoards().get(key) + '\n';
    }
    return p;
  }

  public int getPoints(String card) {
    char face = card.charAt(1);
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

// class Card {
//   public String name;
//   public char face;
//   public char suit;
//   public int point;
//   public Card() {}
//   public Card(String name) {
//     this.name = name;
//     setSuit();
//     setFace();
//     setPoint();
//   }
//   public String getName() { return name; }
//   public char getFace() { return face; }
//   public char getSuit() { return suit; }
//   public int getPoint() { return point; }
//   public void setSuit() {
//     suit = name.charAt(0);
//   }
//   public void setFace() {
//     face = name.charAt(1);
//   }
//   public void setPoint() {
//     switch (face) {
//       case 'A': point = 1; break;
//       case 'X': point = 10; break;
//       case 'J': point = 11; break;
//       case 'Q': point = 12; break;
//       case 'K': point = 13; break;
//       default: point = Character.getNumericValue(face);break;
//     }
//   }
// }
