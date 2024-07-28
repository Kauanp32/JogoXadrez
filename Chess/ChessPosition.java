package Chess;

import Boardgame.Position;

// Classe ChessPosition que representa uma posição no tabuleiro de xadrez
public class ChessPosition {

	// Coluna no tabuleiro de xadrez (a-h)
	private char column;
	
	// Linha no tabuleiro de xadrez (1-8)
	private int row;

	// Construtor da classe ChessPosition
	public ChessPosition(char column, int row) {
		// Verifica se a coluna está entre 'a' e 'h' e a linha está entre 1 e 8, caso contrário, lança uma exceção
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}
		// Inicializa a coluna e a linha
		this.column = column;
		this.row = row;
	}

	// Retorna a coluna
	public char getColumn() {
		return column;
	}

	// Retorna a linha
	public int getRow() {
		return row;
	}

	// Converte uma ChessPosition para uma Position do tabuleiro
	protected Position toPosition() { 
		return new Position(8 - row, column - 'a');
	}

	// Converte uma Position do tabuleiro para uma ChessPosition
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	// Sobrescreve o método toString para retornar a posição no formato "coluna + linha"
	@Override
	public String toString() {
		return "" + column + row;
	}
}