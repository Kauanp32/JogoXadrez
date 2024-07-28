package Boardgame;

// Classe Position que representa a posição de uma peça no tabuleiro
public class Position {
	
	// Linha da posição
	private int row;
	
	// Coluna da posição
	private int column;
	
	// Construtor da classe Position
	public Position(int row, int column) {
		// Inicializa a linha e a coluna com os valores passados como parâmetros
		this.row = row;
		this.column = column;
	}
	
	// Retorna a linha da posição
	public int getRow() {
		return row;
	}

	// Define a linha da posição
	public void setRow(int row) {
		this.row = row;
	}

	// Retorna a coluna da posição
	public int getColumn() {
		return column;
	}

	// Define a coluna da posição
	public void setColumn(int column) {
		this.column = column;
	}
	
	// Define os valores da linha e da coluna
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	// Sobrescreve o método toString para retornar a posição no formato "linha, coluna"
	@Override
	public String toString() {
		return row + "," + column;
	}
}