import java.util.*;

class Follow {
    public First first_of;
    public Map<Character, List<Character>> first_of_ka_map;
    public Map<Character, List<Character>> map;

    public final Character start = 'S';
    public final String EPSILON = "ε";

    Follow(First first_of) {
        this.first_of = first_of;
        this.first_of_ka_map = first_of.map;

        this.map = new HashMap<>();
    }

    public void computeFollow() {
        map.put(start, new ArrayList<>(List.of('$')));
        Set<Character> non_terminals = first_of.production.keySet();

        for (Character ch : non_terminals) {
            List<String> list = first_of.production.get(ch);
            for (int i = 0; i != list.size(); ++i) {
                String item = list.get(i);
                if (item.equals(EPSILON))
                    continue;

                fillFollowForString(ch, item);
            }
        }

        System.out.println("\n\nMy follow set is : " + map);
    }

    public void fillFollowForString(Character root, String str) {
        int i = 0;
        char[] arr = str.toCharArray();

        while (i != arr.length) {
            char ch = arr[i];
            if (ch >= 'A' && ch <= 'Z') {
                if (!map.containsKey(ch))
                    map.put(ch, new ArrayList<Character>());
                if (!map.containsKey(root))
                    map.put(root, new ArrayList<>());
                List<Character> follow_of_root = new ArrayList<>(map.get(root));
                List<Character> follow_of_current = map.get(ch);

                int j = i + 1;
                if (j == arr.length) {
                    // Adding follow of root to the current non_terminal ch . Basically moved
                    // cyclically
                    if (ch == root)
                        break;
                    for (int k = 0; k != follow_of_root.size(); ++k) {
                        Character element = follow_of_root.get(k);
                        if (!follow_of_current.contains(element))
                            follow_of_current.add(element);
                    }

                }
                // S -> {AaAb, BbBa}
                // A -> {ε}
                // B-> {ε}
                else {
                    if (arr[j] >= 'A' && arr[j] <= 'Z')
                        // Recursivize this
                        appendFirstOfNextToFollowOfCurrent(root, str, ch, j);
                    else
                        follow_of_current.add(arr[j]);

                }
            }

            i += 1;

        }

    }

    public void appendFirstOfNextToFollowOfCurrent(Character root, String str, Character char_to_find_follow,
            int index) {
        Character key = char_to_find_follow;
        List<Character> follow_of_root = new ArrayList<>(map.get(root));
        List<Character> follow_of_key = map.get(key);

        char curr = str.charAt(index);

        if (index == str.length()) {
            for (int i = 0; i != follow_of_root.size(); ++i)
                if (!follow_of_key.contains(follow_of_root.get(i)))
                    follow_of_key.add(follow_of_root.get(i));
            return;
        }

        else if (curr >= 'A' && curr <= 'Z') {
            boolean contains_epsilon = false;
            List<Character> first_elements_of_curr = first_of_ka_map.get(curr);

            for (int i = 0; i != first_elements_of_curr.size(); ++i) {
                Character item = first_elements_of_curr.get(i);
                if (item.equals(EPSILON))
                    contains_epsilon = true;
                else if (!follow_of_key.contains(item))
                    follow_of_key.add(item);
            }

            if (contains_epsilon)
                appendFirstOfNextToFollowOfCurrent(root, str, key, index + 1);
            return;
        }

        // Encountered a terminal symbol
        else
            follow_of_key.add(curr);

        return;

    }

    public static void main(String[] args) {
    }

}