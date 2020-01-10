import java.util.*;

public class Freecell {
  public static void main(String[] args) {
    Random rand = new Random();
    ArrayList<String> cards = new ArrayList<>(52);
    ArrayList<String> pileC = new ArrayList<>();
    ArrayList<String> pileD = new ArrayList<>();
    ArrayList<String> pileH = new ArrayList<>();
    ArrayList<String> pileS = new ArrayList<>();
    ArrayList<String> column1 = new ArrayList<>();
    ArrayList<String> column2 = new ArrayList<>();
    ArrayList<String> column3 = new ArrayList<>();
    ArrayList<String> column4 = new ArrayList<>();
    ArrayList<String> column5 = new ArrayList<>();
    ArrayList<String> column6 = new ArrayList<>();
    ArrayList<String> column7 = new ArrayList<>();
    ArrayList<String> column8 = new ArrayList<>();
    ArrayList<String> column9 = new ArrayList<>();

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

    System.out.println(column1);
    System.out.println(column2);
    System.out.println(column3);
    System.out.println(column4);
    System.out.println(column5);
    System.out.println(column6);
    System.out.println(column7);
    System.out.println(column8);
    System.out.println(column9);

    Card c1 = new Card(cards.get(0));
    System.out.println(c1.toString());

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
  public String toString(){
    return "Name: " + name + ", Suit: " + suit + ", Face: " + face + ", Point: " + point;
  }
}
