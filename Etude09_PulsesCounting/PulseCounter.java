import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import uk.me.berndporr.iirj.*;

//https://www.gaussianwaves.com/2010/11/moving-average-filter-ma-filter-2/

public class PulseCounter{

    private static ArrayList<Float> initialData = new ArrayList<>();
    private static boolean debug = false;

    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].charAt(0) == 'd' || args[0].charAt(0) == 'D'){
                debug = true;
            }
        }
        readData();
        System.out.println("Number of Peaks in initialData: "+countNumberOfPeaks(initialData));
        ArrayList<Float> filteredData = new ArrayList<>();
        
        for(int i = 0; i < 3; i++){
            if(i == 0) filteredData = movingAverageFilterPass(initialData);
            else filteredData = movingAverageFilterPass(filteredData);
        }
        /*
        for(int i = 0; i < 10; i++){
            if(i == 0) filteredData = vectorFilteringPass(initialData);
            else filteredData = vectorFilteringPass(filteredData);
            
        }*/
        System.out.println("Number of peaks after filtering: "+countNumberOfPeaks(filteredData));
        //filteredData.forEach((data)->System.out.println(data));
        //initialData.forEach((data)->System.out.println(data));


    }

    /**
     * Vector based filter, currently unused, but has a max window size of 3.
     */
    public static ArrayList<Float> vectorFilteringPass(ArrayList<Float> dataArray){
        if(debug) System.out.println("in vector filter");
        ArrayList<Float> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size();i++){
            if(debug) System.out.println("i: "+ i+" size of array: "+dataArray.size());
            if(i == 0){
                Vector2 dataVector = new Vector2(dataArray.get(0), dataArray.get(1));
                Vector2 smoothingVector = new Vector2(0.8f, 0.1f);
                outArray.add(dataVector.dot(smoothingVector));

            } else if (i == dataArray.size()-1){
                Vector2 dataVector = new Vector2(dataArray.get(i-1), dataArray.get(i));
                Vector2 smoothingVector = new Vector2(0.1f, 0.8f);
                outArray.add(dataVector.dot(smoothingVector));

            } else {
                Vector3 dataVector = new Vector3(dataArray.get(i-1), dataArray.get(i), dataArray.get(i+1));
                Vector3 smoothingVector = new Vector3(0.1f, 0.8f, 0.1f);
                outArray.add(dataVector.dot(smoothingVector));

            }
            
        }
        return outArray;
    }

    /**
     * Moving average filter implementation, uses a window size of 3.
     * */
    public static ArrayList<Float> movingAverageFilterPass(ArrayList<Float> dataArray){
        if(debug) System.out.println("In filter!");
        ArrayList<Float> outArray = new ArrayList<>();
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

    private static ArrayList<Float> bandPassFilter(ArrayList<Float> data){
        ArrayList<Float> filteredData = new ArrayList<>();
        int order = 6;
        double sampleRate = 10;
        double frequencyCutoff = 10;
        Butterworth butterworth = new Butterworth();
        butterworth.bandPass(order, sampleRate, frequencyCutoff, frequencyCutoff/4);
        for(int i = 0; i < data.size(); i++){
            filteredData.put(i,butterworth.filter(data.get(i)));
        }
        return filteredData;
    }

    private static int countNumberOfPeaks(ArrayList<Float> data){
        int average = calculateAverage(data);
        System.out.println("Average: "+average);
        int offset = 0;
        int peaks = 0;
        for(int i = 0; i < data.size();i++){
            if (data.get(i) > average + offset){
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
            }
            //if(data.get(i) >= average+offset) peaks++;
        }
        return peaks;

    }

    private static int calculateAverage(ArrayList<Float> data){
        int sum = 0;
        int number = 0;
        for(int i = 0; i < data.size();i++){
            sum += data.get(i);
            number++;
        }
        return sum/number;
    }

    private static Float sum(Float i1, Float i2, Float i3){
        return (i1 + i2 + i3)/3;
    }

    private static void readData(){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            try {
                initialData.add(Float.parseFloat(line));
            } catch (NumberFormatException e){
                System.out.println("Bad Input: Data file should only include numbers");
                System.exit(0);
            }
        }
        sc.close();
    }

    

}