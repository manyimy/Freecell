import java.util.*;

public class Freecell {
    public static void main(String[] args) {
        Board b1 = new Board();
        System.out.println("boards.get(1)                : " + b1.getBoards().get('1'));
        System.out.println("boards.get(1).get(4)         : " + b1.getBoards().get('1').get(4));

        command(b1);

        System.out.println(b1.toString());
    }

    public static void command(Board b1) {
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
            else if(Integer.parseInt(str[0]) >= 1 && Integer.parseInt(str[0]) <= 9){
                colRotate(b1, str[0]);
            }
            else{
                System.out.println("No such instruction");
            }
            command(b1);
        }
        else if(str.length == 2) {
            move(b1, str[0], str[1]);
            command(b1);
        }
        else if(str.length == 3){
            move(b1, str[0], str[1], str[2].toLowerCase());
            command(b1);
        }
        else{
            System.out.println("Invalid instruction entered.");
            System.out.println("Try enter with the following format:-");
            System.out.println("FROM WHICH COLUMN<space>WHICH CARD<space>TO WHICH COLUMN/PILE");
            command(b1);
        }
        System.out.println();
        input.close();
    }

    public static void colRotate(Board b1, String col) {
        b1.getBoards().get(col.charAt(0)).add(0, b1.getBoards().get(col.charAt(0)).remove(b1.getBoards().get(col.charAt(0)).size()-1));
        command(b1);
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
            command(b1);
        }
        else{
            char toKey = to.charAt(0);
            char fromKey = from.charAt(0);
            b1.getBoards().get(toKey).push( b1.getBoards().get(fromKey).pop() );
        }
        command(b1);
    }

    public static void move(Board b1, String from, String whatCard, String to){
        if(!checkToFrom(b1, to, from)){
            command(b1);
        }
        else if(whatCard.length() != 2 || !isElementOfCards(b1, whatCard)){
            System.out.println("Invalid card entered!");
            command(b1);
        }
        else{
            char toKey = to.charAt(0);
            char fromKey = from.charAt(0);
            int fromSearch = b1.getBoards().get(fromKey).search(whatCard);
            if( fromSearch != -1){
                if(fromSearch == 1){
                    move(b1, from, to);
                }
                else{
                    if(checkMultMove(b1, fromKey, toKey, fromSearch-1)){
                        System.out.println("checkMultMove = true");
                        Stack<String> temp = new Stack<>();
                        for(int i = fromSearch; i>0; i--){
                            temp.push(b1.getBoards().get(fromKey).pop());
                        }
                        for(int i = fromSearch; i>0; i--){
                            b1.getBoards().get(toKey).push(temp.pop());
                        }
                    }
                    else{
                        System.out.println("checkMultMove = false");    //change errror message
                    }
                    
                }
                
            }
            command(b1);
        }
    }

    public static boolean checkMultMove(Board b1, char from, char to, int searchIndex){
        int current = b1.getBoards().get(from).size() - searchIndex - 1;
        for(int i=current; i<b1.getBoards().get(from).size() - 1; i++){
            if(getPoints(b1.getBoards().get(from).get(i)) - getPoints(b1.getBoards().get(from).get(i + 1)) != 1){
                return false;
            }
        }
        if(b1.getBoards().get(to).size() == 0){
            return true;
        }
        else if(getPoints(b1.getBoards().get(from).get(current)) == getPoints(b1.getBoards().get(to).peek()) - 1){
            return true;
        }
        else{
            System.out.println("Invalid destination!");
            return false;
        }
    }

    public static boolean isElementOfCards(Board b1, String c) {
        System.out.println(c);
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
}

class Board {
    private ArrayList<String> cards = new ArrayList<>(52);
    private Map<Character, Stack<String>> boards = new LinkedHashMap<>();
    private ArrayList<Stack<String>> stacks = new ArrayList<Stack<String>>();

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
            if(i == 5) {
                pre = "Column\t";
            }
            else
            i++;
            p = p + pre + key + " : " + getBoards().get(key) + '\n';
        }
        return p;
    }
}