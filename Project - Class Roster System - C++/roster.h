/*
 * roster.h
 *
 *  Created on: Oct 23, 2023
 *      Author: Jeans_Home
 */


#include "student.h"

class Roster {
	public:
		Roster();
		~Roster();
		//3.  Define the following functions:
		void add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress, int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, DegreeProgram degreeprogram);
		void remove(std::string studentID);
		void printAll();
		void printAverageDaysInCourse(std::string studentID);
		void printInvalidEmails();
		void printByDegreeProgram(DegreeProgram degreeProgram);
		std::string idAtIndex(int i);
	private:
		//1.  Create an array of pointers, classRosterArray, to hold the data provided in the “studentData Table.”
		std::vector<Student> classRosterArray;
};


