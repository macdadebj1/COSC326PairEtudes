import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class anagram{

    private static boolean debug = false;
    private static ArrayList<String> toSolve = new ArrayList<>();
    private static HashMap<String,ArrayList<String>> dictionary = new HashMap<>();
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
        doAnagrams();

    }

    /**
     * iterates through toSolve Array and prints the best anagram for each word.
     * */
    private static void doAnagrams(){
        for(int i = 0; i < toSolve.size();i++){
            String word = toSolve.get(i);
            System.out.println(word+": "+findAnagram(word));
        }
    }

    /**
     * Method to find the best possible anagram of the given word from the dictionary.
     * */
    private static String findAnagram(String s){
        String sortedString = sortString(s);
        ArrayList<String> list;
        int longestPartialAnagram = 0;
        if(dictionary.containsKey(sortedString)){ //If we have an entry in the dictionary that is already an anagram of the word.
            if(debug) System.out.println("This is already a perfect anagram!");
            list = dictionary.get(sortedString);
            return list.get(0);
        } else{
            if(debug) System.out.println("We didn't find a perfect anagram!");
            int charsRemaining = sortedString.length();
            HashMap<Character,Integer> StringMap = convertStringToHashMap(sortedString);
            if(debug) printHashMap(StringMap);
            for(String str : dictionary.keySet()){
                if(debug) System.out.println("Trying "+str);
                if(isPartialAnagram(StringMap,str)){
                    if(debug) System.out.println("This is a partial anagram!");
                    if(str.length() > longestPartialAnagram){
                        longestPartialAnagram = str; //If we cannot find an anagram with this longest, it will break...
                        //I think should use an arrayList of arrayLists to show the different combinations..?
                    }
                }else{
                    if(debug) System.out.println("This is not a partial anagram! :(");
                }
            }


        }
        return"";
    }

    private static HashMap<Character,Integer> convertStringToHashMap(String s){
        HashMap<Character,Integer> map = new HashMap<>();
        for(char ch : s.toCharArray()){
            if(map.containsKey(ch)){
                map.put(ch,map.get(ch)+1);
            }else{
                map.put(ch,1);
            }
        }
        return map;
    }

    /**
     * Reads data from stdin and saves it either to the toSolve array or the dictionary.
     * */
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
        dictionary.forEach((k,v)->Collections.sort(v));
        //goes through each list in the dictionary and sorts it alphabetically, this means we only need to get the first
        //element to get the 'best' anagram (as defined in the project sheet).
    }

    private static void printHashMap(HashMap<Character,Integer> map){
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
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
        if(debug) System.out.println(sortedWord);
        if(!dictionary.containsKey(sortedWord)){
            dictionary.put(sortedWord, new ArrayList<String>());
        }
        dictionary.get(sortedWord).add(word);
    }

    /**
     * Sorts the chars in a string alphabetically.
     * */
    private static String sortString(String s){
        char[] cA = s.toCharArray();
        Arrays.sort(cA);
        return new String(cA);
    }

    private static boolean isAnagram(String x, String y){
        return sortString(x).equals(sortString(y));
    }

    /**
     * @param charMap the word that we are trying to find anagrams of, converted to a hashmap of chars, with their frequencies.
     * @param wordToCheck the word to check.
     * */
    private static boolean isPartialAnagram(HashMap<Character,Integer> charMap, String wordToCheck){
        if(debug) System.out.println("In isPartialAnagram");
        for(char ch: wordToCheck.toCharArray()){
            Integer numChars = charMap.get(ch);
            if(numChars == null){
                if(debug) System.out.println("Couldn't find a reference to "+ch+" in isPartialAnagram");
                return false;
            }
            else if(numChars == 0){
                if(debug) System.out.println("Found a reference to "+ch+" but there are 0 more available!");
                return false;
            }
            else{
                if(debug) System.out.println("Found a reference to "+ch+" and we subtracted one from number available");
                charMap.put(ch,charMap.get(ch)-1);
            }
        }
        return true;

    }

    /**
     * Helper method to print the dictionary.
     * */
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

}