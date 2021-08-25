function z = plotSignal(filename)

file = fopen(filename,'r');
formatSpec = '%d';
size = [1 Inf];
data = fscanf(file,formatSpec,size);

plot(data);

end