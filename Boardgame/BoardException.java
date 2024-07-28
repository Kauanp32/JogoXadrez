package Boardgame;

// Classe BoardException que representa exceções específicas do tabuleiro
public class BoardException extends RuntimeException {

	// Serial version UID para a serialização da classe
	private static final long serialVersionUID = 1L;

	// Construtor que aceita uma mensagem e a passa para o construtor da classe pai (RuntimeException)
	public BoardException(String msg) {
		super(msg);
	}
}