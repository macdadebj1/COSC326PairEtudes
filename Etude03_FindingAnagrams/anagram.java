import java.Util.Scanner;

public class anagram{

    private boolean debug = false;

    public static void main(String args[]){
        if(args.length > 0){
            if(args[0].charAt(0) == 'd' || args[0].charAt(0) =='D') debug = true;
        }

    }

    private static void readData(){
        Scanner scan = new Scanner(System.in);
        while(Scan.hasNextLine()){
            String line = Scan.nextLine();
        }
    }
}