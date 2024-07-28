package Chess;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;

// Classe abstrata ChessPiece que representa uma peça de xadrez
public abstract class ChessPiece extends Piece {
	
	// Cor da peça de xadrez
	private Color color;
	
	// Contador de movimentos da peça
	private int moveCount;

	// Construtor da classe ChessPiece
	public ChessPiece(Board board, Color color) {
		// Chama o construtor da classe pai (Piece)
		super(board);
		// Inicializa a cor da peça
		this.color = color;
	}

	// Retorna a cor da peça
	public Color getColor() {
		return color;
	}  
	
	// Retorna o número de movimentos da peça
	public int getMoveCount() {
		return moveCount;
	}
	
	// Incrementa o contador de movimentos
	public void increaseMoveCount() {
		moveCount++;
	}
	
	// Decrementa o contador de movimentos
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	// Retorna a posição da peça no formato de xadrez (ChessPosition)
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	// Verifica se há uma peça oponente na posição especificada
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		// Retorna true se houver uma peça na posição e a cor for diferente
		return p != null && p.getColor() != color;
	}
}