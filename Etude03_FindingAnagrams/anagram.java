import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class anagram{

    private static boolean debug = false;
    private static ArrayList<String> toSolve = new ArrayList<>();
    private static HashMap<Long,String> dictionary = new HashMap<>();
    private static HashMap<Character,Integer> uniqueCharStore = new HashMap<>();

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
        loadUniqueCharPairs();
        doAnagrams();

    }

    private static void doAnagrams(){
        for(int i = 0; i < toSolve.size();i++){
            String word = toSolve.get(i);
            System.out.println(word+": "+findAnagram(word));
        }
    }

    private static String findAnagram(String s){

        findUniqueNumber(s);
        return"";
    }

    private static long findUniqueNumber(String s){
        long result = 1;
        for(int i = 0; i < s.length();i++){
            result*=uniqueCharStore.get(s.charAt(i));
        }
        if(debug) System.out.println("Result: "+result);
        return result;
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

        dictionary.put(findUniqueNumber(word),word);
    }

    private static String sortString(String s){
        char[] cA = s.toCharArray();
        Arrays.sort(cA);
        return new String(cA);
    }

    private static void printDictionary(){
        dictionary.forEach((key,value) -> {
            System.out.println(key+": "+value);
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

    /**
     * I'm so sorry, this is the worst thing I have ever done...
     * I figured it would take me much less time to do it like this than importing my primes
     * code and debugging my code to do this computationally...
     *
     * */
    private static void loadUniqueCharPairs(){
        uniqueCharStore.put(' ',1);
        uniqueCharStore.put('a',2);
        uniqueCharStore.put('b',3);
        uniqueCharStore.put('c',5);
        uniqueCharStore.put('d',7);
        uniqueCharStore.put('e',11);
        uniqueCharStore.put('f',13);
        uniqueCharStore.put('g',17);
        uniqueCharStore.put('h',19);
        uniqueCharStore.put('i',23);
        uniqueCharStore.put('j',29);
        uniqueCharStore.put('k',31);
        uniqueCharStore.put('l',37);
        uniqueCharStore.put('m',41);
        uniqueCharStore.put('n',43);
        uniqueCharStore.put('o',47);
        uniqueCharStore.put('p',53);
        uniqueCharStore.put('q',59);
        uniqueCharStore.put('r',61);
        uniqueCharStore.put('s',67);
        uniqueCharStore.put('t',71);
        uniqueCharStore.put('u',73);
        uniqueCharStore.put('v',79);
        uniqueCharStore.put('w',83);
        uniqueCharStore.put('x',89);
        uniqueCharStore.put('y',97);
        uniqueCharStore.put('z',101);
    }
}