import java.util.*;

class Q2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("This is Question 2 : ");
        System.out.print("Enter your string : ");

        String input= sc.next();
        Grammar grammar = new Grammar(2);

        First first_of = new First(grammar);
        first_of.computeFirst();

        Follow follow_of = new Follow(first_of);
        follow_of.computeFollow();

        Table parse_table = new Table(follow_of);
        parse_table.computeParseTable();

        ValidateString validate = new ValidateString ( parse_table );
        validate.checkIfAccepted ( input );
        
    }
}
