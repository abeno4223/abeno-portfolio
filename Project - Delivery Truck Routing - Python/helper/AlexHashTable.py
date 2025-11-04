# Hash table class for the WGUPS Routing System by Alexandra Beno, ID: 011095454
# Covers everything from the hash function to getting, setting, and removing items.
# Not just limited to packages!
# Additionally, this class also contains custom __str__ and
# __repr__ functions, condensed into a single toString function.

class AlexHashTable:

    # Initializes the hash table. Takes a length as a parameter, and creates a list with length lists inside.
    def __init__(self, length):
        self.length = length
        self.list1 = [[] for slot in range(self.length)]

    # Sets the value of a slot in the table based on a given key value pair.
    # usage: AlexHashTable[key] = value
    def __setitem__(self, key, value):
        # get the slot to put the value in
        slot = self.getslot(key)
        found = False
        # get the list in this slot
        list2 = self.list1[slot]
        # for each tuple already in this slot
        for pair in list2:
            # if the key is already somewhere in this list, change the value at that location.
            if pair[0] == key:
                pair[1] = value
                found = True
        # if the key is not already somewhere in this list, append this new tuple of a key value pair
        if not found:
            list2.append((key, value))

    # The hash function. Takes a key, turns it into unicode, and returns the unicode key modulo the length of the list.
    # That number will be the slot in the list associated with that key.
    def getslot(self, key):
        # print(key % self.length)
        slot = 0
        for character in str(key):
            slot += ord(character)
        return slot % self.length

    # Gets the item associated with a given key from the table, or nothing at all if that key is not stored here.
    # usage: itemdesired = AlexHashTable[key]
    def __getitem__(self, key):
        slot = self.getslot(key)
        list2 = self.list1[slot]
        # for each tuple in this slot, if the key is found, return that key value pair.
        for pair in list2:
            if pair[0] == key:
                return pair
        return None

    # Deletes an item with an associated key. Returns the removed item if possible.
    # usage: del AlexHashTable[key]
    def __delitem__(self, key):
        slot = self.getslot(key)
        list2 = self.list1[slot]
        # for each key value pair in this slot, if the key is found, remove that tuple from the list.
        for pair in list2:
            if pair[0] == key:
                temp = (pair[0], pair[1])
                list2.remove(pair)
                return temp
        return None

    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __str__(self):
        return self.toString()

    # The __str__ function and the __repr__ functions have most of their functionality in the toString function for space and complexity's sake.
    def __repr__(self):
        return self.toString()

    # This turns the hash table into a string. Lists the table out, with each line being a slot in the table.
    def toString(self):
        result = ""
        # for each slot in the table
        for slot in range(self.length):
            list2 = self.list1[slot]
            # for each pair in this slot
            for pair in list2:
                result += str(pair[1])
                result += "\n"
        return result
