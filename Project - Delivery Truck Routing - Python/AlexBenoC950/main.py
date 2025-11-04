# WGUPS Routing Program by Alexandra Beno, ID: 011095454
# This is the main file. It handles all of the logic and calculations for the program.
# It contains code to read the CSV files and import them into the program,
# various methods for calculating algorithms and listing data, and methods to print an overview of the package data.

# Importing default python libraries
import csv
import datetime

# Importing other classes written for this project
from model.Package import Package
from model.Truck import Truck
from helper.AlexHashTable import AlexHashTable

# Creating hash tables to hold the address, package, and distance data.
# For the package hash table, the video by Amy Antonucci said to use a size of 10,
# but Elissa Thomas said to use a size of 40. To make sure collision was working, I used 10.
addressHash = AlexHashTable(10)
packageHash = AlexHashTable(10)
distanceHash = AlexHashTable(10)


# Imports the recipient data from the address CSV file, stores it in the address hash table. The third column, the addresses, is used as the key.
with open("../CSV/Addresses.csv") as csvfile:
    addresses = csv.reader(csvfile, delimiter=',')
    # for each row in the CSV file
    for ad in addresses:
        addressHash[ad[2]] = ad[1]




# Imports the data from the packages CSV file.
# Gets the recipient from the address hash table by using the address found in the packages file as a key.
# Creates a package object for each line of the CSV file, and adds it to the package hash table using the package ID as the key.
with open("../CSV/Packages.csv") as csvfile:
    packs = csv.reader(csvfile, delimiter=',')
    for pack in packs:
        pID = int(pack[0])
        pAddress = pack[1]
        pCity = pack[2]
        pState = pack[3]
        pZip = pack[4]
        pDeadline = pack[5]
        pWeight = pack[6]
        pNotes = pack[7]
        pRecipient = addressHash[pAddress][1]
        pStatus = "IN HUB"
        p = Package(pID, pAddress, pDeadline, pCity, pState, pZip, pWeight, pStatus, pRecipient, pNotes)
        # Package 9 must know both the wrong address and the correct address in order to be prepared in every scenario.
        if pID == 9:
            p.oldAddress = pAddress
            p.PackageAddress = "410 S State St"
            p.oldRecipient = pRecipient
            p.PackageRecipient = addressHash["410 S State St"][1]
        packageHash[pID] = p



# Imports the data from the distance CSV file.
# First extracts a single line worth of data from the CSV file,
# and then stores that data in the distance hash table.
# It uses the address as a key, and stores a tuple containing the index and then the list of distances to other locations.
with open("../CSV/Distance.csv") as csvfile:
    dists = csv.reader(csvfile, delimiter=',')
    index = 0
    # for each row in the CSV file
    for dist in dists:
        dKey = dist[0]
        list3 = []
        # for each item in the row
        for d in dist[1:]:
            if d != '':
                list3.append(float(d))
        distanceHash[dKey] = ((index, list3))
        index += 1


# Gets the distance between two addresses using the distance hash table. The two addresses are inputs. Format ex: "4001 South 700 East"
# Extracts tuples containing the indexes of the locations and the lists of distances to the other locations using the addresses as keys.
# It then determines and returns the distance from the larger index location to the smaller index location.
# Used as part of the nearest neighbor algorithm.
def getdist(address1, address2):
    dist1 = distanceHash[address1]
    dist2 = distanceHash[address2]
    index1 = dist1[1][0]
    index2 = dist2[1][0]
    if index1 > index2:
        return dist1[1][1][index2]
    else:
        return dist2[1][1][index1]


# Gets the destination address and package ID of the package on this truck that has the closest delivery location.
# Returns a tuple containing the address where the package must be delivered and the package's ID.
# The meat of the nearest neighbor algorithm. The truck you want to find the closest package delivery location to is the input.
def getclosest(truck):
    closestdist = 99999
    closestaddress = None
    closestid = None
    # for each package ID in the list of package IDs that the truck is carrying,
    # if the distance to that package's delivery destination is less than the previous closest distance,
    # make this the new closest distance.
    for packageID in truck.packages:
        package = packageHash[packageID]
        address = package[1].PackageAddress
        dist = getdist(address, truck.location)
        if dist < closestdist:
            closestdist = dist
            closestaddress = address
            closestid = packageID
    return ((closestaddress, closestid))


# Moves the truck from where it is to another location, and then delivers the package bound to that location.
# Takes the truck to move, the location to move to, and the package to deliver as inputs.
# Also calculates the distance traveled and increases the time the truck has been out based on the distance traveled.
def movetruck(truck, location, packageID):
    distancetraveled = getdist(location, truck.location)
    timetaken = datetime.timedelta(hours=distancetraveled / truck.mph)
    truck.distancetraveled += distancetraveled
    truck.distancetraveled = round(truck.distancetraveled, 2)

    truck.location = location
    truck.time += timetaken
    truck.packages.remove(packageID)
    package = packageHash[packageID]
    # timestamp the package delivery
    package[1].deliverytime = truck.time
    package[1].PackageStatus = "Delivered at "+str(truck.time)
    truck.packagescarried -= 1

# Sends a truck back home to WGU at 4001 South 700 East.
# Takes a truck as input, and increases the distance traveled and the time based on the distance traveled.
def gohome(truck):
    distancetraveled = getdist("4001 South 700 East", truck.location)
    timetaken = datetime.timedelta(hours=distancetraveled / truck.mph)
    truck.distancetraveled += distancetraveled
    truck.distancetraveled = round(truck.distancetraveled, 2)
    truck.location = "4001 South 700 East"
    truck.time += timetaken

# With a given truck as input, iterates through the list of packages stored on that truck,
# determines which has the closest delivery location, and then has the truck deliver that package.
# Continues until there are no packages left on the truck, and then sends the truck back to WGU at 4001 South 700 East.
def deliver(truck):
    # updates the package status in transit
    for packageID in truck.packages:
        package = packageHash[packageID][1]
        package.PackageStatus = "On Route - Truck #"+str(truck.truckID)
    # while there are packages to deliver, deliver them!
    while truck.packagescarried > 0:
        closest = getclosest(truck)
        movetruck(truck, closest[0], closest[1])
    # go home, your work is done!
    gohome(truck)

# With a given package ID and time as inputs, this method will return the status of the package at that time.
# Very similar to the method below that does the same thing but for all packages.
# Mostly relies on the status and toString functions in the Package class to get accurate readouts.
def packagetime(key, time):
    result = "ID | Recipient | Address | Street | City | State | Zip | Deadline | Weight | Status\n"
    package = packageHash[key][1]
    package.status(time)
    # special care is required for package 9. If it is before 10:20, it must display the wrong address. Afterwards, it can display correctly.
    if key == 9:
        if time < datetime.timedelta(hours=10, minutes=20):
            result += f'{package.PackageID} | {package.oldRecipient} | {package.oldAddress} | {package.PackageCity} | {package.PackageState} | {package.PackageZip} | {package.PackageDeadline} | {package.PackageWeight} Kilo(s) | {package.PackageStatus}'
        else:
            result += str(package)
    else:
        result += str(package)
    result += "\n"

    return result

# With a given time as input, this method will return the status of all the packages at that time.
# Very similar to the method above that does the same thing but for a single package.
# Mostly relies on the status and toString functions in the Package class to get accurate readouts.
def packagelisttime(time):
    result = "ID | Recipient | Address | Street | City | State | Zip | Deadline | Weight | Status\n"
    index = 1
    while index < 41:
        package = packageHash[index][1]
        package.status(time)
        # special care is required for package 9. If it is before 10:20, it must display the wrong address. Afterwards, it can display correctly.
        if index == 9:
            if time < datetime.timedelta(hours=10, minutes=20):
                result += f'{package.PackageID} | {package.oldRecipient} | {package.oldAddress} | {package.PackageCity} | {package.PackageState} | {package.PackageZip} | {package.PackageDeadline} | {package.PackageWeight} Kilo(s) | {package.PackageStatus}'
            else:
                result += str(package)
        else:
            result += str(package)
        result += "\n"
        index += 1
    return result

# Create the trucks, telling them their IDs, starting location, the list of package IDs associated with the packages they will be carrying,
# the place they're leaving from, the time they'll leave at, and the package hash table so they can do some looking up of their own.
# Everybody starts from WGU, but they leave at different times. Truck 1 leaves right away, but Truck 2 must wait for some of its packages to arrive before it can leave at 9:05.
# Truck 3 must wait until a driver returns to WGU, and also until package 9 gets its correct address at 10:20.
truck1 = Truck(1, [13, 14, 15, 16, 19, 20, 1, 29, 30, 31, 7, 34, 37, 40], "4001 South 700 East", datetime.timedelta(hours=8), packageHash)
truck2 = Truck(2, [3, 18, 36, 38, 6, 25, 28, 32, 12, 26, 2, 4, 5], "4001 South 700 East", datetime.timedelta(hours=9, minutes=5), packageHash)
truck3 = Truck(3, [9, 24, 17, 27, 21, 22, 23, 33, 8, 35, 10, 39, 11], "4001 South 700 East", datetime.timedelta(hours=10, minutes=20), packageHash)


# Sends Truck 1 and Truck 2 out when they are ready.
deliver(truck1)
deliver(truck2)
# Truck 3 only gets to leave if Truck 1 or Truck 2 have returned, since there are only 2 drivers.
if truck1.time < truck3.starttime or truck2.time < truck3.starttime:
    deliver(truck3)

# WGUPS Routing Program by Alexandra Beno, ID: 011095454
class main:
    # This is the main class, it handles the display of text to the user, and also handles user input.
    # This is where the user types in the time and the package ID that they want to see the status data for.
    # They are returned with a list of the packages they wanted to see, with all of the available info
    # minus the "Package Notes" (column 8 in Packages.CSV) as that is not part of the requirements.

    # try to see if the user enters something valid
    try:
        print("\nWGUPS Routing Program\nCreated by Alexandra Beno")
        # displays the total distance traveled of all three trucks combined, in miles.
        print("Total distance traveled (all three trucks combined): "+str(truck1.distancetraveled + truck2.distancetraveled + truck3.distancetraveled) + " miles.")
        print("\nPress enter to close the program, or before doing so input a time \nat which you wish to view the status of a package/all packages.\n")
        print("24 hr time. Format: HH:MM:SS")
        # gets the user's input on the time they want to see
        i = input()
        # if they didn't just hit enter to close the program
        if i != "":
            # splits the single string entry the user made into a list using python's split function, with : as the delimiter
            pieces = i.split(":")
            # get the hours, minutes, and seconds from their entry and turn them into a datetime object.
            hour = int(pieces[0])
            minute = int(pieces[1])
            second = int(pieces[2])
            time = datetime.timedelta(hours=hour, minutes=minute, seconds=second)
            print("Press enter to view the status of all packages at " + str(time) + ". Input a package ID before doing so to view the status of that package.")
            # get's the users input on the package(s) they want to see
            packageID = input()
            # if they didn't enter anything, show them all the packages.
            if(packageID == ""):
                print(packagelisttime(time))
            # if they entered an ID, show them only the package associated with that ID.
            else:
                print(packagetime(int(packageID), time))
    # catch the user not entering something valid.
    except ValueError:
        print("Invalid input.")