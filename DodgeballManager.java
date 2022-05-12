/* Thomas Kleinknecht
 * Assignment 2
 * November 4, 2021
 * Description: This program is a DodgeballManager object class which manages a game of 
 * "Circle Dodgeball". Each object created holds two fields, each containing the first 
 * node of a linked list of DodgeballNodes, one list representing the throwers and one
 * for the dodgers. DodgeballManager keeps track of the role of each player, their 
 * position in the room, and their score. From this their are many methods, for hits, 
 * dodges, printing the information which is being held, detrmining the winner, and more.
 * Input: There is no input to this program as it is an object class.
 * Output: There is no output from this program as it is an object class.
 */

import java.io.PrintStream;
import java.util.List;

public class DodgeballManager {
	DodgeballNode throwerFirstNode = null;
	DodgeballNode dodgerFirstNode = null;

	/* constructor which takes two String lists as inputs, one for the dodger and
	 * one for the thrower names and declares these names into a dodgeballNode list
	 * with each score initialized to zero */
	public DodgeballManager(List<String> initialThrowers, List<String> initialDodgers) {

		/* calling the transferListToDodgeball method which takes the names in the list
		 * parameters passed to the constructor and makes a linked list of
		 * DodgeballNodes with them, all initialized to a score of zero */
		throwerFirstNode = transferListToDodgeball(throwerFirstNode, initialThrowers);
		dodgerFirstNode = transferListToDodgeball(dodgerFirstNode, initialDodgers);
	}

	/* method prints a list of all throwers with their respective scores on one line 
	 * to the passed PrintStream object */
	public void printThrowers(PrintStream stream) {

		/* calling the printDodgeballList method which will print the passed thrower
		 * list to the PrintStream object */
		printDodgeballList(throwerFirstNode, stream);
	}

	/* method prints a list of all dodgers with their respective scores on one line
	 * to the passed PrintStream object */
	public void printDodgers(PrintStream stream) {

		/* calling the printDodgeballList method which will print the passed dodger list
		 * to the PrintStream object also passed */
		printDodgeballList(dodgerFirstNode, stream);
	}

	/* method prints the winner to the PrintStream object passed, or throws
	 * IllegalStateException if there is more than one player with the "winning"
	 * score */
	public void printWinner(PrintStream stream) {

		// using getMaximumScore method to determine the winning score
		int max = this.getMaximumScore();

		/* initializing our winners counter variable to zero, this will keep track of
		 * the number of winners to know when to throw exception */
		int winners = 0;

		/* using checkWinnersInList to ensure that there is a total of one winner with
		 * the passed max score across both the throwers and dodgers lists */
		checkWinnersInList(dodgerFirstNode, max, checkWinnersInList(throwerFirstNode, max, winners));

		/* using the findWinner method on both the thrower and dodger list which returns
		 * either empty string if winner is not in list or string with winner and proper
		 * output statement, then printing to PrintStrean object */
		stream.print(findWinner(throwerFirstNode, max) + findWinner(dodgerFirstNode, max));
	}

	/* method returns a boolean value representing whether or not the string
	 * parameter "name" is in the list of throwers */
	public boolean throwersContains(String name) {

		/* returns the result of a more general listContains method which accepts a
		 * DodgeballNode, the first thrower node, and string "name" parameter and
		 * returns whether or not the name is in the linked list associated with the
		 * passed node */
		return listContains(throwerFirstNode, name);
	}

	// method returns a boolean value representing whether or not the string
	// parameter "name" is in the list of dodgers
	public boolean dodgersContains(String name) {

		/*
		 * returns the result of a more general listContains method which accepts a
		 * DodgeballNode, the first dodger node, and string "name" parameter and returns
		 * whether or not the name is in the linked list associated with the passed node
		 */
		return listContains(dodgerFirstNode, name);
	}

	/*
	 * this method increases the score of the thrower with the name of the first
	 * string passed, and rearranges the thrower and dodger linked lists so that the
	 * dodger and thrower switch places
	 */
	public void hit(String throwerName, String dodgerName) {

		// running checkArguments method which ensures that the two string parameters
		// passed are in their respective lists, and not null or empty
		checkArguments(throwerName, dodgerName);

		// using the addPoint method to add a point to the thrower who hit the dodger
		throwerFirstNode = addPoint(throwerFirstNode, throwerName);

		// index of thrower which we need to change
		int t = getIndex(throwerFirstNode, throwerName);

		// index of dodger which we need to change
		int d = getIndex(dodgerFirstNode, dodgerName);

		// this temp variable points to the thrower which we need to move over
		DodgeballNode thrower = throwerFirstNode;
		for (int i = 0; i < t; i++) {
			thrower = thrower.next;
		}

		// points to thrower in the list after the one we are moving which subsequently
		// points to the rest of the throwers
		DodgeballNode tTemp2 = thrower.next;

		// points to dodger which we are moving over
		DodgeballNode dodger = dodgerFirstNode;
		for (int i = 0; i < d; i++) {
			dodger = dodger.next;
		}

		// points to dodger in the list after the one we are moving which subsequently
		// points to the rest of the dodgers
		DodgeballNode dTemp2 = dodger.next;

		// checks whether or not thrower is in first position, if it isn't we need to
		// make another temp variable pointing to node before thrower and point to
		// dodger we are moving over
		if (t != 0) {
			DodgeballNode tTemp1 = throwerFirstNode;
			for (int i = 0; i < t - 1; i++) {
				tTemp1 = tTemp1.next;
			}
			tTemp1.next = dodger;

			// If it is in first position, we need to point the start of the linked list to
			// the dodger which we are moving over
		} else {
			throwerFirstNode = dodger;
		}

		// checks whether or not dodger is in first position, if it isn't we need to
		// make another temp variable pointing to node before dodger and point to
		// thrower we are moving over
		if (d != 0) {
			DodgeballNode dTemp1 = dodgerFirstNode;
			for (int i = 0; i < d - 1; i++) {
				dTemp1 = dTemp1.next;
			}
			dTemp1.next = thrower;

			// If it is in first position, we need to point the start of the linked list to
			// the thrower which we are moving over
		} else {
			dodgerFirstNode = thrower;
		}

		// Appending the rest of the throwers list to the dodger which we moved over, in
		// the case of the thrower being at the end, this will point the dodger to null
		dodger.next = tTemp2;

		// Appending the rest of the dodgers list to the thrower we moved over, in the
		// case of the dodger being at the end, this will point the thrower to null
		thrower.next = dTemp2;
	}

	// method takes two strings as input for thrower name and dodger name and
	// increases the score of the dodger
	public void dodge(String throwerName, String dodgerName) {

		// running checkArguments method which ensures that the two string parameters
		// passed are in their respective lists, and not null or empty
		checkArguments(throwerName, dodgerName);

		// using the addPoint method to add a point to the dodger who dodged the throw
		dodgerFirstNode = addPoint(dodgerFirstNode, dodgerName);
	}

	// this is an instance method which returns the maximum score amongst all
	// players in the DodgeballManager object it is called on
	public int getMaximumScore() {
		// calls on maxScoreInList method which finds max score in the thrower list
		// passed in, assigns to max integer variable
		int max = maxScoreInList(this.throwerFirstNode);

		// compares the max score we have already found of the thrower list with the max
		// score in the dodger list, the greater one is set to max variable's value
		if (max < maxScoreInList(this.dodgerFirstNode)) {
			max = maxScoreInList(this.dodgerFirstNode);
		}

		return max;
	}

	// did not make an attempt at this method
	public void printSortedScores(PrintStream stream) {

	}

	// takes DodgeballNode and string list parameters, and creates linked list of
	// dodgeball nodes, each set to a name in the string list in the same order
	// inputted and declared to score of 0
	public DodgeballNode transferListToDodgeball(DodgeballNode first, List<String> input) {

		// ensuring the string list is not empty or null, if so, throwing exception
		if (input == null || input.size() == 0) {
			throw new IllegalArgumentException();
		} else {

			// fenceposting by creating the last dodgeballNode in the list, and setting it
			// to the last name
			DodgeballNode lastThrow = new DodgeballNode(input.get(input.size() - 1));

			// setting the dodgeballNode parameter equal to this node, as if there is only
			// one name in list this is the first and last node
			first = lastThrow;

			/*
			 * checking if there is more than one name in list, if so, adding each node
			 * starting at the end which we have already created and working back to the
			 * front, each time setting the dodgeballNode parameter "first" equal to the
			 * front-most node, and setting the front-most node to point to the rest
			 */
			if (input.size() > 1) {
				for (int i = input.size() - 2; i >= 0; i--) {
					DodgeballNode tempDodge = new DodgeballNode(input.get(i), first);
					first = tempDodge;
				}
			}
		}

		// returning the DodgeballNode parameter which now points to our list of
		// declared dodgeballNodes with the names
		return first;
	}

	// Prints all terms and scores of dodgers or throwers corresponding to the
	// dodgeballNode parameter passed to the PrintStream object passed
	public void printDodgeballList(DodgeballNode first, PrintStream stream) {

		// creating temp dodgeballNode variable to parse through the dodgeballNode
		// linked list we need to print
		DodgeballNode temp = first;

		// fenceposting the first node, ensuring there is a node, and then printing it
		// with no comma in case there is only one before moving the temp to the next
		// node
		if (temp != null) {
			stream.print(temp.name + " " + temp.score);
			temp = temp.next;
		}

		// now that the first node has been printed, parsing through the rest of the
		// nodes and printing them with a preceding comma so that the list is formatted
		// properly
		while (temp != null) {
			stream.print(", " + temp.name + " " + temp.score);
			temp = temp.next;
		}
	}

	// returns integer value of maximum score in a list of dodgeball nodes of
	// throwers or dodgers, denoted by "first" parameter
	public int maxScoreInList(DodgeballNode first) {

		// initializing integer variable max to zero, it will store max score which we
		// will return
		int max = 0;

		// creating temp dodgeballNode variable to parse through the dodgeballNode
		// linked list we need to find max in
		DodgeballNode temp = first;

		// while loop goes through each node, comparing its score to the existing max
		// value, which starts at zero, and declaring max to equal its score if it is
		// greater than the current max
		while (temp != null) {
			if (temp.score > max) {
				max = temp.score;
			}
			temp = temp.next;
		}

		// returns the max value found by above while loop process
		return max;
	}

	/*
	 * method counts the amount of dodgers or throwers in a DodgeballNode linked
	 * list which have the winning score, and updates the winners paramater which is
	 * passed in, if this goes over 1, IllegalStateException is thrown, if not, this
	 * value is returned
	 */
	public int checkWinnersInList(DodgeballNode first, int score, int winners) {

		// creating temp dodgeballNode variable to parsing through each node in list,
		// updating the winners tally by one if the score at that node equals the score
		// parameter passed in
		DodgeballNode temp = first;
		while (temp != null) {
			if (temp.score == score) {
				winners++;
			}

			// checking on each iteration of the while loop whether or not the winners tally
			// is now greater than 1 to throw exception
			if (winners > 1) {
				throw new IllegalStateException();
			}
			temp = temp.next;
		}

		// if no exception has been thrown, the winners tally will be returned
		return winners;
	}

	/*
	 * assuming that there is only one thrower or dodger in the linked list which is
	 * passed in which has the winning score that is passed in, returns the required
	 * sentence explaining the winner or empty string if no one has that score in
	 * the passed list
	 */
	public String findWinner(DodgeballNode first, int score) {

		// initializing temp dodgeball node to use to iterate through list and winner
		// string to empty string in case winner is not found
		DodgeballNode temp = first;
		String winner = "";

		// iterating through each node in the linked list to find the one with the
		// winning score, if found string winner is set to necessary statement
		while (temp != null) {
			if (temp.score == score) {
				winner = "The winner is " + temp.name + " with " + temp.score + " points";
			}
			temp = temp.next;
		}

		// returns winner, which will be empty string if no winner was found, or the
		// proper winner declaration sentence if it was
		return winner;
	}

	// Checks if the given linked list of DodgeballNodes contains the given name,
	// ignoring case, and returns boolean value if true or false
	public boolean listContains(DodgeballNode first, String name) {

		// initializing temp dodgeball node to use to iterate through list and boolean
		// result variable to false which will be returned if the name is not found
		DodgeballNode temp = first;
		boolean result = false;

		// iterating through each element in the linked list and comparing teh string
		// parameter with the name at that node, if they are the same, changes result
		// variable to true
		while (temp != null) {
			if (temp.name.equalsIgnoreCase(name)) {
				result = true;
			}
			temp = temp.next;
		}

		// returns result of true if the name is in the list or false if not
		return result;
	}

	// checks a thrower and dodger name string input to ensure that they are in the
	// thrower and dodger lists, and not empty or nor null, if so, throws exception
	public void checkArguments(String throwerName, String dodgerName) {
		if (!throwersContains(throwerName) || !dodgersContains(dodgerName) || throwerName.isEmpty()
				|| dodgerName.isEmpty() || throwerName == null || dodgerName == null) {
			throw new IllegalArgumentException();
		}
	}

	// method adds a point to string name which is passed in, in the given dodgers
	// or throwers linked list given by the first node of this list passed as
	// "first" parameter
	public DodgeballNode addPoint(DodgeballNode first, String name) {

		// creating temp dodgeballNode variable to parsing through each node in list,
		// looking for the node in which the name matches the name parameter, and adding
		// a point to the score at this node
		DodgeballNode temp = first;
		while (temp != null) {
			if (temp.name.equalsIgnoreCase(name)) {
				temp.score++;
			}
			temp = temp.next;
		}

		// returns the dodgeballNode pointing to the linked list with the now updated
		// score
		return first;
	}

	// takes DodgeballNode and string parameters and returns the index in the linked
	// list which the node lies that contains the given name
	public int getIndex(DodgeballNode first, String name) {

		// initializing the integer index variable which will be returned and the temp
		// dodgeballNode variable which will parse through the list
		int index = 0;
		DodgeballNode temp = first;

		// increases index by one and moves to the next node as long as it hasn't found
		// the node with the name parameter, once it has, it leaves while loop and
		// returns that index
		while (!temp.name.equalsIgnoreCase(name)) {
			temp = temp.next;
			index++;
		}
		return index;
	}
}