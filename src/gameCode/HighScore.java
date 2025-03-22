package gameCode;

import java.time.LocalDate;
import java.time.LocalTime;

public record HighScore(String name, LocalTime time, LocalDate date) {

	/**
	 * Gibt an ob der übergebene HighScore schneller war.
	 * D.h. Die Zeit des übergebenen HighScores ist kleiner als von diesem.
	 */
	public boolean isFaster(HighScore highScore) {
		return highScore.time().isBefore(time());
	}
}
