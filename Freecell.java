import java.util.*;

public class Freecell {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Board b1 = new Board();
    System.out.println(b1.toString());

    System.out.println();
    for(Character key: b1.getBoards().keySet())
      System.out.println(key + " : " + b1.getBoards().get(key));
    // ArrayList<Card> cards = new ArrayList<>(52);
    //
    // ArrayList<Card> pileC = new ArrayList<>();
    // ArrayList<Card> pileD = new ArrayList<>();
    // ArrayList<Card> pileH = new ArrayList<>();
    // ArrayList<Card> pileS = new ArrayList<>();
    //
    // ArrayList<Card> column1 = new ArrayList<>();
    // ArrayList<Card> column2 = new ArrayList<>();
    // ArrayList<Card> column3 = new ArrayList<>();
    // ArrayList<Card> column4 = new ArrayList<>();
    // ArrayList<Card> column5 = new ArrayList<>();
    // ArrayList<Card> column6 = new ArrayList<>();
    // ArrayList<Card> column7 = new ArrayList<>();
    // ArrayList<Card> column8 = new ArrayList<>();
    // ArrayList<Card> column9 = new ArrayList<>();



    /*for(int i=0; i<4; ++i){
      for(int j=0; j<13; ++j){
        String s = suit[i] + face[j];
        cards.add(s);
      }
    }*/

    // System.out.print("Pile C: ");
    // System.out.println(pC);
    // System.out.print("Pile D: ");
    // System.out.println(pD);
    // System.out.print("Pile H: ");
    // System.out.println(pH);
    // System.out.print("Pile S: ");
    // System.out.println(pS);
    // System.out.print("Column 1: ");
    // System.out.println(c1);
    // System.out.print("Column 2: ");
    // System.out.println(c2);
    // System.out.print("Column 3: ");
    // System.out.println(c3);
    // System.out.print("Column 4: ");
    // System.out.println(c4);
    // System.out.print("Column 5: ");
    // System.out.println(c5);
    // System.out.print("Column 6: ");
    // System.out.println(c6);
    // System.out.print("Column 7: ");
    // System.out.println(c7);
    // System.out.print("Column 8: ");
    // System.out.println(c8);
    // System.out.print("Column 9: ");
    // System.out.println(c9);



    // showAll(pileC, pileD, pileH, pileS, column1, column2, column3, column4,
    //         column5, column6, column7, column8, column9);

    // System.out.print("Pile C: ");
    // System.out.println(pileC);
    // System.out.print("Pile D: ");
    // System.out.println(pileD);
    // System.out.print("Pile H: ");
    // System.out.println(pileH);
    // System.out.print("Pile S: ");
    // System.out.println(pileS);
    // System.out.print("Column 1: ");
    // System.out.println(column1);
    // System.out.print("Column 2: ");
    // System.out.println(column2);
    // System.out.print("Column 3: ");
    // System.out.println(column3);
    // System.out.print("Column 4: ");
    // System.out.println(column4);
    // System.out.print("Column 5: ");
    // System.out.println(column5);
    // System.out.print("Column 6: ");
    // System.out.println(column6);
    // System.out.print("Column 7: ");
    // System.out.println(column7);
    // System.out.print("Column 8: ");
    // System.out.println(column8);
    // System.out.print("Column 9: ");
    // System.out.println(column9);

    // System.out.print("Enter (from ? to ?): ");
    // char in1 = input.next().charAt(0);
    // char in2 = input.next().charAt(0);
    // if(in1 == '1'){
    //   if(in2 == 'c'){
    //     move(column1, pileC);
    //   }
    // }
    // showAll(pileC, pileD, pileH, pileS, column1, column2, column3, column4,
    //         column5, column6, column7, column8, column9);
  }

  // public static <E> void move(ArrayList<Card> list1, ArrayList<Card> list2) {
  //   list2.add(list1.get(list1.size()-1));
  //   list1.remove(list1.size()-1);
  // }

  // public static void showAll(ArrayList<Card> pC, ArrayList<Card> pD, ArrayList<Card> pH, ArrayList<Card> pS, ArrayList<Card> c1, ArrayList<Card> c2, ArrayList<Card> c3, ArrayList<Card> c4, ArrayList<Card> c5, ArrayList<Card> c6, ArrayList<Card> c7, ArrayList<Card> c8, ArrayList<Card> c9) {
  //
  // }

  // public static void choice() {
  //    int source = input.nextInt();
  //   if(input.hasNext()){
  //     if(input.hasNext()) {
  //       String select = input.next().substring(0,2);  //get two characters
  //     }
  //     else{
  //       char des = input.nextInt();
  //     }
  //   }
  // }
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

  public Map<Character, ArrayList<Card>> getBoards(){
    return boards;
  }

  public String toString() {
    return "Pile C: " + Arrays.toString(pileC.toArray()) + "\nPile D: " + Arrays.toString(pileD.toArray()) +
           "\nPile H: " + Arrays.toString(pileH.toArray()) + "\nPile S: " + Arrays.toString(pileS.toArray()) +
           "\nColumn 1: " + Arrays.toString(column1.toArray()) + "\nColumn 2: " + Arrays.toString(column2.toArray()) +
           "\nColumn 3: " + Arrays.toString(column3.toArray()) + "\nColumn 4: " + Arrays.toString(column4.toArray()) +
           "\nColumn 5: " + Arrays.toString(column5.toArray()) + "\nColumn 6: " + Arrays.toString(column6.toArray()) +
           "\nColumn 7: " + Arrays.toString(column7.toArray()) + "\nColumn 8: " + Arrays.toString(column8.toArray()) +
           "\nColumn 9: " + Arrays.toString(column9.toArray());
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
