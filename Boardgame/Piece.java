package Boardgame;

// Classe abstrata Piece que representa uma peça no tabuleiro
public abstract class Piece {

	// Posição da peça no tabuleiro
	protected Position position;
	
	// Referência ao tabuleiro
	private Board board;

	// Construtor da classe Piece
	public Piece(Board board) {
		super();
		// Inicializa o tabuleiro
		this.board = board;
		// Inicializa a posição como null
		position = null;
	}

	// Retorna o tabuleiro associado à peça
	protected Board getBoard() {
		return board;
	}

	// Método abstrato para determinar os movimentos possíveis da peça
	public abstract boolean[][] possibleMoves();

	// Verifica se a peça pode se mover para uma determinada posição
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	// Verifica se há algum movimento possível para a peça
	public boolean isThereAnyPossibleMove() {
	    boolean[][] mat = possibleMoves();
	    for (int i = 0; i < mat.length; i++) {
	        for (int j = 0; j < mat[i].length; j++) {
		        if (mat[i][j]) {
			        return true;
		        }	
		    }
	    }
	    return false;
    }
}