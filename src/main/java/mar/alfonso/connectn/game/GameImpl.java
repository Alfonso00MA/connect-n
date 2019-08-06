package mar.alfonso.connectn.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mar.alfonso.connectn.Board;
import mar.alfonso.connectn.ChipColor;
import mar.alfonso.connectn.ChipPosition;
import mar.alfonso.connectn.GameResult;
import mar.alfonso.connectn.GameTest;

public class GameImpl implements Game {

	private Board board;
	private int n;

	private ChipPosition lastChipPositionAdded;
	private ChipColor lastChipAdded;

	/**
	 * @param rows
	 *            the number of rows in the board
	 * @param columns
	 *            the number of columns in the board
	 * @param n
	 *            the number of connected chips required to win the game
	 */
	public GameImpl(final int rows, final int columns, final int n) {
		this.board = new Board(rows, columns);
		this.n = n;
	}

	@Override
	public void putChip(final ChipColor chip, final int column) {
		ChipPosition chipPosition = this.getBoard().insertFicha(chip, column);

		this.setLastChipPositionAdded(chipPosition);
		this.setLastChipAdded(chip);

	}

	@Override
	public GameResult getGameResult() {
		return checkStreaksForWin();
	}

	/**
	 * Crea cuatro Streaks en las cuatro direciones posibles: horizontal vertical y
	 * dos diagonales a partir de la ultima ficha introducida en el tablero. Evalua
	 * esos cuatro Streaks para ver si se ha producido un ganador
	 * 
	 * @return
	 */
	private GameResult checkStreaksForWin() {
		int column = this.getLastChipPositionAdded().getColumn();
		int row = this.getLastChipPositionAdded().getRow();
		int newN = this.getN() - 1;

		GameResult toReturn = null;
		// diagonales
		ChipPosition[] streakDiagonalA = GameTest.buildStreak(new ChipPosition(row - newN, column - newN), new ChipPosition(row + newN, column + newN));
		toReturn = checkStreakForWin(streakDiagonalA);
		if (toReturn != null) {
			return toReturn;
		}

		ChipPosition[] streakDiagonalB = GameTest.buildStreak(new ChipPosition(row + newN, column - newN), new ChipPosition(row - newN, column + newN));
		toReturn = checkStreakForWin(streakDiagonalB);
		if (toReturn != null) {
			return toReturn;
		}

		// vertical
		ChipPosition[] streakVertical = GameTest.buildStreak(new ChipPosition(row + newN, column), new ChipPosition(row - newN, column));
		toReturn = checkStreakForWin(streakVertical);
		if (toReturn != null) {
			return toReturn;
		}

		// horizontal
		ChipPosition[] streakHorizontal = GameTest.buildStreak(new ChipPosition(row, column - newN), new ChipPosition(row, column + newN));
		toReturn = checkStreakForWin(streakHorizontal);
		if (toReturn != null) {
			return toReturn;
		}

		return null;
	}

	private GameResult checkStreakForWin(ChipPosition[] chipPositions) {
		List<ChipPosition> winnerStreak = getWinnerStreak(chipPositions);

		if (winnerStreak != null) {
			return new GameResult(this.getLastChipAdded(), winnerStreak.toArray(new ChipPosition[winnerStreak.size()]));
		}
		return null;
	}

	private List<ChipPosition> getWinnerStreak(ChipPosition[] chipPositions) {
		List<ChipPosition> chipPositionsLimpio = cleanForInvalidPositions(chipPositions);
		return checkForWinnerSubstreak(chipPositionsLimpio);
	}

	/**
	 * Limpia el streak de posiciones invalidas: [-1,-2], [-1,0]......
	 * 
	 * @param chipPositions
	 * @return
	 */
	private List<ChipPosition> cleanForInvalidPositions(ChipPosition[] chipPositions) {
		List<ChipPosition> chipPositionsLimpio = new ArrayList<>();
		for (ChipPosition chipPosition : chipPositions) {
			if (isValidPositionInBoard(chipPosition)) {
				chipPositionsLimpio.add(chipPosition);
			}
		}
		return chipPositionsLimpio;
	}

	/**
	 * Comprueba si una ChipPosition es valida en relacion a un tablero
	 * 
	 * @param chipPosition
	 * @return
	 */
	private boolean isValidPositionInBoard(ChipPosition chipPosition) {
		if (chipPosition.getColumn() < 0 || chipPosition.getColumn() > board.getChips().length - 1 || chipPosition.getRow() < 0 || chipPosition.getRow() > board.getChips()[0].length - 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Busca el streak ganador en los substreaks posibles de tamaño n
	 * 
	 * @param chips
	 * @return
	 */
	private List<ChipPosition> checkForWinnerSubstreak(List<ChipPosition> chips) {

		for (int i = 0; i < chips.size() - (this.n - 1); i++) {

			List<ChipPosition> sublist = chips.subList(i, i + this.n);

			if (checkIfAllEquals(sublist)) {
				return sublist;
			}
		}

		return null;

	}

	/**
	 * Comprueba si todos los Chips de las posiciones del Streak son iguales, con lo
	 * cual seria un ganador
	 * 
	 * @param chipPositionList
	 * @return
	 */
	private boolean checkIfAllEquals(List<ChipPosition> chipPositionList) {

		List<ChipColor> chips = new ArrayList<>();
		for (ChipPosition chipPosition : chipPositionList) {
			int column = chipPosition.getColumn();
			int row = chipPosition.getRow();

			ChipColor chipOnBoard = this.getBoard().getChips()[column][row];

			chips.add(chipOnBoard);
		}

		if (chips.size() != this.n || chips.contains(null)) {
			return false;
		} else {
			boolean allEqual = new HashSet<ChipColor>(chips).size() <= 1;

			return allEqual;
		}
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public ChipPosition getLastChipPositionAdded() {
		return lastChipPositionAdded;
	}

	public void setLastChipPositionAdded(ChipPosition lastChipPositionAdded) {
		this.lastChipPositionAdded = lastChipPositionAdded;
	}

	public ChipColor getLastChipAdded() {
		return lastChipAdded;
	}

	public void setLastChipAdded(ChipColor lastChipAdded) {
		this.lastChipAdded = lastChipAdded;
	}

	@Override
	public String toString() {
		return "GameImpl [board=" + board + ", n=" + n + ", lastChipPositionAdded=" + lastChipPositionAdded + ", lastChipAdded=" + lastChipAdded + "]";
	}


}
