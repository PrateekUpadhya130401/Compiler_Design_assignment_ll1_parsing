import java.util.*;

public class Grammar {
    public LinkedHashMap<Character, List<String>> production;

    Grammar(int question_number) {
        if (question_number == 1) {
            List<String> list1 = new ArrayList<>(List.of("AB", "eDa"));
            List<String> list2 = new ArrayList<>(List.of("ab", "c"));
            List<String> list3 = new ArrayList<>(List.of("dC"));
            List<String> list4 = new ArrayList<>(List.of("ε", "eC"));
            List<String> list5 = new ArrayList<>(List.of("ε", "fD"));
            production = new LinkedHashMap<>();

            production.put('S', list1);
            production.put('A', list2);
            production.put('B', list3);
            production.put('C', list4);
            production.put('D', list5);
        }

        else if (question_number == 2) {
            List<String> list1 = new ArrayList<>(List.of("AaAb", "BbBa"));
            List<String> list2 = new ArrayList<>(List.of("ε"));
            List<String> list3 = new ArrayList<>(List.of("ε"));
            production = new LinkedHashMap<>();

            production.put('S', list1);
            production.put('A', list2);
            production.put('B', list3);
        }
    }

    public static void main(String[] args) {
        // nothing
    }
}
