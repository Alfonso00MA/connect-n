package mar.alfonso.connectn;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import mar.alfonso.connectn.ChipColor;
import mar.alfonso.connectn.ChipPosition;
import mar.alfonso.connectn.GameResult;
import mar.alfonso.connectn.game.Game;
import mar.alfonso.connectn.game.GameImpl;

public class GameTest {

	private static GameResult run(final int rows, final int columns, final int connectN, final Player playerA, final Player playerB) {
		final GameEngine gameEngine = new GameEngine();
		final Game game = new GameImpl(rows, columns, connectN);
		return gameEngine.play(game, playerA, playerB);
	}

	public static ChipPosition[] buildStreak(final ChipPosition start, final ChipPosition end) {
		final int rowIncrement = Integer.signum(end.getRow() - start.getRow()); // doesn't work properly on int overflow
		final int columnIncrement = Integer.signum(end.getColumn() - start.getColumn()); // doesn't work properly on int overflow
		final int length = Math.max(Math.abs(end.getRow() - start.getRow()), Math.abs(end.getColumn() - start.getColumn())) + 1;
		final ChipPosition[] result = new ChipPosition[length];

		int row = start.getRow();
		int column = start.getColumn();
		for (int i = 0; i < length; i++) {
			result[i] = new ChipPosition(row, column);
			row += rowIncrement;
			column += columnIncrement;
		}

		return result;
	}

	@Test
	public void test1() {
		final GameResult gameResult = run(6, 7, 2, new Player(ChipColor.RED, 1, 3, 5), new Player(ChipColor.YELLOW, 0, 2, 0));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.YELLOW));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(new ChipPosition(0, 0), new ChipPosition(1, 0)));
	}

	@Test
	public void test2() {
		final GameResult gameResult = run(6, 7, 3, new Player(ChipColor.RED, 1, 2, 4, 3, 5, 5), new Player(ChipColor.YELLOW, 1, 2, 3, 2, 3, 3));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.YELLOW));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(1, 1), new ChipPosition(3, 3))));
	}

	@Test
	public void test3() {
		final GameResult gameResult = run(6, 7, 5, new Player(ChipColor.RED, 1, 2, 4, 3, 5, 5, 4, 4, 5),
				new Player(ChipColor.YELLOW, 1, 2, 3, 2, 3, 3, 4, 0, 4));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.YELLOW));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(0, 0), new ChipPosition(4, 4))));
	}

	@Test
	public void test4() {
		final GameResult gameResult = run(6, 7, 6, new Player(ChipColor.RED, 1, 2, 4, 3, 5, 5, 4, 4, 5, 0, 2, 1),
				new Player(ChipColor.YELLOW, 1, 2, 3, 2, 3, 3, 4, 0, 4, 1, 0, 1));
		assertNull(gameResult);
	}

	@Test
	public void test5() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 1, 2, 3, 4), new Player(ChipColor.YELLOW, 1, 1, 1, 5));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.RED));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(0, 1), new ChipPosition(0, 4))));
	}

	@Test
	public void test6() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 1, 1, 2, 2), new Player(ChipColor.YELLOW, 3, 3, 3, 3));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.YELLOW));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(0, 3), new ChipPosition(3, 3))));
	}

	@Test
	public void test7() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 1, 2, 2, 3, 3, 3, 4), new Player(ChipColor.YELLOW, 2, 3, 3, 4, 4, 4, 6));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.RED));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(0, 1), new ChipPosition(3, 4))));
	}

	@Test
	public void test8() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 1, 1, 1, 2, 3, 4), new Player(ChipColor.YELLOW, 2, 1, 2, 3, 5, 5));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.RED));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(3, 1), new ChipPosition(0, 4))));
	}

	@Test
	public void test9() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 2, 3, 4, 2, 3, 4, 6, 6, 6),
				new Player(ChipColor.YELLOW, 1, 2, 2, 5, 2, 3, 4, 5, 3));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.YELLOW));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(4, 2), new ChipPosition(1, 5))));
	}

	@Test
	public void test10() {
		final GameResult gameResult = run(6, 7, 4, new Player(ChipColor.RED, 0, 2, 2, 5, 2, 3, 4, 5, 4, 6, 5),
				new Player(ChipColor.YELLOW, 2, 3, 4, 2, 3, 4, 6, 6, 5, 5, 6));
		assertNotNull(gameResult);
		assertThat(gameResult.getChip(), is(ChipColor.RED));
		assertThat(gameResult.getPositions(), arrayContainingInAnyOrder(buildStreak(new ChipPosition(1, 2), new ChipPosition(4, 5))));
	}

}
