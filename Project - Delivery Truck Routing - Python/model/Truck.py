# Truck class for the WGUPS Routing System by Alexandra Beno, ID: 011095454
# This class stores data for truck objects, including the IDs of the packages
# they are carrying, the speed they can move at, their locations, their time and
# distance spent travelling, and the time they left at.
# Additionally, this class also contains custom __str__ and
# __repr__ functions, condensed into a single toString function.

# Import default python libraries
import datetime

class Truck:

    # Initializes the truck class. Takes the truck Id, list of package IDs that it will be carrying, the location it's starting from (WGU), the time it's starting at, and the full hash table of all the packages.
    def __init__(self, truckID, packageIDlist, location, time, packageHash):
        self.truckID = truckID
        self.mph = 18
        self.maxpackages = 16
        self.distancetraveled = 0.0

        self.packages = packageIDlist
        self.packagescarried = len(self.packages)
        self.location = location
        self.startlocation = location
        self.time = time
        self.starttime = time
        # for each package ID, find the associated package in the main package hash table, and update
        # it so that it knows what truck it is on and that it has been loaded.
        for packageID in self.packages:
            package = packageHash[packageID][1]
            package.leavingtime = time
            package.tID = self.truckID
            package.status(datetime.timedelta(hours=8))

    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __str__(self):
        return self.toString()

    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __repr__(self):
        return self.toString()

    # Returns the truck as a string. Tries to provide it in an easily readable manner
    def toString(self):
        result = "Truck #" + str(self.truckID) + " Start Time: " + str(self.starttime) + " Start Location: " + str(
            self.startlocation) + " Current Time: " + str(self.time) + " Current Location: " + str(
            self.location) + " Distance Traveled " + str(self.distancetraveled) + " Packages Carried: " + str(
            self.packagescarried)
        result += "\nPackages IDs:\n"
        index = 0
        # for each package this is carrying, add it to the result string so that we can see what is left.
        for package in self.packages:
            result += str(package)
            if len(self.packages) - 1 > index:
                result += ", "
            index += 1
        return result

