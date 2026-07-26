import java.util.HashMap;
import java.util.Map;

class Demo {
    public static void main (String A[]) {
        String sentence = "the quick brown fox jumps over the lazy dog the fox";

        String[] words = sentence.split(" ");

        Map<String, Integer> freq = new HashMap<>();
            for (String word : words)
                freq.put(word, freq.getOrDefault(word,0) + 1);
        
        int most = 0;
        String mostWord = null;
        for (String key : freq.keySet()) {
            System.out.println(key + " : " + freq.get(key));
            if (most < freq.get(key)) {
                most = freq.get(key);
                mostWord = key;
            }        
        }

        System.out.println("Most frequest : " + mostWord + " (" + most + " times)");

    }
}