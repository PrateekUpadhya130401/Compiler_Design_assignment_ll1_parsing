import java.util.*;

class First {
    public LinkedHashMap<Character, List<String>> production;

    public Map<Character, List<Character>> map; // map holding the (key, value) pairs of non_terminal with its
                                                // corresponding first states bro
    public final Character EPSILON = 'ε';

    First(Grammar g) {
        this.production = g.production;
        this.map = new HashMap<>();
    }

    public void computeFirst() {
        for (Character ch : production.keySet())
            fill_First_For_ch(ch);
        System.out.println("\n\nMy first set is : " + map);
    }

    public void fill_First_For_ch(char ch) {
        List<String> list = production.get(ch);
        List<Character> elements_of_first_of_ch = new ArrayList<>();

        for (int i = 0; i != list.size(); ++i) {
            String item = list.get(i);
            for (int j = 0; j != item.length(); ++j) {
                if (item.charAt(j) >= 'A' && item.charAt(j) <= 'Z') {
                    fill_First_For_ch(item.charAt(j));
                    List<Character> temp = map.get(item.charAt(j));
                    boolean contains_epsilon = false;

                    for (int k = 0; k != temp.size(); ++k) {
                        Character element = temp.get(k);
                        if (element.equals(EPSILON))
                            contains_epsilon = true;
                        else
                            elements_of_first_of_ch.add(element);
                    }

                    if (!contains_epsilon)
                        break;
                }

                else {
                    elements_of_first_of_ch.add(item.charAt(j));
                    break;
                }

            }
        }

        map.put(ch, elements_of_first_of_ch);
    }

    public static void main(String[] args) {
    }

}