package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author user
 *
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = 42L;

	public enum Direction {
		up, down, left, right
	};

	private char ch;
	private int row;
	private int column;

	public Tile(char ch, int row, int column) {
		this.ch = ch;
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	@Override
	public String toString() {
		return ch + "";
	}

	protected Tile clone() {

		return new Tile(this.getCh(), row, column);
	}

	// used in PipeGameMapSearchable's getAllPossibleStates() method
	public double getDistance(Tile tile) {
		return getDistance(tile.row, tile.column);
	}

	// calculate Euclidean distance
	public double getDistance(int row, int column) {
		return Math.hypot(row - this.getRow(), column - this.getColumn());
	}

	public boolean equals(Tile tile) {
		return this.getCh() == tile.getCh() && this.getColumn() == tile.getColumn() && this.getRow() == tile.getRow();
	}

	public boolean isStart() {
		return getCh() == 's';
	}

	public boolean isGoal() {
		return getCh() == 'g';
	}

	public boolean isBlank() {
		return getCh() == ' ';
	}

	public boolean isStraightLine() {
		return getCh() == '-' || getCh() == '|';
	}

	public boolean isRightAngle() {
		return getCh() == '7' || getCh() == 'J' || getCh() == 'L' || getCh() == 'F';
	}

	@Override
	public boolean equals(Object obj) { // we override Object's equals method
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (!(obj instanceof Tile))) {
			return false;
		}

		return this.equals(((Tile) obj));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ch;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}
	
	
	/**
	 * @return the default character position in a tile
	 */
	public Tile hashTile() {
		char hashch = getCh();
		switch (this.ch) {
			case '-':
			case '|': {
				hashch = '|';
				break;
			}
			case '7':
			case 'J':
			case 'L':
			case 'F': {
				hashch = 'L';
				break;
			}
			default: {
				break;
			}
		}
		Tile tile = this.clone();
		tile.setCh(hashch);
		return tile;
	}
	
	//Ensure we don't over-rotate the character in PipeGameMapSearchable getAllPossibleMovesHelper()
	public int numofrotates() {
		int num = 1;
		switch (this.ch) {
		case '-':
		case '|': {
			num = 2;
			break;
		}
		case '7':
		case 'J':
		case 'L':
		case 'F': {
			num = 4;
			break;
		}
		case ' ':
		case 's':
		case 'g': {
			num = 0;
			break;
		}
		default: {
			break;
		}
		}
		return num;
	}

	public Tile rotate() {
		switch (this.ch) {
			case '-': {
				this.ch = '|';
				break;
			}
			case '|': {
				this.ch = '-';
				break;
			}
			case '7': {
				this.ch = 'J';
				break;
			}
			case 'J': {
				this.ch = 'L';
				break;
			}
			case 'L': {
				this.ch = 'F';
				break;
			}
			case 'F': {
				this.ch = '7';
				break;
			}
			default: {
				break;
			}
		}
		return this;
	}
	
	// direction of tiles current tile can connect to 
	public Collection<Direction> getDirections() {
		Collection<Direction> directions = new ArrayList<Direction>();
		switch (this.ch) {
			case '-': {
				directions.add(Direction.left);
				directions.add(Direction.right);
				break;
			}
			case '|': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				break;
			}
			case '7': {
				directions.add(Direction.left);
				directions.add(Direction.down);
				break;
			}
			case 'J': {
				directions.add(Direction.left);
				directions.add(Direction.up);
				break;
			}
			case 'L': {
				directions.add(Direction.up);
				directions.add(Direction.right);
				break;
			}
			case 'F': {
				directions.add(Direction.down);
				directions.add(Direction.right);
				break;
			}
			case 's': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				directions.add(Direction.right);
				directions.add(Direction.left);
				break;
			}
			case 'g': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				directions.add(Direction.right);
				directions.add(Direction.left);
				break;
			}
		}
		return directions;
	}
}
