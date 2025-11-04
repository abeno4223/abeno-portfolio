/*
 * student.cpp
 *
 *  Created on: Oct 23, 2023
 *      Author: Jeans_Home
 */


#include "student.h"





//Create each of the following functions in the Student class:
//a.  an accessor (i.e., getter) for each instance variable from part D1
	std::string Student::getID(){
		return studentID;
	}
	std::string Student::getFirstName(){
		return firstName;
	}
	std::string Student::getLastName(){
		return lastName;
	}
	std::string Student::getEmail(){
		return emailAddress;
	}
	int Student::getAge(){
		return age;
	}
	std::vector<int> Student::getDaysPerCourse(){
		return daysPerCourse;
	}
	DegreeProgram Student::getDegreeType(){
		return degreeType;
	}

	//b.  a mutator (i.e., setter) for each instance variable from part D1
	void Student::setID(std::string newID){
		studentID = newID;
	}
	void Student::setFirstName(std::string newFirstName){
		firstName = newFirstName;
	}
	void Student::setLastName(std::string newLastName){
		lastName = newLastName;
	}
	void Student::setEmail(std::string newEmail){
		emailAddress = newEmail;
	}
	void Student::setAge(int newAge){
		age = newAge;
	}
	void Student::setDaysPerCourse(std::vector<int> newDaysPerCourse){
		daysPerCourse.at(0) = newDaysPerCourse.at(0);
		daysPerCourse.at(1) = newDaysPerCourse.at(1);
		daysPerCourse.at(2) = newDaysPerCourse.at(2);
	}
	void Student::setDegreeType(DegreeProgram newDegreeType){
		degreeType = newDegreeType;
	}
	//c.  All external access and changes to any instance variables of the Student class must be done using accessor and mutator functions.
	//d.  constructor using all of the input parameters provided in the table
	Student::Student(std::string newID, std::string newFirstName, std::string newLastName, std::string newEmail, int newAge, std::vector<int> newDaysPerCourse, DegreeProgram newDegreeType){
		studentID = newID;
		firstName = newFirstName;
		lastName = newLastName;
		emailAddress = newEmail;
		age = newAge;
		daysPerCourse = std::vector<int>(3);
		daysPerCourse.at(0) = newDaysPerCourse.at(0);
		daysPerCourse.at(1) = newDaysPerCourse.at(1);
		daysPerCourse.at(2) = newDaysPerCourse.at(2);
		degreeType = newDegreeType;
	}
	Student::~Student(){
		//std::cout << "student dec called ";
	}
	//e.  print() to print specific student data
	void Student::print(){
		std::cout << studentID << "\t" <<
				"First Name: " << firstName << "\t" <<
				"Last Name: " << lastName << "\t" <<
				"Age: " << age << "\t" <<
				"daysInCourse : [" << daysPerCourse.at(0) << ", " << daysPerCourse.at(1) << ", " << daysPerCourse.at(2) << "]\t" <<
				"Degree Program: ";
		if(degreeType == 0){
			std::cout<<"SECURITY"<<std::endl;
		}
		else if(degreeType == 1){
			std::cout<<"NETWORK"<<std::endl;
		}
		else{
			std::cout<<"SOFTWARE"<<std::endl;
		}
	}

