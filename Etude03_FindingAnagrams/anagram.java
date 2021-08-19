import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class anagram{

    private static boolean debug = false;
    private static ArrayList<String> toSolve = new ArrayList<>();
    private static HashMap<String,ArrayList<String>> dictionary = new HashMap<>();

    public static void main(String args[]){
        if(args.length > 0){
            if(args[0].charAt(0) == 'd' || args[0].charAt(0) =='D') debug = true;
            if(debug) System.out.println("Debug enabled!");
        }
        readData();
        System.out.println("Dictionary:");
        printDictionary();
        System.out.println("\nWords to find anagrams of:");
        printToSolveArray();


    }

    private static void doAnagrams(){
        for(int i = 0; i < toSolve.size();i++){
            String word = toSolve.get(i)
            System.out.println(word+": "+findAnagram(word));
        }
    }

    private static void findAnagram(String s){

    }

    private static void readData(){
        boolean readingDictionaryWords = false;
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            if(debug) System.out.println(line);
            if(debug) System.out.println("reading dictionary words? "+readingDictionaryWords);
            if(line.equals("")) {
                readingDictionaryWords = true;
                continue;
            }
            if(!readingDictionaryWords){
                toSolve.add(line);
            }else if(readingDictionaryWords){
                addToDictionary(line);
            }

        }
    }


    /**
     * This is sorting stored words as the key for the dictionary and words that sort to the same will be at the same index.
     * Sorry for my bad explanation, see below diagram :)
     * LOOP -> [LOOP, POOL, POLO]
     * OPST -> [STOP, POST]
     * */
    private static void addToDictionary(String word){
        //Strings cannot be easily sorted like this, so converting to a char array, sorting that and
        //then creating a new string out of the sorted char array.


        String sortedWord = sortString(word);
        printd(sortedWord);
        if(!dictionary.containsKey(sortedWord)){
            dictionary.put(sortedWord, new ArrayList<String>());
        }
        dictionary.get(sortedWord).add(word);
    }

    private static String sortString(String s){
        char[] cA = s.toCharArray();
        Arrays.sort(cA);
        return new String(cA);
    }

    private static void printDictionary(){
        dictionary.forEach((key,array) -> {
            System.out.print(key+": [");
            for(int i = 0; i < array.size();i++){
                System.out.print(array.get(i)+ " ");
            }
            System.out.println("]");
        });
    }

    private static void printToSolveArray(){
        for(int i = 0; i < toSolve.size(); i++){
            System.out.println(toSolve.get(i));
        }
    }

    private static void printd(String s){
        if(debug) System.out.println(s);
    }
}