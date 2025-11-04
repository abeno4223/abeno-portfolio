# Package class for the WGUPS Routing System by Alexandra Beno, ID: 011095454
# This class stores data for individual packages, including
# the times they leave WGU and the times they are delivered.
# Additionally, this class also contains custom __str__ and
# __repr__ functions, condensed into a single toString function.
# Also handles the display of the package's status at any given time.

# Importing default python libraries
import datetime


class Package:

    # Initializes the package. Takes the package ID, address, delivery deadline, city, state, zip, weight, status, recipient, and notes as inputs.
    def __init__(self, PackageID, PackageAddress, PackageDeadline, PackageCity, PackageState, PackageZip, PackageWeight, PackageStatus, PackageRecipient, PackageNotes):
        self.PackageID = PackageID
        self.PackageState = PackageState
        self.PackageAddress = PackageAddress
        self.PackageDeadline = PackageDeadline
        self.PackageCity = PackageCity
        self.PackageZip = PackageZip
        self.PackageWeight = PackageWeight
        self.leavingtime = None
        self.deliverytime = None
        self.tID = None
        self.PackageRecipient = PackageRecipient
        self.PackageNotes = PackageNotes
        # sets up the package status for the morning, before involving trucks.
        self.status(datetime.timedelta(hours=8))
        self.oldAddress = None
        self.oldRecipient = None


    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __str__(self):
        return self.toString()

    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __repr__(self):
        return self.toString()

    # Lists out the data from the package in an easily readable manner.
    def toString(self):
        return f'{self.PackageID} | {self.PackageRecipient} | {self.PackageAddress} | {self.PackageCity} | {self.PackageState} | {self.PackageZip} | {self.PackageDeadline} | {self.PackageWeight} Kilo(s) | {self.PackageStatus}'

    # Returns the status of the package at a given time.
    def status(self, time):
        # if the package has its delivery timestamp and it's past the delivery time, return when the package was been delivered and by which truck.
        if self.deliverytime is not None and self.deliverytime < time:
            self.PackageStatus = "Delivered at " + str(self.deliverytime) + " by Truck #" + str(self.tID)
        # if the package has its leaving time and that time is in the past, but the package has not yet been delivered,
        # return that the package is en route to its destination and the truck its on.
        elif self.leavingtime is not None and self.leavingtime < time:
            self.PackageStatus = "En Route on Truck #"+str(self.tID)
        # if the package hasn't left the HUB but it has been loaded onto a truck, return which truck that is.
        elif self.tID is not None:
            self.PackageStatus = "IN HUB - Loaded on Truck #"+str(self.tID)
        # if the package hasn't been loaded onto a truck, return that the package is in the hub.
        else:
            self.PackageStatus = "IN HUB"
        # packages 6, 25, 28, and 32 require special care, as they don't arrive at the hub until 9:05 am.
        # so, if it's before 9:05 am, report that the packages still haven't arrived at the facility.
        if (self.PackageID == 6 or self.PackageID == 25 or self.PackageID == 28 or self.PackageID == 32) and time < datetime.timedelta(hours=9, minutes=5):
            self.PackageStatus = "Delayed on flight. Awaiting arrival in Hub."
        # package 9 requires special care. If it is before 10:20, the wrong address must be returned. Otherwise, the correct address must be returned.
        if self.tID is not None and self.PackageID == 9 and time < datetime.timedelta(hours=10, minutes=20):
            self.PackageStatus = "IN HUB - Loaded on Truck #"+str(self.tID)+" - Wrong address listed, awaiting correction"
        elif self.tID is None and self.PackageID == 9 and time < datetime.timedelta(hours=10, minutes=20):
            self.PackageStatus = "IN HUB - Wrong address listed, awaiting correction"