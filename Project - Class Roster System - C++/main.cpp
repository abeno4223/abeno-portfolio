/*
 * main.cpp
 *
 *  Created on: Oct 23, 2023
 *      Author: Jeans_Home
 */
#include "roster.h"
#include <map>
using namespace std;




int main() {
	//the textbook recommends we use vectors instead of arrays but since this is how the rubric wants it I will leave it. I am also assuming I am not supposed to change my ID here to my actual ID?
	//either way if you change the size of this it's going to mess up the program and I can't fix that without going against the rubric.
	const string studentData[] = {
			"A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
			"A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
			"A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
			"A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
			"A5,Alex,Beno.,abeno4@wgu.edu,23,42,57,53,SOFTWARE" //you asked for tab separated. i added a . to my last name so it doesn't mess up the formatting when the last name only has 4 characters.
	};
	//Demonstrate the program’s required functionality by adding a main() function in main.cpp, which will contain the required function calls to achieve the following results:
	//1.  Print out to the screen, via your application, the course title, the programming language used, your WGU student ID, and your name.
	cout << "Course Title: Scripting and Programming - Applications - C867\nProgramming language used: C++. Were we allowed to use a different language? This question implies it but the rubric says C++.\nStudent ID: 011095454\nName: Alex Beno\n\n";
	//2.  Create an instance of the Roster class called classRoster.
	Roster classRoster;
	//3.  Add each student to classRoster.
	int i = 0;
	int x;
	int y;
	while(i<5){
		x = 0;
		y = 0;
		string temp = "";
		string id,firstName,lastName,email;
		int age;
		std::vector<int> daysPerCourse(3);
		DegreeProgram degreeType;
		bool comma = false;
		while(!comma && x < studentData[i].length()){
			char current = studentData[i].at(x);
			if(current != ','){
				temp += current;
			}
			if(current == ',' || x == studentData[i].length()-1){
				if(y==0){
					id = temp;
				}
				else if(y==1){
					firstName = temp;
				}
				else if(y==2){
					lastName = temp;
				}
				else if(y==3){
					email = temp;
				}
				else if(y==4){
					//cout << "age: " << temp << " ";
					age = stoi(temp);
				}
				else if(y == 5){
					daysPerCourse.at(0) = stoi(temp);
				}
				else if(y == 6){
					daysPerCourse.at(1) = stoi(temp);
				}
				else if(y == 7){
					daysPerCourse.at(2) = stoi(temp);
				}
				else{
					if(temp.compare("SECURITY") == 0){
						degreeType = SECURITY;
					}
					else if(temp.compare("SOFTWARE") == 0){
						degreeType = SOFTWARE;
					}
					else{
						degreeType = NETWORK;
					}
				}
				temp = "";
				y++;
			}

			x++;

		}
		/*
		cout << "ID: "<<id<<" Name: "<<firstName<<" "<<lastName<<" Email: "<<email<<" Age: "<<age<<" Days Per Course: "<<daysPerCourse.at(0)<<". "<<daysPerCourse.at(1)<<". "<<daysPerCourse.at(2)<<" Degree Type: ";
		if(degreeType == 0){
			cout<<"SECURITY"<<endl;
		}
		else if(degreeType == 1){
			cout<<"NETWORK"<<endl;
		}
		else{
			cout<<"SOFTWARE"<<endl;
		}
		*/
		classRoster.add(id, firstName, lastName, email, age, daysPerCourse.at(0), daysPerCourse.at(1), daysPerCourse.at(2), degreeType);
		i++;
	}
	//4.  Convert the following pseudo code to complete the rest of the  main() function:

	classRoster.printAll(); //you asked for tab separated. i added a . to my last name so it doesn't mess up the formatting when the last name only has 4 characters.

	classRoster.printInvalidEmails();



	//loop through classRosterArray and for each element:
	i = 0;
	while(i<5){
		/*
		 * Here's how to do this the easy way:
		 * classRoster.printAverageDaysInCourse("A1");
		 * classRoster.printAverageDaysInCourse("A2");
		 * classRoster.printAverageDaysInCourse("A3");
		 * classRoster.printAverageDaysInCourse("A4");
		 * classRoster.printAverageDaysInCourse("A5");
		 *
		 * However, the rubric specifically asks for me to iterate through "classRosterArray", which the above doesn't satisfy. Also, you shouldn't even be able to access classRosterArray outside of the roster class.
		 * Therefore, I will be adding a function to the roster class specifically to pull IDs out of the roster so I can immediately put them back into the roster get average days in course:
		 */
		classRoster.printAverageDaysInCourse(classRoster.idAtIndex(i));
		i++;
	}








	classRoster.printByDegreeProgram(SOFTWARE);

	classRoster.remove("A3");

	classRoster.printAll();

	classRoster.remove("A3");
	//expected: the above line should print a message saying such a student with this ID was not found.

	//5.  Implement the destructor to release the memory that was allocated dynamically in Roster.
	//i put this into the roster class itself. I don't know why you are asking for it in main.cpp




   return 0;
}



