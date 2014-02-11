library(doParallel)
library(foreach)

registerDoParallel(8)
darcSample = function(n, csv = FALSE){
	load("2013Nov20_00.RData")
	headers = as.data.frame(users)
	rsample = data.frame( row.names = names(headers))
	
	sample = vector(length=n)
	data = read.csv("darcData.csv")
	rand = floor(runif(n,1,max(data$cumSumNum)))
	num = vector(length = length(data$cumSumNum))
	
	for(j in 1:length(data$cumSumNum)){
		for(i in 1:length(rand)){	
			if((rand[i]>=1)&&(rand[i]<data$cumSumNum[1])){
				num[j] = num[j] + 1 
			}
			else if((rand[i]>=data$cumSumNum[j])&&(rand[i]<=data$cumSumNum[j+1])){
				num[j] = num[j] + 1 
			}

		}
	}
	
	index = which(num > 0)
	
	rsample = foreach(i = seq(1, length(index), 1), .combine = rbind)%dopar%{
		load(as.character(data$fileName[index[i]]))
		new = users[sample(nrow(users), num[index[i]]),]
		c(rsample, new)
	}
	
if(csv == TRUE){
	write.csv(rsample, file = "darcSample.csv")
}	

return(rsample)

	
}
