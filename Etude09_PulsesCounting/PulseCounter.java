import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import com.github.psambit9791.jdsp.filter.Butterworth;

//https://www.gaussianwaves.com/2010/11/moving-average-filter-ma-filter-2/

public class PulseCounter{

    private static ArrayList<Double> initialData = new ArrayList<>();
    private static boolean debug = false;

    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].charAt(0) == 'd' || args[0].charAt(0) == 'D'){
                debug = true;
            }
        }
        readData();
        System.out.println("Number of Peaks in initialData: "+countNumberOfPeaksDouble(initialData));
        ArrayList<Double> filteredData = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            if(i == 0) filteredData = movingAverageFilterPass(initialData);
            else filteredData = movingAverageFilterPass(filteredData);
        }
        filteredData = bandPassFilter(filteredData);

        for(int i = 0; i < 5; i++){
            filteredData = movingAverageFilterPass(filteredData);
        }
/*
        for(int i = 0; i < 10; i++){
            if(i == 0) filteredData = vectorFilteringPass(initialData);
            else filteredData = vectorFilteringPass(filteredData);

        }*/
        filteredData.forEach((data)->System.out.println(data));
        System.out.println("Number of peaks after filtering: "+countNumberOfPeaksDouble(filteredData));
        //initialData.forEach((data)->System.out.println(data));


    }

    /**
     * Vector based filter, currently unused, but has a max window size of 3.
     */
    public static ArrayList<Double> vectorFilteringPass(ArrayList<Double> dataArray){
        if(debug) System.out.println("in vector filter");
        ArrayList<Double> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size();i++){
            if(debug) System.out.println("i: "+ i+" size of array: "+dataArray.size());
            if(i == 0){
                Vector2 dataVector = new Vector2(dataArray.get(0), dataArray.get(1));
                Vector2 smoothingVector = new Vector2(0.8, 0.1);
                outArray.add(dataVector.dot(smoothingVector));

            } else if (i == dataArray.size()-1){
                Vector2 dataVector = new Vector2(dataArray.get(i-1), dataArray.get(i));
                Vector2 smoothingVector = new Vector2(0.1, 0.8);
                outArray.add(dataVector.dot(smoothingVector));

            } else {
                Vector3 dataVector = new Vector3(dataArray.get(i-1), dataArray.get(i), dataArray.get(i+1));
                Vector3 smoothingVector = new Vector3(0.1, 0.8, 0.1);
                outArray.add(dataVector.dot(smoothingVector));

            }
            
        }
        return outArray;
    }

    /**
     * Moving average filter implementation, uses a window size of 3.
     * */
    public static ArrayList<Double> movingAverageFilterPass(ArrayList<Double> dataArray){
        if(debug) System.out.println("In filter!");
        ArrayList<Double> outArray = new ArrayList<>();
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

    private static ArrayList<Double> bandPassFilter(ArrayList<Double> data){
        ArrayList<Double> outData = new ArrayList<>();
        double[] inData = new double[data.size()];
        for(int i = 0; i < data.size();i++){
            inData[i] = data.get(i);
        }
        int order = 3;
        double sampleRate = 10;
        double nyq = sampleRate/2;
        double lowCut = 1.2;
        double highCut = 2.25;
        Butterworth butterworth = new Butterworth(inData,sampleRate);
        double[] filteredData = butterworth.bandPassFilter(order, lowCut,highCut);
        for(int i = 0; i < data.size(); i++){
            outData.add(i,filteredData[i]);
        }
        return outData;
    }

    private static int countNumberOfPeaks(ArrayList<Double> data){
        double average = calculateAverage(data);
        System.out.println("Average: "+average);
        int offset = 0;
        int peaks = 0;
        data.set(0,data.get(1));
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

    private static int countNumberOfPeaksDouble(ArrayList<Double> data){
        int average = calculateAverageDouble(data);
        System.out.println("Average: "+average);
        int offset = 0;
        int peaks = 0;
        for(int i = 0; i < data.size();i++){
            if (data.get(i) > average + offset){
            if(i == 0){
                if(data.get(i) > data.get(i+1)){
                    //peaks++;
                }
            }else if (i == data.size()-1){
                if(data.get(i) > data.get(i-1)){
                    //peaks++;
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

    private static double calculateAverage(ArrayList<Double> data){
        double sum = 0;
        double number = 0;
        for(int i = 0; i < data.size();i++){
            sum += data.get(i);
            number++;
        }
        return sum/number;
    }
    private static int calculateAverageDouble(ArrayList<Double> data){
        int sum = 0;
        int number = 0;
        for(int i = 0; i < data.size();i++){
            sum += data.get(i);
            number++;
        }
        return sum/number;
    }

    private static Double sum(Double i1, Double i2, Double i3){
        return (i1 + i2 + i3)/3;
    }

    private static void readData(){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            try {
                initialData.add(Double.parseDouble(line));
            } catch (NumberFormatException e){
                System.out.println("Bad Input: Data file should only include numbers");
                System.exit(0);
            }
        }
        sc.close();
    }

    

}