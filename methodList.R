library(doParallel)
library(foreach)
library(igraph)
library(proxy)

registerDoParallel(8)

data = read.csv("darcData.csv")
files = as.character(data$fileName)
num = as.numeric(data$fileNum)
cumnum = as.numeric(data$cumSumNum)


#darcSample is a function to randomly sample n rows from all of the data files
darcSample = function(n, fileStart = 1, fileEnd = nrow(data) , csv = FALSE){		
	
	files2read = files[fileStart:fileEnd]
	num2read = rmultinom(1, n, prob = num[fileStart:fileEnd])
	
	#index returns the index location of each file that has some rows to be extracted
	index = which(num2read > 0)
	
	rsample = foreach(i = seq(1, length(index), 1), .combine = rbind)%dopar%{
		#for each index location, in the variable index, to be extracted do the following:
		load(files2read[index[i]]) #load the file
		users[sample(nrow(users), num2read[index[i]]),] #sample however many rows the variable num2read says to extract from the file
	}
	
if(csv == TRUE){
	#if specified, write the sample to a csv
	write.csv(rsample, file = "darcSample.csv")
}	

return(rsample)

	
}

darcConsec = function(fileStart = 1, fileEnd = nrow(data),  csv = FALSE){
	files2read = files[fileStart:fileEnd]
	consecSample = foreach(i = seq(1,length(files2read), 1), .combine = rbind) %dopar% {
		load(files2read[i])
		users
	}
	
	if(csv == TRUE){
		#if specified, write the sample to a csv
		write.csv(consecSample, file = "darcConsec.csv")
	}
		
	return(consecSample)
}

darcJoint = function(g, csv = FALSE){
	x = table(degree(g,mode="in"),degree(g,mode="out"))
	infactor = factor(degree(g,mode="in"),levels=1:max(degree(g,mode="in")))
	outfactor = factor(degree(g,mode="out"),levels=1:max(degree(g,mode="out")))

	joint = table(infactor,outfactor)
	
	if(csv == TRUE){
		#if specified, write the sample to a csv
		write.csv(joint, file = "darcConsec.csv")
	}
	
	return(joint)
}