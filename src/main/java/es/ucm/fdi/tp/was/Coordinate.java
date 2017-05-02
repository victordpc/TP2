package es.ucm.fdi.tp.was;

public class Coordinate {
	/**
	 * Contiene la fila en la que se encuentra la ficha en el tablero
	 */
	private int x;
	/**
	 * Contiene la columna en la que se encuentra la ficha en el tablero
	 */
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Contiene la columna en la que se encuentra la ficha en el tablero
	 * 
	 * @return Devuelve la fila en la que se encuentra la ficha en el tablero.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Contiene la columna en la que se encuentra la ficha en el tablero
	 * 
	 * @return Devuelve la columna en la que se encuentra la ficha en el
	 *         tablero.
	 */
	public int getY() {
		return y;
	}

	public boolean isEqual(Coordinate testCoordinate) {
		return (this.x == testCoordinate.getX()) && (this.y == testCoordinate.getY());
	}
}
