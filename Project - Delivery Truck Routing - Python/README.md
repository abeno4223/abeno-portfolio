WGUPS Routing Program by Alexandra Beno, ID: 011095454

Developed in Python 3.13

IDE Pycharm 2025.1.2

Part I
---
The Packages and Addresses CSV files were provided for the project by Robert Ferdinand, program mentor at WGU via WGU connect on March 29th, 2025. Accessed 6/18/25. Citing source: https://wguconnect.wgu.edu/hub/wgu-connect/groups/c950-data-structures-and-algorithms-ii/discussion/question/168133/CSV-Data-Loading-Address-Information 

The Distance CSV is a cleaned version of the WGUPS Distance Table.xlsx file provided for the project via the task page. Date unknown. Accessed 6/16/25. Citing source: https://tasks.wgu.edu/student/011095454/course/30860017/task/4042/overview#:~:text=downtown%20map.docx-,WGUPS%20Distance%20Table,-.xlsx


PART F
---
1. The algorithm used in in the solution is the nearest neighbor algorithm. It is a self-adjusting greedy algorithm, meaning it is always trying to find the best solution it can at any given point. One strength of the nearest neighbor algorithm is that it will provide a short route through the given points. However, the route provided is not always optimal. In this solution, it yields a result of 122.4 total miles travelled. Though this is below the 140 mile benchmark, it is likely not the optimal way through the destinations. The other strength of the nearest neighbor algorithm is that it works quickly - it only takes one loop through a list to find the smallest number. Finding a more optimal route than the one the nearest neighbor solution provides would likely require a more complex or time-consuming algorithm.
2. The algorithm yielded a result of 122.4 total miles traveled, less than the benchmark of 140 miles. It is self-adjusting, meaning it is always considering what the best path forward is from its current position. The program provides an efficient route for the WGUPS system trucks to take, and delivers all 40 of its packages, accounting for special notes and delivery times. The code has comments explaining what each method and class does, even walking through specific loops and iterations, allowing the code and choices made so that the program can be easily maintained and adapted by others. The program includes an interface that allows a user to look up package data at specific points, such as which package is on/has been delivered by each truck, at any time they wish.
3.
a. 

One other algorithm that could accomplish this task is Dijkstra's algorithm. This algorithm would accomplish the task by comparing the routes from the starting node to each other node, constantly trying to find the shortest path. It could potentially find a faster route, as it could look at the paths between more than one node at a time while nearest neighbor only looks at one. However, this would come at the cost of greater complexity. 

Another alogirthm that could compete in this scenario is the 2-opt algorithm. By starting with an inefficient path that overlays with itself, the 2opt algorithm would swap the order the delivery locations were visited. With each swap potentially allowing it shorter paths, it could rapidly develop an optimal route through the delivery locations. But in a worst case scenario, it would take much longer to determine a solution than the nearest neighbor algorithm would.

PART G
---
If I did this project again, I would focus on making sure it would be adaptable to a variety of scenarios. Currently this program is designed to run with a specific set of 40 packages in a specific set of locations. One way this program could be improved like this is by designing trucks so that they are automatically loaded after the package data is retrieved from the CSV file. That way the program can be automatically provided with packages and the trucks will be readied without the user needing to manually assign them. Another change that could be made would be adapting the distance and address variables and functions so that they can support any addresses or start locations. These changes would allow the program to be easier adapted to other locations or other scenarios that arise.

PART H
---
The data structure used in the solution is a chaining hash table called AlexHashTable. Using no other libraries or classes, the AlexHashTable class is able to store a variety of data types in this project. Though the main feature is the package hash table, the AlexHashTable class is also used to store data for the distances and addresses in this project. It allows for easy storage and retrieval of whatever data you want to store, including valuable package information such as the delivery address, delivery deadline, delivery city, delivery zip code, package weight, and delivery status, provided you have an associated key. You can also retrieve any stored information at will through a lookup function, again provided you have the associated key. Though it only stores a maximum of 40 items in this project, the AlexHashTable class is cable of holding many more.

1. Though the hash table is a valuable data structure that meets the requirements, it might be overkill for this situation. A simple array could accomplish this scenario's requirements, provided the requirements didn't include using a hash table. The most items stored in a data structure in this scenario are the 40 packages, and they all are sorted linearly when you read them from the CSV file. They all have IDs, 1-40, and could easily just be placed in a 40-length array. If you wanted the information for package 30, it's easier to get item 30 out of a list than it is to find the hash associated with key 30 and then get the package information you want. Another data structure that could accomplish this task is the binary search tree. It is a tree where a given node is less than all of its descendants to the right and greater than all of its descendants to the left. By using the package IDs as keys, you could store the package data easily, and be able to retrieve it easily through binary search. The time complexity would even be similar to that of the hash table.

a.  The hash table used in the program is very different from a standard array in a variety of ways. A standard array holds a fixed amount of data, while the chaining hash table used in the program can hold infinite data if memory is infinite, without limits having to be defined prior. The chaining hash table represents a list of lists, while arrays are primitive versions of lists. The hash table is also different from the binary search tree. The tree's lookup requires you to iterate through its list, but the hash table merely requires you to plug a key into a hash function to locate the slot a value needs to be plugged into. However, in average case, the binary search tree could potentially be less time complex than the hash table.


Program Explanation:
---
The WGUPS Routing Program created by Alexandra Beno is designed to efficiently route three trucks through their deliveries of 40 packages from the WGU HUB to their delivery destinations. It takes CSV files as inputs, requiring one for Addresses, Distance, and Packages. It stores most of its data using a custom chaining hash table class called AlexHashTable. Using the nearest neighbor algorithm, it produces a total mileage of 122.4 miles, less than the required benchmark of 140. Running the program will present you with a console interface that allows you to input a time and check the status of a package/all packages at that time.

Directory explanation:
---
AlexBenoC950: Main directory, contains screenshots for parts D and E and README.md for parts F, G, H, and I

Subdirectories:
----
alexbenoc950: contains main.py, parts B, C, D

CSV: contains Addresses.csv, Distance.csv, and Packages.csv

helper: contains AlexHashTable.py, part A

model: contains Package.py and Truck.py
