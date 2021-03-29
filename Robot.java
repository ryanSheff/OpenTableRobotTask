package project;
import project.Main.Direction;

//Used to keep track of all necessary information for each robot
public class Robot {
	int posX;
	int posY;
	
	Direction facing;
	
	void setPosition(int newPosX, int newPosY) {
		posX = newPosX;
		posY = newPosY;
	}
	
	void setPosX(int newPosX) {
		posX = newPosX;
	}
	int getPosX() {
		return posX;
	}
	
	void setPosY(int newPosY) {
		posY = newPosY;
	}
	int getPosY() {
		return posY;
	}
	
	void setFacing(Direction newFacing) {
		facing = newFacing;
	}
	Direction getFacing() {
		return facing;
	}
	
	//Checks for arena size are done in Main, so this function can just move the robot
	void move() {
		switch (facing) {
		case N:
			posY++;
			break;
		case S:
			posY--;
			break;
		case E:
			posX++;
			break;
		case W:
			posX--;
			break;
		}
	}
	
	void turnLeft() {
		switch (facing) {
		case N:
			facing = Direction.W;
			break;
		case S:
			facing = Direction.E;
			break;
		case E:
			facing = Direction.N;
			break;
		case W:
			facing = Direction.S;
			break;
		}
	}
	
	void turnRight() {
		switch (facing) {
		case N:
			facing = Direction.E;
			break;
		case S:
			facing = Direction.W;
			break;
		case E:
			facing = Direction.S;
			break;
		case W:
			facing = Direction.N;
			break;
		}
	}
}
