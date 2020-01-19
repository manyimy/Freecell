import java.util.*;

public class Freecell {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Board b1 = new Board();
    System.out.println(b1.toString());
    System.out.println();
    System.out.println("boards.get(1)                : " + b1.getBoards().get('1'));
    System.out.println("boards.get(1).get(4)         : " + b1.getBoards().get('1').get(4));
    System.out.println("boards.get(1).get(4).getPoint: " + b1.getBoards().get('1').get(4).getPoint());

    choice(b1);
    System.out.println(b1.toString());
  }

  public static void choice(Board b1) {
    Scanner input = new Scanner(System.in);
    System.out.print("Command > ");
    String strInput = input.nextLine();
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
      move(b1, str[0], str[1], str[2]);
    }
    System.out.println();
    System.out.println(b1.toString());
  }

  public static void move(Board b1, String f, String t){
    boolean numeric = true;
    int from;
    char to;
    try{
      from = Integer.parseInt(f);
      to = t.charAt(0);
      System.out.println("From: " + from);
      System.out.println("To: " + to);
    }
    catch(NumberFormatException e){
       numeric = false;
    }
    if(!numeric){
      System.out.println("Invalid data entered!");
      System.out.println(b1.toString());
      choice(b1);
    }
  }

  public static void move(Board b1, String f, String whatCard, String t){
    boolean numeric = true;
    int from;
    char to;
    try{
      from = Integer.parseInt(f);
      to = t.charAt(0);
      System.out.println("From: " + from);
      System.out.println("To: " + to);
    }
    catch(NumberFormatException e){
       numeric = false;
    }
    if(!numeric){
      System.out.println("Invalid data entered!");
      System.out.println(b1.toString());
      choice(b1);
    }
  }
}

class Board {
  private ArrayList<Card> cards = new ArrayList<>(52);
  private Map<Character, ArrayList<Card>> boards = new LinkedHashMap<>();

  private ArrayList<Card> pileC = new ArrayList<>();
  private ArrayList<Card> pileD = new ArrayList<>();
  private ArrayList<Card> pileH = new ArrayList<>();
  private ArrayList<Card> pileS = new ArrayList<>();

  private ArrayList<Card> column1 = new ArrayList<>();
  private ArrayList<Card> column2 = new ArrayList<>();
  private ArrayList<Card> column3 = new ArrayList<>();
  private ArrayList<Card> column4 = new ArrayList<>();
  private ArrayList<Card> column5 = new ArrayList<>();
  private ArrayList<Card> column6 = new ArrayList<>();
  private ArrayList<Card> column7 = new ArrayList<>();
  private ArrayList<Card> column8 = new ArrayList<>();
  private ArrayList<Card> column9 = new ArrayList<>();

  public Board(){
    // suit: spades(s), hearts (H), clubs (c), diamonds (d)
    String[] suit = {"c","d","h","s"};
    // face: number on the card
    String[] face = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
    for(int i=0; i<4; ++i){
      for(int j=0; j<13; ++j){
        String s = suit[i] + face[j];
        Card c = new Card(s);
        cards.add(c);
      }
    }

    Collections.shuffle(cards); //shuffle the order of cards
    for(int i=0; i<7; ++i)
      column1.add(cards.get(i));
    for(int i=7; i<14; ++i)
      column2.add(cards.get(i));
    for(int i=14; i<21; ++i)
      column3.add(cards.get(i));
    for(int i=21; i<28; ++i)
      column4.add(cards.get(i));
    for(int i=28; i<34; ++i)
      column5.add(cards.get(i));
    for(int i=34; i<40; ++i)
      column6.add(cards.get(i));
    for(int i=40; i<46; ++i)
      column7.add(cards.get(i));
    for(int i=46; i<52; ++i)
      column8.add(cards.get(i));

    boards.put('c', pileC);
    boards.put('d', pileD);
    boards.put('h', pileH);
    boards.put('s', pileS);
    boards.put('1', column1);
    boards.put('2', column2);
    boards.put('3', column3);
    boards.put('4', column4);
    boards.put('5', column5);
    boards.put('6', column6);
    boards.put('7', column7);
    boards.put('8', column8);
    boards.put('9', column9);
    toString();
  }

  public void newBoard() {
    boards.clear();
    pileC.clear();
    pileD.clear();
    pileH.clear();
    pileS.clear();
    column1.clear();
    column2.clear();
    column3.clear();
    column4.clear();
    column5.clear();
    column6.clear();
    column7.clear();
    column8.clear();
    column9.clear();
    if(boards.isEmpty()){
      String[] suit = {"c","d","h","s"};
      // face: number on the card
      String[] face = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
      for(int i=0; i<4; ++i){
        for(int j=0; j<13; ++j){
          String s = suit[i] + face[j];
          Card c = new Card(s);
          cards.add(c);
        }
      }

      Collections.shuffle(cards); //shuffle the order of cards
      for(int i=0; i<7; ++i)
        column1.add(cards.get(i));
      for(int i=7; i<14; ++i)
        column2.add(cards.get(i));
      for(int i=14; i<21; ++i)
        column3.add(cards.get(i));
      for(int i=21; i<28; ++i)
        column4.add(cards.get(i));
      for(int i=28; i<34; ++i)
        column5.add(cards.get(i));
      for(int i=34; i<40; ++i)
        column6.add(cards.get(i));
      for(int i=40; i<46; ++i)
        column7.add(cards.get(i));
      for(int i=46; i<52; ++i)
        column8.add(cards.get(i));

      boards.put('c', pileC);
      boards.put('d', pileD);
      boards.put('h', pileH);
      boards.put('s', pileS);
      boards.put('1', column1);
      boards.put('2', column2);
      boards.put('3', column3);
      boards.put('4', column4);
      boards.put('5', column5);
      boards.put('6', column6);
      boards.put('7', column7);
      boards.put('8', column8);
      boards.put('9', column9);
    }
  }

  public Map<Character, ArrayList<Card>> getBoards(){
    return boards;
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
}

class Card {
  public String name;
  public char face;
  public char suit;
  public int point;
  public Card() {}
  public Card(String name) {
    this.name = name;
    setSuit();
    setFace();
    setPoint();
  }
  public String getName() { return name; }
  public char getFace() { return face; }
  public char getSuit() { return suit; }
  public int getPoint() { return point; }
  public void setSuit() {
    suit = name.charAt(0);
  }
  public void setFace() {
    face = name.charAt(1);
  }
  public void setPoint() {
    switch (face) {
      case 'A': point = 1; break;
      case 'X': point = 10; break;
      case 'J': point = 11; break;
      case 'Q': point = 12; break;
      case 'K': point = 13; break;
      default: point = Character.getNumericValue(face);break;
    }
  }
  // public String toString(){
  //   return "Name: " + name + ", Suit: " + suit + ", Face: " + face + ", Point: " + point;
  // }
  public String toString(){
    return name;
  }
}
