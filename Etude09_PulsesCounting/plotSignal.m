function z = plotSignal(filename)
file = fopen(filename,'r');
formatSpec = '%d';
size = [1 Inf];
data = fscanf(file,formatSpec,size);

sampleFrequency = 200;
order = 10;
lowCutFrequency = 1.5;
highCutFrequency = 7.5;
%flag = 'scale';

window = blackman(order+1);

bandPass = fir1(order, [lowCutFrequency highCutFrequency]/(sampleFrequency/2), 'bandpass',window, 'scale'); $
discreteTimeFilter = dfilt.dffir(bandPass);
filteredData = filter(discreteTimeFilter,data);
%bandP = bandpass(data, [500,800], 1000);


hold on;
plot(data);
plot(filteredData);
%plot(bandP);
hold off;
count = 0;
for n=2: length(filteredData)-1
   if filteredData(n) > filteredData(n-1)
       if filteredData(n) > filteredData(n+1)
           count = count +1;
       end    
   end    
end
z = count;
end