package Boardgame;

// Classe Board que representa o tabuleiro do jogo
public class Board {
	
	// Número de linhas do tabuleiro
	private int rows;
	
	// Número de colunas do tabuleiro
	private int columns;
	
	// Matriz de peças no tabuleiro
	private Piece[][] pieces;
	
	// Construtor da classe Board
	public Board (int rows, int columns) {
		// Verifica se o número de linhas ou colunas é menor que 1, se sim, lança uma exceção
		if (rows <1 || columns <1) {
			throw new BoardException("Rows and columns must be greater than 0");
		}
		// Inicializa o número de linhas e colunas
		this.rows = rows;
		this.columns = columns;
		// Inicializa a matriz de peças com o tamanho especificado
		pieces = new Piece[rows][columns];
	}
	
	// Retorna o número de linhas do tabuleiro
	public int getRows() {
		return rows;
	}
	
	// Retorna o número de colunas do tabuleiro
	public int getColumns() {
		return columns;
	}
	
	// Retorna a peça na posição especificada (linha, coluna)
	public Piece piece (int row, int column ) {
		// Verifica se a posição existe no tabuleiro, se não, lança uma exceção
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the Board");
		}
 		// Retorna a peça na posição especificada
		return pieces[row][column];
	}
	
	// Retorna a peça na posição especificada (objeto Position)
	public Piece piece(Position position) {
		// Verifica se a posição existe no tabuleiro, se não, lança uma exceção
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board");
	    }
		// Retorna a peça na posição especificada
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// Coloca uma peça na posição especificada
	public void placePiece(Piece piece, Position position) {
		// Verifica se já existe uma peça na posição, se sim, lança uma exceção
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		// Coloca a peça na posição especificada
		pieces[position.getRow()][position.getColumn()] = piece;
		// Atualiza a posição da peça
		piece.position = position;
	}
	
	// Remove e retorna a peça na posição especificada
	public Piece removePiece(Position position) {
		// Verifica se a posição existe no tabuleiro, se não, lança uma exceção
		if (!positionExists(position)) {
			throw new BoardException("Position not on the Board");
		}
		// Verifica se há uma peça na posição especificada, se não, retorna null
		if (piece(position) == null) {
			return null;
		}
		// Guarda a peça a ser removida
		Piece aux = piece(position);
		// Define a posição da peça como null
		aux.position = null;
		// Remove a peça do tabuleiro
		pieces[position.getRow()][position.getColumn()] = null;
		// Retorna a peça removida
		return aux;
	}
	
	// Verifica se a posição (linha, coluna) existe no tabuleiro
	private boolean positionExists(int row, int column) {
		// Retorna true se a posição estiver dentro dos limites do tabuleiro
		return row >= 0 && row < rows && column >= 0 && column < columns;	
	}
	
	// Verifica se a posição (objeto Position) existe no tabuleiro
	public boolean positionExists(Position position) {
		// Chama o método sobrecarregado para verificar se a posição existe
		return positionExists(position.getRow(), position.getColumn());
	}
	
	// Verifica se há uma peça na posição especificada
	public boolean thereIsAPiece(Position position) {
		// Retorna true se houver uma peça na posição especificada
		return piece(position) != null;
	}
}