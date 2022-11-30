import java.util.*;

class ValidateString {
    private Stack<Character> stack;
    private Map<Character, Map<Character, String>> map;
    private final Character start = 'S';

    ValidateString(Table parse_table) {
        this.map = parse_table.map;
        this.stack = new Stack<>();

        stack.push('$');
        stack.push(start);

        // something
    }

    public void checkIfAccepted(String str) {
        str += '$';

        boolean input_has_non_terminal = checkIfStringHasNonTerminal(str);
        if (input_has_non_terminal) {
            System.out.println("Invalid string since it has a non_teminal symbol");
            return;
        }

        char[] input = str.toCharArray();

        int index = 0;

        while (index != input.length) {
            char ch = input[index];

            // Case 1 :-Top of stack is ε
            if (stack.size() != 0 && stack.peek() == 'ε')
                stack.pop();

            // Case 2:- Top of stack is dollar and ch is also dollar ( Accepted case)
            else if (stack.size() != 0 && (stack.peek() == '$' && ch == '$')) {
                System.out.println("Congrats!! . The string has matched as per the grammar by ll(1) parsing ");
                return;
            }

            // Case 3.1 : Both are terminals both dont match
            else if (stack.size() != 0 && !(stack.peek() >= 'A' && stack.peek() <= 'Z') && ch != stack.peek()) {
                System.out.println("Not accepted because of case 3.1 ");
                // System.out.println ( stack.size() + "," + ch + "," + stack.peek() );
                return;
            }

            // Case 3.2 :- Both are terminals and match
            else if (stack.size() != 0 && !(stack.peek() >= 'A' && stack.peek() <= 'Z') && ch == stack.peek()) {
                stack.pop();
                index += 1;
            }

            // Case 4 : top of stack is non_teminal
            else if (stack.peek() >= 'A' && stack.peek() <= 'Z') {
                Map<Character, String> map_of_non_teminal = map.get(stack.peek());

                // Case 4.1 :- Top of stack has ch in its map
                if (map_of_non_teminal.containsKey(ch)) {
                    // do action
                    String matched_string_for_ch = map_of_non_teminal.get(ch);

                    // Step 1: POP the top of stack and replace it with matched_string_for_ch in
                    // reverse
                    stack.pop();
                    for (int i = matched_string_for_ch.length() - 1; i >= 0; --i)
                        stack.push(matched_string_for_ch.charAt(i));

                }

                // Case 4.2 :- Top of stack does not have ch in its map
                else {
                    System.out.println(
                            "Sorry !!! The given input is not accppted by the prser because grammar does not contains "
                                    + ch + "Case 4.2");
                    return;
                }

            }

        }

    }

    public boolean checkIfStringHasNonTerminal(String input) {
        char[] arr = input.toCharArray();
        for (char ch : arr)
            if (ch >= 'A' && ch <= 'Z')
                return true;
        return false;
    }

    public static void main(String[] args) {
    }
}