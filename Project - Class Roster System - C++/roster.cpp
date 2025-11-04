/*
 * roster.cpp
 *
 *  Created on: Oct 23, 2023
 *      Author: Jeans_Home
 *
 *
 *
 */

#include "roster.h"


Roster::Roster(){
	classRosterArray = std::vector<Student>();
}

Roster::~Roster(){
	//std::cout << "roster dec called ";

}

//sets the instance variables from part D1 and updates the roster.
void Roster::add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress, int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, DegreeProgram degreeprogram){
	std::vector<int> daysPerCourse(3);
	daysPerCourse.at(0) = daysInCourse1;
	daysPerCourse.at(1) = daysInCourse2;
	daysPerCourse.at(2) = daysInCourse3;
	Student person = Student(studentID, firstName, lastName, emailAddress, age, daysPerCourse, degreeprogram);
	classRosterArray.push_back(person);
}


//removes students from the roster by student ID. If the student ID does not exist, the function prints an error message indicating that the student was not found.
void Roster::remove(std::string studentID){
	int i = 0;
	bool found = false;
	while(i<classRosterArray.size()){
		Student temp = classRosterArray.at(i);
		std::string tempID = temp.getID();
		if(tempID.compare(studentID) == 0){
			found = true;
			classRosterArray.erase(classRosterArray.begin() + i);
			i--;
		}
		i++;
	}
	if(!found){
		std::cout << "Error: no student was found to remove with ID: " << studentID << std::endl;
	}
	else{
		std::cout << "Removed student with ID: " << studentID << std::endl;
	}
}

/*
 * that prints a complete tab-separated list of student data in the provided format:
 * A1 [tab] First Name: John [tab] Last Name: Smith [tab] Age: 20 [tab]daysInCourse: {35, 40, 55} Degree Program: Security
 * The printAll() function should loop through all the students in classRosterArray and call the print() function for each student.
 *
 * I am assuming there is supposed to be another tab after the days in course brackets. I am also assuming that you intend for there to be a newline after each student and not all the students on the same line.
 */
void Roster::printAll(){
	int i = 0;
	std::cout << "Class Roster:\n";
	while(i<classRosterArray.size()){
		classRosterArray.at(i).print();
		i++;
	}
}

//that correctly prints a student’s average number of days in the three courses. The student is identified by the studentID parameter.
void Roster::printAverageDaysInCourse(std::string studentID){
	int i = 0;
	bool found = false;
	double average = 0.0;
	while(i<classRosterArray.size() && !found){
		Student temp = classRosterArray.at(i);
		std::string tempID = temp.getID();
		if(tempID.compare(studentID) == 0){
			found = true;
			average = (static_cast<double>(temp.getDaysPerCourse().at(0)) + static_cast<double>(temp.getDaysPerCourse().at(1)) + static_cast<double>(temp.getDaysPerCourse().at(2)))/3.0;
		}
		i++;
	}
	if(!found){
		std::cout << "Error: no student was found to print average days in course with ID: " << studentID << std::endl;
	}
	else {
		std::cout << "Student ID: " << studentID << " spent an average of " << average << " days per course." << std::endl;
	}
}

/*that verifies student email addresses and displays all invalid email addresses to the user.
 *Note: A valid email should include an at sign ('@') and period ('.') and should not include a space (' ').
 *
 *
 * What would you even do if the student emails are invalid? You can't email them to let them know, and your database only has emails as contact info.
 * I would recommend checking if the email is invalid when the email is entered into the database, not scanning for invalid emails after they are already inside the database.
 */
void Roster::printInvalidEmails(){
	std::cout << "Invalid emails: ";
	int y = 0;
	int x = 0;
	bool any = false;
	while(y<classRosterArray.size()){
		std::string temp = classRosterArray.at(y).getEmail();
		bool isValid = true;
		x = 0;
		bool spaceHere = false;
		bool foundAt = false;
		bool foundPeriod = false;
		while(isValid && x < temp.length()){
			char current = temp.at(x);
			x++;

			if(current == ' '){
				spaceHere = true;
			}
			else if(current == '@'){
				foundAt = true;
			}
			else if(current == '.'){
				foundPeriod = true;
			}
		}
		if(spaceHere || !foundAt || !foundPeriod){
			any = true;
			std::cout << temp << "\t";
		}
		y++;
	}
	if(!any) std::cout << "No invalid emails found." << std::endl;
	else std::cout << std::endl;
}

//prints out student information for a degree program specified by an enumerated type.
void Roster::printByDegreeProgram(DegreeProgram degreeProgram){
	int i = 0;
		std::cout << "Students in the ";
		if(degreeProgram == 0){
			std::cout<<"SECURITY";
		}
		else if(degreeProgram == 1){
			std::cout<<"NETWORK";
		}
		else{
			std::cout<<"SOFTWARE";
		}
		std::cout << " program:\n";
		while(i<classRosterArray.size()){
			if(classRosterArray.at(i).getDegreeType() == degreeProgram){
				classRosterArray.at(i).print();
			}
			i++;
		}
}

//see comments on lines 117-127 of main.cpp
std::string Roster::idAtIndex(int i){
	return classRosterArray.at(i).getID();
}
