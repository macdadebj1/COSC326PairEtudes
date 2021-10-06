import numpy as np
from scipy.signal import butter, lfilter,find_peaks
import fileinput

def butterBandpassFilter(data, lowCut, highCut, sRate, order):
    nyquistFrequency = sRate * 0.5
    low = lowCut/nyquistFrequency
    high = highCut/nyquistFrequency
    b, a = butter(order, [low,high], btype='band',analog=False)
    filteredData = lfilter(b,a,data)
    return filteredData

def countPeaks(data):
    peaks = find_peaks(data)
    return len(peaks)

def main():
    sampleRate = 10.0
    lowCutoff = 1.2
    highCutoff = 2.5
    order = 5
    data = []
    count = 0
    for line in fileinput.input():
        data.append(float(line))
    filteredData = butterBandpassFilter(data,lowCutoff,highCutoff,sampleRate,order)
    for i in range(len(filteredData)):
        print(filteredData[i])
        if i != 0 and i != len(filteredData)-1:
            if filteredData[i] > filteredData[i-1]:
                if filteredData[i] > filteredData[i+1]:
                    count = count +1

    print("Number of peaks: ")
    print(count)
main()

