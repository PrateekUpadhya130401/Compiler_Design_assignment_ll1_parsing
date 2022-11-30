import java.util.*;

class Table {
    Set<Character> non_terminals;
    LinkedHashSet<Character> terminals;
    First first_of;
    Follow follow_of;

    Map<Character, Map<Character, String>> map;

    private final Character EPSILON = 'ε';

    Table(Follow follow_of) {
        this.first_of = follow_of.first_of;
        this.follow_of = follow_of;
        this.non_terminals = new HashSet<>();
        this.terminals = new LinkedHashSet<>();
        this.map = new HashMap<>();
    }

    public void computeParseTable() {
        fillNonTerminals();
        fillTerminals();

        for (Character ch : non_terminals) {
            LinkedHashMap<Character, List<String>> production = first_of.production;

            for (int i = 0; i != production.get(ch).size(); ++i) {
                String item = production.get(ch).get(i);
                fillUpParseTableForch(ch, item);
            }
        }

        displayTheParseTable ();
    }

    public void fillNonTerminals() {
        for (Character ch : follow_of.map.keySet())
            non_terminals.add(ch);
        // System.out.println ("My Non Terminals are : " +non_terminals);
    }

    public void fillTerminals() {
        for (Character ch : follow_of.map.keySet()) {
            List<Character> non_terminals_obtained_from_follow_of_ch = follow_of.map.get(ch);
            int size_of_ch_ka_follow = non_terminals_obtained_from_follow_of_ch.size();

            for (int i = 0; i != size_of_ch_ka_follow; ++i) {
                Character item = non_terminals_obtained_from_follow_of_ch.get(i);
                if (!item.equals(EPSILON))
                    terminals.add(item);
            }
        }

        for (Character ch : first_of.map.keySet()) {
            List<Character> non_terminals_obtained_from_first_of_ch = first_of.map.get(ch);
            int size_of_ch_ka_follow = non_terminals_obtained_from_first_of_ch.size();

            for (int i = 0; i != size_of_ch_ka_follow; ++i) {
                Character item = non_terminals_obtained_from_first_of_ch.get(i);
                if (!item.equals(EPSILON))
                    terminals.add(item);
            }
        }

        // System.out.println ("My Terminals are : " +terminals);
    }

    public void fillUpParseTableForch(Character ch, String item) {
        LinkedHashMap<Character, List<String>> production = first_of.production;

        if (!map.containsKey(ch))
            map.put(ch, new HashMap<>());

        Map<Character, String> map_of_matched_terminals = map.get(ch);

        if (item.equals(EPSILON + ""))
            fill_Follow_Of_ch_In_Map(ch);
        else {
            char[] arr = item.toCharArray();

            for (int i = 0; i <= arr.length; ++i) {
                if (i == arr.length) {
                    fill_Follow_Of_ch_In_Map(ch);
                    break;
                }

                char value = arr[i];
                if (value >= 'A' && value <= 'Z') {

                    List<Character> temp = first_of.map.get(value);
                    boolean contains_epsilon = false;

                    for (int k = 0; k != temp.size(); ++k) {
                        Character element = temp.get(k);
                        if (map_of_matched_terminals.containsKey(element)) {
                            System.out.println(
                                    "Cant follow LL(1) grammar since multiple values in same cell of " + element);
                            return;
                        } else if (element.equals(EPSILON))
                            contains_epsilon = true;
                        else
                            map_of_matched_terminals.put(element, item);
                    }

                    if (!contains_epsilon)
                        break;

                }

                else {
                    map_of_matched_terminals.put(value, item);
                    break;
                }

            }

            map.put(ch, map_of_matched_terminals);

        }

        return;

    }

    public void fill_Follow_Of_ch_In_Map(Character ch) {
        List<Character> follow_of_ch = follow_of.map.get(ch);

        if (!map.containsKey(ch))
            map.put(ch, new HashMap<>());

        Map<Character, String> map_of_matched_terminals = map.get(ch);

        for (int i = 0; i != follow_of_ch.size(); ++i)
            map_of_matched_terminals.put(follow_of_ch.get(i), "" + EPSILON);

        map.put(ch, map_of_matched_terminals);
    }

    public void displayTheParseTable ()
    {
        System.out.println("\nMy parseTable is : \n\n");

        for( Character ch : terminals )
            System.out.print ( "\t"+ ch + "\t\t");
        System.out.println ("");

        for( Character ch :non_terminals )
        {
            System.out.print ( ch + "\t");
            Map<Character, String> map_of_match_of_ch = this.map.get( ch );

            for( Character item : terminals )
                if ( !map_of_match_of_ch .containsKey( item ) )
                    System.out.print("NULL\t\t");
                else
                    System.out.print ( ch + "->" +map_of_match_of_ch.get(item) + "\t\t");

            System.out.println ("");
                    
        }

        System.out.println ("\n\n\n");        
    }

    public static void main(String[] args) {}
}