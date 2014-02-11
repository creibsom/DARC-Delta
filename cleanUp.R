# Author: Cody Reibsome (creibsom@mail.umw.edu)
# Last Updated: 2/11/2014
library(doParallel)
library(foreach)

# Allows for use of up to 8 cores, won't negatively affect performance if numCores < 8.
registerDoParallel(8)

# Strips all bad data from the files given by darcData.csv.
# Returns a data frame with [numToFieldEmptyRemoved, numRetweetsRemoved] for each file.
cleanData = function() {
  data = read.csv("darcData.csv")
	out = data.frame(row.names = NULL)
	cat(paste(""), file = "log.txt")  # Empty log.txt file of information from previous runs.
	
  # For every file listed in darcData.csv:
  # 1) Load the file
  # 2) Append a statement to log.txt noting that the file is being edited
  # 3) Determine which rows have blank "to" field
  # 4) Remove them (if none, do nothing)
  # 5) Repeat 4-5 for retweets
  # 6) Save the file
  # 7) Keep track of the number of tweets removed from each file (stored in out)
	out = foreach(i = seq(1, length(data$X), 1), .combine = rbind) %dopar% {
	  load(paste("Data", data[i,]$fileName, sep = "/"))
		cat(paste("Editing file", data[i,]$fileName, "\n"), file = "log.txt", append = TRUE)
		toRemoveTo = which(nchar(users$to) == 0)
		# Edge-case: If length(toRemoveTo) == 0, do nothing (otherwise all entries will be removed from users)
		if (length(toRemoveTo) > 0) users = users[-toRemoveTo,]  
		toRemoveRT = grep("RT:", users$tweet, ignore.case = TRUE)
		# Edge-case: If length(toRemoveRT) == 0, do nothing (otherwise all entries will be removed from users)
		if (length(toRemoveRT) > 0) users = users[-toRemoveRT,]
		save(users, file = as.character(data[i,]$fileName))
		c(out, data.frame(blankToFieldNum = length(toRemoveTo), retweets = length(toRemoveRT)))
	}
	return(out)
}		