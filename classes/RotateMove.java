package classes;

import java.util.ArrayList;
import java.util.Collection;

public class RotateMove {
	private int row;
	private int column;
	private int numofrotate;

	public RotateMove() {
	}

	private RotateMove(Tile toriginal, Tile tsoulved) throws Exception {
		if (toriginal.getColumn() == tsoulved.getColumn() && toriginal.getRow() == tsoulved.getRow()) {
			this.column = toriginal.getColumn();
			this.row = toriginal.getRow();
			toriginal = toriginal.clone();
			if (toriginal.hashTile().getCh() == tsoulved.hashTile().getCh()) {
				int r = 0;
				while (toriginal.getCh() != tsoulved.getCh()) {
					r++;
					toriginal = toriginal.rotate();
				}
				r += 4;
				r %= 4;
				this.numofrotate = r;
			} else {
				throw new Exception("tiles from diffrent type");
			}

		} else {
			throw new Exception("tiles from diffrent location");
		}
	}

	public final int getRow() {
		return row;
	}

	public final int getColumn() {
		return column;
	}

	public final int getNumofrotate() {
		return numofrotate;
	}

	@Override
	public String toString() {
		return (row) + "," + (column) + "," + numofrotate;
	}

	/// remove the static and switch:
	/// RotateMove.getRotatesMoves(o,s)
	/// to
	/// (new RotateMove).getRotatesMoves(o,s)
	public static Collection<RotateMove> getRotatesMoves(PipeGameMap pipeGameMapO, PipeGameMap pipeGameMapS)
			throws Exception {
		Collection<RotateMove> arrayList = new ArrayList<>();
		if (pipeGameMapO.getColumns() != pipeGameMapS.getColumns() || pipeGameMapO.getRows() != pipeGameMapS.getRows()
				|| !(pipeGameMapO.hashMap().equals(pipeGameMapS.hashMap()))) {
			throw new Exception("Not the same Bord");
		}
		int columns = pipeGameMapO.getColumns();
		int rows = pipeGameMapO.getRows();

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				try {
					RotateMove rotateMove = new RotateMove(pipeGameMapO.getTile(r, c), pipeGameMapS.getTile(r, c));
					// System.out.println("rotateMove: "+rotateMove.toString());
					if (rotateMove.numofrotate != 0) {
						arrayList.add(rotateMove);
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
		return arrayList;
	}

}
