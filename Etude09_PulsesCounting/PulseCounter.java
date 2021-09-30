import java.util.ArrayList;
import java.util.Scanner;

//https://www.gaussianwaves.com/2010/11/moving-average-filter-ma-filter-2/

public class PulseCounter{

    private static ArrayList<Integer> initialData = new ArrayList<>();

    public static void main(String[] args){
        

    }

    public static ArrayList<Integer> movingAverageFilterPass(ArrayList<Integer> dataArray){
        ArrayList<Integer> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size()-1;i++){
            if(i == 0){
                outArray.add(i,(dataArray.get(i) + dataArray.get(i+1))/2);
            }else if(i == dataArray.size()-2){
                outArray.add(i,(dataArray.get(i) + dataArray.get(i-1))/2);
            }else{
                outArray.add(i,sum(outArray.get(i-1),outArray.get(i),outArray.get(i+1)));
            }
        }
        return outArray;
    }

    private static Integer sum(Integer i1, Integer i2, Integer i3){
        return (i1 + i2 + i3)/3;
    }

    private static void readData(){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            try {
                initialData.add(Integer.parseInt(line));
            } catch (NumberFormatException e){
                System.out.println("Bad Input: Data file should only include numbers");
                System.exit(0);
            }
        }
        sc.close();
    }

    

}