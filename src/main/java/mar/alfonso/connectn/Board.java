package mar.alfonso.connectn;

import java.util.Arrays;

/** Representa un tablero del juego Conecta N
 * @author Alfonso
 *
 */
public class Board {

	private ChipColor[][] chips;

	public Board(int rows, int columns) {
		super();
		chips = new ChipColor[columns][rows];

	}

	public ChipColor[][] getChips() {
		return chips;
	}

	public void setChips(ChipColor[][] chips) {
		this.chips = chips;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(chips);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(chips, other.chips))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Board [chips=" + Arrays.toString(chips) + "]";
	}

	/**
	 * Inserta una {@link ChipColor} en la columna especificada
	 * 
	 * @param chip
	 * @param column
	 */
	public ChipPosition insertFicha(ChipColor chip, int column) {
		int i = getRowGravity(column);
		chips[column][i] = chip;

		return new ChipPosition(i, column);

	}

	private int getRowGravity(int column) {
		int i = 0;
		while (i <= chips[column].length - 1) {
			if (chips[column][i] == null) {
				return i;
			} else {
				i++;
			}
		}
		throw new UnsupportedOperationException("You can not insert in this column!");
	}

}
