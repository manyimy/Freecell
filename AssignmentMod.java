import java.util.*;

public class AssignmentMod{
    //use enum to make shape public,static,final,group of constant
    public enum CARD_SHAPE {
        HEARTS,
        CLUBS,
        DIAMONDS,
        SPADES
    }


    public static class Card {
        private int point;
        private CARD_SHAPE shape ;

        /**
         * Initialize card object base on string
         * c8 - CLUBS 8
         * cx - CLUBS 10
         */
        public Card(String card){
            char type = card.toLowerCase().charAt(0);
            char face = card.toLowerCase().charAt(1);

            switch  (type){
                case 'c': this.shape=CARD_SHAPE.CLUBS; break;
                case 'd': this.shape=CARD_SHAPE.DIAMONDS; break;
                case 'h': this.shape=CARD_SHAPE.HEARTS; break;
                case 's': this.shape=CARD_SHAPE.SPADES; break;

            }


        if (face == 'x') this.point = 10;
        else if (face == 'j') this.point = 11;
        else if (face == 'q') this.point = 12;
        else if (face =='k') this.point = 13;
        else this.point=Integer.valueOf(String.valueOf(face));
        }

        public Card(int point, CARD_SHAPE shape){
            this.point = point;
            this.shape =shape;
        }
    //class that create our own dont have default override ToString() and equals()
        @Override
        public String toString() {
            String result = "" ;
            switch(shape){
                case CLUBS: result="c"; break;
                case DIAMONDS: result = "d"; break;
                case HEARTS: result = "h"; break;
                case SPADES: result = "s"; break;

            }
            if (point < 10){
                result = result + point;
            }else{
                switch(point){
                    case 10 : result=result+"X"; break;
                    case 11 : result=result+"J"; break;
                    case 12 : result=result+"Q"; break;
                    case 13 : result=result+"K"; break;
                    default:  result = "ERROR";
                }
            }
            return result;
        }
        //for comparing the equality of 2 obj
        @Override
        public boolean equals(Object obj) {
            Card cardToCompare = (Card) obj;
            return cardToCompare.point == this.point &&
                cardToCompare.shape == this.shape;
        }
    }
//deck respoensible for a set of 52 cards
    public static class Deck {
        private List<Card> cards;

        public Card popCard(){
            Card cardTobPopped = cards.get(cards.size()-1);
            cards = cards.subList(0, cards.size()-1);
            return cardTobPopped;
        }

        public List<Card> drawCards(int numberOfCards){
            List<Card> cardTobDraw = new ArrayList<Card>();
            for ( int i = 0 ; i < numberOfCards ; i ++ ){
                cardTobDraw.add(this.popCard());
            }
            return cardTobDraw;
        }


        public Deck(){
            cards = new ArrayList<Card>();
            for (CARD_SHAPE shape : CARD_SHAPE.values()){
                for (int i = 1 ; i <= 13; i ++ ){
                    cards.add(new Card(i, shape));
                }
            }
        }

        public void shuffle(){
            Collections.shuffle(cards);
        }
        //accesor and mutator for cards in deck
        public List<Card> getCards() {
            return cards;
        }

        public void setCards(List<Card> cards) {
            this.cards = cards;
        }
    }

// Pile class
// -have push Card method
    public static class Pile {
        private List<Card> stackCards;

        public Pile(){
            this.stackCards = new ArrayList<Card>();
        }

        public boolean checkOrder(List<Card> cards){
            return true;
        }

        public void pushCardStack(List<Card> card){
            if(checkOrder(card))
            this.stackCards.addAll(card);
        }
    }

//Column
    public static class Column{
        private List<Card> columnCards;

        public Column(){
            this.columnCards = new ArrayList<Card>();
        }

        public void setCards(List<Card> cards) {
            this.columnCards = cards;
        }

        public boolean isCardInStack(Card card) {
            return columnCards.contains(card);
        }

        public List<Card> popCardStack(Card card) {
            int index = columnCards.indexOf(card);
            List<Card> poplist = columnCards.subList(index, columnCards.size());
            this.columnCards = columnCards.subList(0, index);
            return poplist;
        }

        public void pushCardStack(List<Card> card) {
            this.columnCards.addAll(card);
        }

        public void add(int index, Card card) {
            this.columnCards.add(index, card);
        }

        public Card remove(int index) {
            return this.columnCards.remove(index);

        }

        public int size() {
            return this.columnCards.size();
        }

    }
//Board
// initial board
//moving
//check command valid
    public static class Board {
        private Stack<Pile> piles = new Stack<Pile>(4);
        private Stack<Column> columns = new Stack<Column>(9);

        static final int CLUBS = 0;
        static final int HEARTS = 1;
        static final int DIAMONDS = 2;
        static final int SPADES = 3;

        public Board() {
            super();
            this.initializeBoard();
        }

        public void initializeBoard() {

            // piles are empty
            for (int i = 0; i < 4; i++) {
                piles.push(new Pile());
            }

            // initialize a deck
            Deck deck = new Deck();
            deck.shuffle();

            // column 1 to 8 are place with random card
            for (int i = 0; i < 9; i++) {
                columns.push(new Column());

                if (i == 8) {
                    break;
                }

                // give 7 cards to first 4 column
                if (i < 4) {
                    columns.get(i).setCards(deck.drawCards(7));
                } else {
                    // give 6 cards to remaining column
                    columns.get(i).setCards(deck.drawCards(6));
                }
            }
        }

        public void move(String command) {

            String cmd = command.trim(); // delete empty space pf input at front & behind
            String[] commands = cmd.split(" ");

            String source = commands[0];
            String cardToMatch = commands[1];
            String destination = commands[2];
            Column sourceColumn = this.columns.get(Integer.valueOf(source) - 1);
            Card card = new Card(cardToMatch);
            boolean validMove = sourceColumn.isCardInStack(card);

            boolean isDestinationAPile = destination.equalsIgnoreCase("C") || destination.equalsIgnoreCase("D")
                    || destination.equalsIgnoreCase("H") || destination.equalsIgnoreCase("S");

            boolean isDestinationMatchPile = ((cardToMatch.startsWith("c")) && destination.equalsIgnoreCase("c"))
                    || ((cardToMatch.startsWith("d")) && destination.equalsIgnoreCase("d"))
                    || ((cardToMatch.startsWith("h")) && destination.equalsIgnoreCase("h"))
                    || ((cardToMatch.startsWith("s")) && destination.equalsIgnoreCase("s"));

            if (validMove) {
                List<Card> cards = sourceColumn.popCardStack(card);

                if (isDestinationAPile && isDestinationMatchPile) {
                    Pile pile = null;
                    switch (destination) {
                    case "d":
                        pile = this.piles.get(DIAMONDS);
                        break;
                    case "c":
                        pile = this.piles.get(CLUBS);
                        break;
                    case "h":
                        pile = this.piles.get(HEARTS);
                        break;
                    case "s":
                        pile = this.piles.get(SPADES);
                        break;
                    default:
                        System.out.println("Pile does not exits !");
                    }
                    pile.pushCardStack(cards);
                } else if (!isDestinationAPile && !isDestinationMatchPile) {
                    // switch case for destination is 1- 9
                    switch (destination) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        Column destinationColumn = this.columns.get(Integer.valueOf(destination) - 1);
                        destinationColumn.pushCardStack(cards);
                        break;
                    default:
                        System.out.println("Column does not exist! ");
                        sourceColumn.pushCardStack(cards);
                    }
                } else if (isDestinationAPile && !isDestinationMatchPile) {
                    System.out.println("card doesnt match pile");
                    sourceColumn.pushCardStack(cards);
                }
            } else {
                System.out.println("Invalid card!");
            }
        }

        public boolean validateCommand(String command) {
            String cmd = command.trim();
            String[] commands = cmd.split(" ");

            // a command should have 3 input items
            if (commands.length != 3) {
                return false;
            }

            // second command should always be a card
            if (commands[1].length() != 2) {
                return false;
            }
            // cannot move from piles
            if (commands[0].equals("c") || commands[0].equals("d") || commands[0].equals("h")
                || commands[0].equals("s")) {
                System.out.println("Cards in Pile cannot move!");
                return false;
            }

            return true;
        }

        public String printCardArray(List<Card> cards) {

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cards.size(); i++) {
                if (i == cards.size() - 1) {
                    builder.append(cards.get(i));
                } else {
                    builder.append(cards.get(i)).append(", ");
                }
            }
            return builder.toString();
        }

        public String draw() {

            StringBuilder board = new StringBuilder();

            board.append("Pile c : [").append(printCardArray(piles.get(CLUBS).stackCards)).append("]").append("\n");
            board.append("Pile d : [").append(printCardArray(piles.get(DIAMONDS).stackCards)).append("]").append("\n");
            board.append("Pile h : [").append(printCardArray(piles.get(HEARTS).stackCards)).append("]").append("\n");
            board.append("Pile s : [").append(printCardArray(piles.get(SPADES).stackCards)).append("]").append("\n");

            for (int columnIndex = 0; columnIndex < this.columns.size(); columnIndex++) {
                board.append("Column ")
                .append(columnIndex + 1)
                .append(" : [")
                .append(printCardArray(this.columns.get(columnIndex).columnCards)) //get cards from column class
                .append("]")
                .append("\n");
            }

            return board.toString();
        }

        public void autoSolve(){

        }

        public void columnRotation(String command){
            System.out.println("column rotate-ing..");
            Column rotateColumn = this.columns.get(Integer.valueOf(command)-1);
            rotateColumn.add(0, rotateColumn.remove(rotateColumn.size()-1));
        }
    }

//Commander
//Ask user input
    public static class Commander{
        public String ask(){
            Scanner scanner = null;
            String input ="";
                System.out.print("Command > ");
                scanner = new Scanner(System.in);
                input = scanner.nextLine();
                return input;
           //use try finally solve input close
        }

    }

//Game Manager
//start game
    public static class GameManager{
        private Commander commander;
        private Board board ;
        public GameManager(Commander commander, Board board){
            this.commander = commander;
            this.board = board;
        }

        public void startGame(){
            String ques  = "";
            do {
                System.out.println(board.draw());
                ques = commander.ask();

                if(ques.equalsIgnoreCase("r")){
                    // reinitialize board
                    board = new Board();
                }
                if (ques.equalsIgnoreCase("s")){
                    board.autoSolve();
                }

                boolean isCommandValid =  board.validateCommand(ques);

                if( ! isCommandValid ){
                    System.out.println("Please enter a valid Command !!");
                }
                else{
                    board.move(ques);
                }

                switch (ques) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9": board.columnRotation(ques);break;
                }

            }while ( !ques.equalsIgnoreCase("x"));
        }
    }

//main
    public static void main(String[] args) {
       Board board = new Board();
       Commander commander = new Commander();
       GameManager manager = new GameManager(commander, board) ;
       manager.startGame();
    }

}
