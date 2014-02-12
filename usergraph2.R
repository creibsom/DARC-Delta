# From Dave 2/10/14:
# The enclosed will read in two weeks of data and make two graphs, one for
# the first week, the other for the second. By turning this into a function
# and making it so one specifies the week (and/or the start/duration) one
# can have a function that reads in a graph covering a fixed amount of
# time. It takes 48 minutes to get both graphs on my machine and uses about
# 17% of the 32G ram.
# 
# The graphs have orders:  3,074,617 and 2,422,840 with 1,300,538 vertices
# in common. To see this last, do length(intersect(V(g1)$name,V(g2)$name))
# The largest connected components of the two graphs have 2,646,704 and
# 1,957,869 respectively.
# 
# I think you can use these graphs (or similar ones) to play around with as
# a starting point for your analysis.  The enclosed plot shows the degree
# distributions for the two graphs. It worries me -- I wonder if I'm doing
# something wrong.

require(lubridate) 
require(igraph)
 
t1 <- Sys.time() 
files <- list.files(path='Users',pattern="RData",full.names=TRUE) 
dates <- strptime(basename(files),"%Y%b%d_%H.RData") 
o <- order(dates) 
dates <- dates[o] 
files <- files[o]

## Needed because I have way more data than C&B. 
a <- which(dates>=ymd("2013-11-20") & dates<YMD("2014-01-23")) 
dates <- dates[a]
files <- files[a]
## You can strip the above off

w1 <- which(dates>=dates[1] & dates<=(dates[1]+days(7))) 
w2 <- which(dates>(dates[1]+days(7)) & 
		 dates<=(dates[1]+days(14))) 
files1 <- files[w1] 
files2 <- files[w2] 

load(files1[1]) 
a <- which(nchar(users[,'to'])>0) 
users <- users[a,] 
## probably faster to remove duplicate edges than to simplify
## after.
b <- which(duplicated(paste(users[,'from'],users[,'to']))) 
users <- users[-b,] 
g1 <- graph.edgelist(cbind(users[,'from'],users[,'to'])) 
cat(1,basename(files1[1]),vcount(g1),ecount(g1),"\n") 
for(i in 2:length(files1)){ 
	load(files1[i]) 
	a <- which(nchar(users[,'to'])>0) 
	users <- users[a,] 
	b <- which(duplicated(paste(users[,'from'],users[,'to']))) 
	users <- users[-b,] 
	h <- graph.edgelist(cbind(users[,'from'],users[,'to'])) 
	g1 <- g1 %u% h 
	cat(i,basename(files1[i]),vcount(g1),ecount(g1),"\n") 
} 
print(Sys.time()-t1) 

load(files2[1]) 
a <- which(nchar(users[,'to'])>0) 
users <- users[a,] 
b <- which(duplicated(paste(users[,'from'],users[,'to']))) 
users <- users[-b,] 
g2 <- graph.edgelist(cbind(users[,'from'],users[,'to'])) 
cat(1,basename(files2[1]),vcount(g2),ecount(g2),"\n") 
for(i in 2:length(files2)){ 
	load(files2[i]) 
	a <- which(nchar(users[,'to'])>0) 
	users <- users[a,] 
	b <- which(duplicated(paste(users[,'from'],users[,'to']))) 
	users <- users[-b,] 
	h <- graph.edgelist(cbind(users[,'from'],users[,'to'])) 
	g2 <- g2 %u% h 
	cat(i,basename(files2[i]),vcount(g2),ecount(g2),"\n") 
} 
print(Sys.time()-t1) 
