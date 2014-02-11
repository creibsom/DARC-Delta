count = 0
counter = 0

novLength = vector(length = 23+(24*(30-21)))
decLength = vector(length = 22+(24*(31-1)))
janLength = vector(length = 22+(24*(22-1)))
dataLength = vector(length = (length(novLength)+length(decLength)+length(janLength)))

novName = vector(length = 23+(24*(30-21)))
decName = vector(length = 22+(24*(31-1)))
janName = vector(length = 22+ (24*(22-1)))
dataName = vector(length = (length(novName)+length(decName)+length(janName)))
dataUnique = vector(length = (length(novName)+length(decName)+length(janName)))


months = c("Nov", "Dec", "Jan")
years = c("2013", "2014")
d = 1:31
h = 0:23

for(i in 1:length(d)){	
	if(i < 10){
		d[i] = paste("0", as.character(d[i]), sep = "")
	}else{
		d[i] = as.character(d[i])
	}
}

for(i in 1:length(h)){	
	if(i <= 10){
		h[i] = paste("0", as.character(h[i]), sep = "")
	}else{
		h[i] = as.character(h[i])
	}
}


hours = h
days = d


for(year in 1:length(years)){
#cat("1 \n")
	for(month in 1:length(months)){
#cat("2 \n")
		if(year == 1){
#cat("3 \n")
			if(month == 1){
#cat("4 \n")
				for(day in 20:20){
#cat("5 \n")
					for(hour in 1:19){
#cat("5a \n")			
						load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						novName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						novLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
						
						
						
					}
					
					for(hour in 21:length(hours)){	
#cat("5b \n")
						load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						novName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						novLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
					}
				}
				for(day in 21:30){
					for(hour in 1:length(hours)){
						load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						novName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						novLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
						
					}
				}
			}else if(month == 2){
#cat("6 \n")	
			count = 0
				for(day in 1:31){
#cat("7 \n")
					if(day == 14){
						for(hour in 1:15){
							
							load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
							count = count+1
							decName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							decLength[count] = length(users$id)
							
							counter = counter+1
							dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							dataLength[counter] = length(users$id)
							dataUnique[counter] = length(unique(users$id))
						
						}
						for(hour in 18:length(hours)){
							
							load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
							count = count+1
							decName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							decLength[count] = length(users$id)
							
							counter = counter+1
							dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							dataLength[counter] = length(users$id)
							dataUnique[counter] = length(unique(users$id))
						
						}
					}else{
						for(hour in 1:length(hours)){
							
							load(paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
							count = count+1
							decName[count] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							decLength[count] = length(users$id)
							
							counter = counter+1
							dataName[counter] = paste(years[1], months[month], days[day], "_", hours[hour], ".RData", sep = "")
							dataLength[counter] = length(users$id)
							dataUnique[counter] = length(unique(users$id))
						}
					}
				}
			}
			
		}else if((year == 2)&&(month==3)){
#cat("8 \n")	
		count = 0
		
			for(day in 1:22){
#cat("9 \n")
				if(day == 13){
					for(hour in 1:13){
						
						load(paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						janName[count] =  paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						janLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
						
					}
					for(hour in 16:length(hours)){
						
						load(paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						janName[count] =  paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						janLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
						
					}
					
				}else{
					for(hour in 1:length(hours)){
						
						load(paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = ""))
						count = count+1
						janName[count] =  paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						janLength[count] = length(users$id)
						
						counter = counter+1
						dataName[counter] = paste(years[2], months[month], days[day], "_", hours[hour], ".RData", sep = "")
						dataLength[counter] = length(users$id)
						dataUnique[counter] = length(unique(users$id))
						
					}
				}
			}
			
		}
	}
}

ncumsum = cumsum(dataLength)
ucumsum = cumsum(dataUnique)

data = data.frame(fileName = dataName, fileNum = dataLength, cumSumNum = ncumsum, uniqueUsers = dataUnique, cumSumUniq = ucumsum)

write.csv(data, file = "darcData.csv")
