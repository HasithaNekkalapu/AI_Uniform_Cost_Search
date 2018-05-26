# Artificial-Intelligence-Uniform Cost Search


Main Class:"final_route"

city nodes use "Node" class.
"fringe" variable is for the fringe.
"cityNames" variable is used to store names of cities in Array
"inputData" variable stores input.txt in array
"closed" variable to store all th explored cities, to prevent looping
"distance" variable to get and store distance between cities.

Node Class: 

Properties: name, parent and cost.
parent is used to trace the path taken.
cost for that node is the total path cost.

First the program reads input file and stores cities and distances in respective variables.
Uniform cost search method takes source city and destination city as inputs
Explored nodes are marked to prevent from infinite loop.
Nodes are created just before inserting into fringe.
Code now follows algorithm of Uniform Cost searh.
Instead of priority queue for fringe, Arraylist is used which is sorted at the end of each iteration.
After reaching Destination path is traced using parent of each node till we reach Source.

Instructions:
javac find_route.java
java find_route input1.txt Bremen Frankfurt
