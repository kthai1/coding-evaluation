package com.aa.act.interview.org;

import java.util.ArrayList;
import java.util.Optional;

public abstract class Organization {

	private Position root;
	private int employeeID = 1;

	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		//your code here
		return recursiveMethod(root, person, title);
	}

	public Optional<Position> recursiveMethod(Position position, Name person, String title) {

		//variables
		Optional<Position> newPos = Optional.empty();

		//2 conditions
		//if title exists/matches the current position, return it
		if (position.getTitle() == title){

			//create employee
			Employee newEmp = new Employee(employeeID, person);

			//checks if employee exists via their title, skips them
			if (!position.isFilled()){

				//Increment Employee's unique ID
				employeeID = employeeID + 1;

				//hire employee, assign the newly created employee to the position
				position.setEmployee(Optional.of(newEmp));
				newPos = Optional.of(position);
			}
		}
		//else if doesn't check if direct report if title exists
		if (position.getDirectReports() != null){

			//loop through the tree of direct reports for the title
//			for (int i = 0; i < root.getDirectReports().size(); i++){
//				System.out.println(root);
//			}
			//loop through the current position's direct reports using a for each loop
			for (Position currPos : position.getDirectReports()){

				//recursive call to loop through the previous position's direct reports in the for loop
				newPos = recursiveMethod(currPos, person, title);
			}
		}
		return newPos;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
