/*
 * student.h
 *
 *  Created on: Oct 23, 2023
 *      Author: Jeans_Home
 */

#include "degree.h"
#include <string>
#include <vector>
#include <iostream>

class Student {
	public:
	//Create each of the following functions in the Student class:
	//a.  an accessor (i.e., getter) for each instance variable from part D1
		std::string getID();
		std::string getFirstName();
		std::string getLastName();
		std::string getEmail();
		int getAge();
		std::vector<int> getDaysPerCourse();
		DegreeProgram getDegreeType();
	//b.  a mutator (i.e., setter) for each instance variable from part D1
		void setID(std::string newID);
		void setFirstName(std::string newFirstName);
		void setLastName(std::string newLastName);
		void setEmail(std::string newEmail);
		void setAge(int newAge);
		void setDaysPerCourse(std::vector<int> newDaysPerCourse);
		void setDegreeType(DegreeProgram newDegreeType);
	//c.  All external access and changes to any instance variables of the Student class must be done using accessor and mutator functions.
	//d.  constructor using all of the input parameters provided in the table
		Student(std::string newID, std::string newFirstName, std::string newLastName, std::string newEmail, int newAge, std::vector<int> newDaysPerCourse, DegreeProgram newDegreeType);
		~Student();
	//e.  print() to print specific student data
		void print();
	//Create the class Student which includes each of the following variables:
	private:
		std::string studentID;
		std::string firstName;
		std::string lastName;
		std::string emailAddress;
		int age;
		std::vector<int> daysPerCourse;
		DegreeProgram degreeType;
};

