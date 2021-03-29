package project;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static Scanner scan = new Scanner(System.in); //Used to take in commands
	static ArrayList<Robot> robots = new ArrayList<Robot>(); //Used to store all robots in the arena
	enum Direction {N, S, E, W}; //Used for the facing direction of robots
	
	//Keeps track of the arena size
	static int arenaSizeX; 
	static int arenaSizeY;
	
	int getArenaX () {
		return arenaSizeX;
	}
	
	int getArenaY () {
		return arenaSizeY;
	}
	
	//Makes sure entries that need to be integers can be parsed
	private static boolean CheckIntegersInString(String[] entries) {
		boolean isInteger = false;
		for (String entry : entries) {
			isInteger = entry.chars().allMatch(Character::isDigit); 
		}
		return isInteger;
	}
	
	//Same as above except this is to only check certain entries from the command line
	private static boolean CheckIntegersInString(String[] entries, int amountToCheck) {
		String[] entriesToCheck = new String[amountToCheck];
		for (int i = 0; i < amountToCheck; i++) {
			entriesToCheck[i] = entries[i];
		}
		return CheckIntegersInString(entriesToCheck);
	}
	
	//Make necessary checks and all cases to ensure a valid arena size has been entered
	private static boolean EnterArenaSize() {
		String arenaSizeString = scan.nextLine();
		String sizes[] = arenaSizeString.split(" ");
		if (sizes.length != 2) {
			System.out.println( "Please input 2 numbers, an X and a Y, for the size of the arena" );
			return false;
		}
		boolean inputIntegers = CheckIntegersInString(sizes);
		if (inputIntegers == false) {
			System.out.println( "Please make sure coordinates entered are numbers, with only 1 space between them" );
			return false;
		} else {
			arenaSizeX = Integer.parseInt(sizes[0]);
			arenaSizeY = Integer.parseInt(sizes[1]);
			return true;
		}
		
	}
	
	//Make necessary checks and all cases to ensure that valid robot information has been entered
	private static boolean EnterRobotInfo(int robotNumber) {
		String robotInfoString = scan.nextLine();
		String robotInfo[] = robotInfoString.split(" ");
		if (robotInfo.length != 3) {
			System.out.println( "Please input 3 entries, an X coordinate, a Y coordinate, and a facing direction" );
			return false;
		}
		boolean inputIntegers = CheckIntegersInString(robotInfo, 2);
		if (!inputIntegers) {
			System.out.println( "Please make sure coordinates entered are numbers, with only 1 space between them" );
			return false;
		}
		
		int robotX = Integer.parseInt(robotInfo[0]);
		int robotY = Integer.parseInt(robotInfo[1]);
		Direction initFace;
		
		if (robotX > arenaSizeX || robotX < 0 || robotY > arenaSizeY || robotY < 0) {
			System.out.println( "Invalid robot starting position. Coordinates are outside of the listed arena size" );
			return false;
		}
		try {
			robotInfo[2] = robotInfo[2].toUpperCase();
			initFace = Direction.valueOf(robotInfo[2]);
			
		} catch (Exception e) {
			System.out.println( "Invalid robot direction. Initial direction of the robot has to be one of N, S, E or W" );
			return false;
		}
		
		robots.get(robotNumber).setPosition(robotX, robotY);
		robots.get(robotNumber).setFacing(initFace);
		System.out.println( "Robot successfully placed at coordinates " + robotX + " " + robotY );
		return true;
	}
	
	//Make necessary checks and all cases to ensure that valid instructions for the robot have been entered
	private static void EnterRobotInstructions(int robotNumber) {
		String instructionString = scan.nextLine().toUpperCase();
		char[] instructions = instructionString.toCharArray();
		
		for (char instruction : instructions) {
			switch (instruction) {
			case 'M':
				Direction robotFacing = robots.get(robotNumber).getFacing();
				if (robotFacing==Direction.N) {
					if (robots.get(robotNumber).getPosY() == arenaSizeY) {
						System.out.println( "Reached arena north limit, cannot move North. Ignoring instruction" );
						break;
					}
				}
				else if (robotFacing==Direction.S) {
					if (robots.get(robotNumber).getPosY() == 0) {
						System.out.println( "Reached arena south limit, cannot move South. Ignoring instruction" );
						break;
					}
				}
				else if (robotFacing==Direction.E) {
					if (robots.get(robotNumber).getPosX() == arenaSizeX) {
						System.out.println( "Reached arena east limit, cannot move East. Ignoring instruction" );
						break;
					}
				}
				else if (robotFacing==Direction.W) {
					if (robots.get(robotNumber).getPosX() == 0) {
						System.out.println( "Reached arena west limit, cannot move West. Ignoring instruction" );
						break;
					}
				}
				robots.get(robotNumber).move();
				System.out.println( "Robot now at coordinates " + robots.get(robotNumber).getPosX() + " " + robots.get(robotNumber).getPosY() );
				break;
			case 'L':
				robots.get(robotNumber).turnLeft();
				System.out.println( "Robot now facing " + robots.get(robotNumber).getFacing() );
				break;
			case 'R':
				robots.get(robotNumber).turnRight();
				System.out.println( "Robot now facing " + robots.get(robotNumber).getFacing() );
				break;
			default:
				System.out.println( "Input '" + instruction + "' Is not a recognised instruction. Ignoring instruction" );
				break;
			}
		}
		
	}
	
	static public void main(String[] args) {
		
		System.out.println( "Input arena size, with a space between the X and the Y coordinate: " );
		boolean acceptArenaSize = false;
		while (acceptArenaSize != true) {
			acceptArenaSize = EnterArenaSize();
		}
		
		System.out.println( "Arena size set to X:" + arenaSizeX + " Y:" + arenaSizeY );
		
		robots.add(new Robot());
		robots.add(new Robot());
		
		for (int i = 0; i < robots.size(); i++) {
			boolean coordsValid = false;
			while (coordsValid == false) {
				System.out.println( "Input robot starting position and initial facing direction, with a space between each position and direction: " );
				coordsValid = EnterRobotInfo(i);
			}
			System.out.println( "Input robot instructions, where L is turn left, R is turn right and M is move forwards: " );
			EnterRobotInstructions(i);
		}
		
		//final output
		System.out.println( );
		System.out.println( "Final coordinates and heading of each robot: " );
		for (Robot robot : robots) {
			System.out.println( robot.getPosX() + " " + robot.getPosY() + " " + robot.getFacing() );
		}
		
	}
}
