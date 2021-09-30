import java.util.ArrayList;

//https://www.gaussianwaves.com/2010/11/moving-average-filter-ma-filter-2/

public class PulseCounter{

    ArrayList<Integer> initialData = new ArrayList<>();

    public static void main(String[] args){
        

    }

    public static ArrayList<Integer> movingAverageFilterPass(ArrayList<Integer> dataArray){
        ArrayList<Integer> outArray = new ArrayList<>();
        for(int i = 0; i < dataArray.size();i++){
            if(i == 0){
                outArray.add(i,(dataArray.get(i) + dataArray.get(i+1))/2);
            }else if(i == dataArray.size()-1){
                outArray.add(i,(dataArray.get(i) + dataArray.get(i-1))/2);
            }else{
                outArray.add(i,sum(outArray.get(i-1),outArray.get(i),outArray.get(i+1)));
            }
        }
        return outArray;
    }

    private static int countNumberOfPeaks(ArrayList<Integer> data){

        for(int i = 0; i < data.size();i++){

        }
    }

    private static int calculateAverage(ArrayList<Integer> data){
        
    }

    private static Integer sum(Integer i1, Integer i2, Integer i3){
        return (i1 + i2 + i3)/3;
    }

    private static void readData(){

    }
}