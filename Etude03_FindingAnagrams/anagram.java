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
        System.out.println("======================================");
        doAnagrams();

    }

    /**
     * iterates through toSolve Array and prints the best anagram for each word.
     * */
    private static void doAnagrams(){
        if(debug) System.out.println("In do anagrams");
        for(int i = 0; i < toSolve.size();i++){
            String word = toSolve.get(i);
            System.out.println(word+": "+findAnagram(word));
            if(debug) System.out.println("==============================");
        }
    }

    /**
     * Method to find the best possible anagram of the given word from the dictionary.
     * */
    private static String findAnagram(String s){
        String sortedString = sortString(s);
        sortedString.replaceAll(" ","");
        if(debug) System.out.println("Trying to find an anagram for the String: "+s);
        ArrayList<String> list; //list of partial or full anagrams...
        int longestPartialAnagram = 0;
        String anagram = "";
        if(dictionary.containsKey(sortedString)){ //If we have an entry in the dictionary that is already an anagram of the word.
            if(debug) System.out.println("This is already a perfect anagram!");
            list = dictionary.get(sortedString);
            return list.get(0);
        } else{
            if(debug) System.out.println("We didn't find a perfect anagram!");
            list = new ArrayList<>();
            int charsRemaining = sortedString.length();
            for(String str : dictionary.keySet()){
                if(isPartialAnagram(sortedString,str)) list.add(dictionary.get(str).get(0));
            }
            Collections.sort(list, (string1, string2) -> Integer.compare(string2.length(),string1.length()));
            if(debug) System.out.println("~~~LIST OF PARTIAL ANAGRAMS:~~~");
            if(debug) list.forEach((str)->System.out.println(str));
            if(debug) System.out.println("~~~End List~~~");
            HashMap<Character,Integer> StringMap;
            String anagramToReturn = "";
            for(int i = 0; i < list.size();i++){
                anagramToReturn = "";
                StringMap = convertStringToHashMap(sortedString);
                if(debug) printHashMap(StringMap);
                //FIND BEST COMBINATION OF PARTIAL ANAGRAMS!
                for(int j = i; j <list.size();j++){
                   /* String tempAnagram = anagramToReturn;
                    if(debug) System.out.println("Trying: "+list.get(j)+" our current anagram is: "+tempAnagram);
                    if(tempAnagram.length()-countCharOccurencesInString(tempAnagram,' ') ==sortedString.length()){
                        if(debug) System.out.println("Found a multiWord Anagram: "+tempAnagram);
                        return tempAnagram;
                    }else if(tempAnagram.length()-countCharOccurencesInString(tempAnagram,' ') >sortedString.length()){
                        if(debug) System.out.println("A potential anagram was longer than the inputted String! :( trying again!");
                        break;
                    }else{
                        if(debug) System.out.println("We found a bad multiword anagram: "+ tempAnagram);
                    }
                    if(checkWordAgainstAnagram(StringMap,list.get(j))){
                        tempAnagram+=list.get(j)+" ";

                    }else break;*/
                }
            }
        }
        return anagram;
    }

    private static int countCharOccurencesInString(String s, char c){
        int numberOfOccurences = 0;
        for(char ch : s.toCharArray()){
            if(ch == c){
                numberOfOccurences++;
            }
        }
        return numberOfOccurences;
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
        map.forEach((key, value)->System.out.println(key + ": " + value));
        /*for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
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


    private static ArrayList<String> findPartialAnagrams(String toSolveFor, ArrayList<String> dict){
        
    }


    private static boolean isAnagram(String x, String y){
        if(debug) System.out.println("Str1: "+x+". Str2: "+y);
        return sortString(x).equals(sortString(y));
    }

    /**
     * @param charMap the word that we are trying to find anagrams of, converted to a hashmap of chars, with their frequencies.
     * @param wordToCheck the word to check.
     * */
    private static boolean isPartialAnagram(String mainWord, String wordToCheck){
        HashMap<Character,Integer> charMap = convertStringToHashMap(mainWord);
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

    private static boolean checkWordAgainstAnagram(HashMap<Character,Integer> charMap, String wordToCheck){
        HashMap<Character,Integer> tempHM = deepCopyHashMap(charMap);
        if(debug) System.out.println("In checkWordAgainstAnagram");
        for(char ch: wordToCheck.toCharArray()){
            Integer numChars = tempHM.get(ch);
            if(numChars == null){
                if(debug) System.out.println("Couldn't find a reference to "+ch+" in checkWordAgainstAnagram");
                return false;
            }
            else if(numChars == 0){
                if(debug) System.out.println("Found a reference to "+ch+" but there are 0 more available!");
                return false;
            }
            else{
                if(debug) System.out.println("Found a reference to "+ch+" and we subtracted one from number available");
                tempHM.put(ch,tempHM.get(ch)-1);
            }
        }
        charMap = deepCopyHashMap(tempHM);
        if(debug) System.out.println("Copying tempHashMap back into mainHashMap!");
        return true;
    }

    private static HashMap<Character,Integer> deepCopyHashMap(HashMap<Character,Integer> hm){
        HashMap<Character,Integer> tempHM = new HashMap<>();
        hm.forEach((key,value)-> tempHM.put(new Character(key),new Integer(value)));
        return tempHM;
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


    /*public class StrLenghtComparitor implements Comparitor<String>{
        @Override
        public int compare(String s1, String s2){
            if(s1.lenght() != s2.length()){
                return s1.length() - s2.length();
            }
            return s1.compareTo(s2);
        }
    }*/
}