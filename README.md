
# Bus Route Challenge

<b>Assumptions</b>

1) Map<Departure, Set<Arrival>> - Data structure used to achive minimum responce time in finding out connected direct stations

2) This data structure is applicable only to find the if there is a direct connection exist. any additional requirement need a changes to the data structure

3) Current in-memory model should to be modified if the data file size increses beyound the requirement limit. Cache servers like redis or EVCache can be used. 
 
4) All the station mentioned in a route row in the data file is in forward direction 

5) Empty rows in the data file are ignored.

6) If the data file consist of any non integer content, file is considerd invalid

7) "data/example" is taken as the default data processing file, if no file is provided as input.


<b>Test Steps</b>

1) git clone https://github.com/ArunRamakani/bus_route_challenge.git

2) cd bus_route_challenge

3) ./service.sh dev_build

4) ./service.sh dev_run /Users/hack/Documents/mygithub/javaproblem/bus-route-challenge/src/test/resources/MaxRouteStation  (./service.sh dev_run <file-name>)

