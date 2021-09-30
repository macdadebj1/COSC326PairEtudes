import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

//https://www.gaussianwaves.com/2010/11/moving-average-filter-ma-filter-2/

public class PulseCounter{

    private static ArrayList<Integer> initialData = new ArrayList<>();
    private static boolean debug = false;

    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].charAt(0) == 'd' || args[0].charAt(0) == 'D'){
                debug = true;
            }
        }
        readData();
        System.out.println("Number of Peaks in initialData: "+countNumberOfPeaks(initialData));
        ArrayList<Integer> filteredData = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            if(i == 0) filteredData = movingAverageFilterPass(initialData);
            else filteredData = movingAverageFilterPass(filteredData);
        }
        
        System.out.println("Number of peaks after filtering: "+countNumberOfPeaks(filteredData));
        filteredData.forEach((data)->System.out.println(data));
        //initialData.forEach((data)->System.out.println(data));


    }

    public static ArrayList<Integer> vectorFilteringPass(ArrayList<Integer> dataArray){
        if(debug) System.out.println("in vector filter");
        ArrayList<Integer> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size();i++){
            if(debug) System.out.println("i: "+ i+" size of array: "+dataArray.size());
            if(i == 0){
                Vector<Integer>  dataVec = new Vector2(dataArray.get(i), dataArray.get(i+1));
                Vector<Float> smoothingVector = new Vector<>(0.8,0.1);
            }
        }
    }


    public static ArrayList<Integer> movingAverageFilterPass(ArrayList<Integer> dataArray){
        if(debug) System.out.println("In filter!");
        ArrayList<Integer> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size();i++){
            if(debug) System.out.println("i: "+ i+" size of array: "+dataArray.size());
            if(i == 0){
                if(debug) System.out.println("i = 0");
                outArray.add(i,(dataArray.get(i) + dataArray.get(i+1))/2);
            }else if(i == dataArray.size()-1){
                if(debug) System.out.println("at last index");
                outArray.add(i,(dataArray.get(i) + dataArray.get(i-1))/2);
            }else{
                if(debug) System.out.println("anything else");
                outArray.add(i,sum(dataArray.get(i-1),dataArray.get(i),dataArray.get(i+1)));
            }
        }
        return outArray;
    }

    private static int countNumberOfPeaks(ArrayList<Integer> data){
        int average = calculateAverage(data);
        System.out.println("Average: "+average);
        int offset = 0;
        int peaks = 0;
        for(int i = 0; i < data.size();i++){
            if(i == 0){
                if(data.get(i) > data.get(i+1)){
                    peaks++;
                }
            }else if (i == data.size()-1){
                if(data.get(i) > data.get(i-1)){
                    peaks++;
                }
            }
            else{
                if(data.get(i) > data.get(i-1) && data.get(i) > data.get(i+1)){
                    peaks++;
                }
            }
            //if(data.get(i) >= average+offset) peaks++;
        }
        return peaks;

    }

    private static int calculateAverage(ArrayList<Integer> data){
        int sum = 0;
        int number = 0;
        for(int i = 0; i < data.size();i++){
            sum += data.get(i);
            number++;
        }
        return sum/number;
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