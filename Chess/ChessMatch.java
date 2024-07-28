package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;
import Chess.pieces.Bishop;
import Chess.pieces.King;
import Chess.pieces.Knight;
import Chess.pieces.Pawn;
import Chess.pieces.Queen;
import Chess.pieces.Rook;

public class ChessMatch {

	private int turn; // Contador de turnos
	private Color currentPlayer; // Jogador atual
	private Board board; // Tabuleiro do jogo
	private boolean check; // Estado de cheque
	private boolean checkMate; // Estado de cheque-mate
	private ChessPiece enPassantVulnerable; // Peça vulnerável ao en passant
	private ChessPiece promoted; // Peça promovida

	private List<Piece> piecesOnTheBoard = new ArrayList<>(); // Peças no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>(); // Peças capturadas

	public ChessMatch() {
		board = new Board(8, 8); // Inicializa o tabuleiro 8x8
		turn = 1; // Inicia no turno 1
		currentPlayer = Color.WHITE; // O jogador branco começa
		initialSetup(); // Configuração inicial do tabuleiro
	}

	public int getTurn() {
		return turn; // Retorna o turno atual
	}

	public Color getCurrentPlayer() {
		return currentPlayer; // Retorna o jogador atual
	}

	public boolean getCheck() {
		return check; // Retorna se o rei está em cheque
	}

	public boolean getCheckMate() {
		return checkMate; // Retorna se o jogo está em cheque-mate
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable; // Retorna a peça vulnerável ao en passant
	}

	public ChessPiece getPromoted() {
		return promoted; // Retorna a peça promovida
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; // Cria uma matriz de peças de xadrez
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // Preenche a matriz com as peças do tabuleiro
			}
		}
		return mat; // Retorna a matriz de peças de xadrez
	}

	public boolean[][] possibleMove(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition(); // Converte a posição de xadrez para a posição interna
		validateSourcePosition(position); // Valida a posição de origem
		return board.piece(position).possibleMoves(); // Retorna os movimentos possíveis para a peça na posição de
														// origem
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // Converte a posição de origem para a posição interna
		Position target = targetPosition.toPosition(); // Converte a posição de destino para a posição interna
		validateSourcePosition(source); // Valida a posição de origem
		validateTargetPosition(source, target); // Valida a posição de destino
		Piece capturedPiece = makeMove(source, target); // Realiza o movimento

		if (testCheck(currentPlayer)) { // Verifica se o jogador atual está em cheque após o movimento
			undoMove(source, target, capturedPiece); // Desfaz o movimento
			throw new ChessException("You can't put yourself in check"); // Exceção se o movimento coloca o jogador em
																			// cheque
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target); // Obtém a peça movida

		// Movimento especial de promoção
		promoted = null; // Reseta a peça promovida
		if (movedPiece instanceof Pawn) { // Verifica se a peça movida é um peão
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(target); // Define a peça promovida
				promoted = replacePromotedPiece("Q"); // Promove para Rainha por padrão
			}
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false; // Verifica se o oponente está em cheque

		if (testCheckMate(opponent(currentPlayer))) { // Verifica se o oponente está em cheque-mate
			checkMate = true; // Define o estado de cheque-mate
		} else {
			nextTurn(); // Passa para o próximo turno
		}

		// Movimento especial en passant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece; // Define a peça vulnerável ao en passant
		} else {
			enPassantVulnerable = null; // Reseta a peça vulnerável ao en passant
		}

		return (ChessPiece) capturedPiece; // Retorna a peça capturada
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted"); // Exceção se não houver peça para ser
																					// promovida
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted; // Retorna a peça promovida se o tipo for inválido
		}

		Position pos = promoted.getChessPosition().toPosition(); // Obtém a posição da peça promovida
		Piece p = board.removePiece(pos); // Remove a peça do tabuleiro
		piecesOnTheBoard.remove(p); // Remove a peça da lista de peças no tabuleiro

		ChessPiece newPiece = newPiece(type, promoted.getColor()); // Cria a nova peça promovida
		board.placePiece(newPiece, pos); // Coloca a nova peça no tabuleiro
		piecesOnTheBoard.add(newPiece); // Adiciona a nova peça à lista de peças no tabuleiro

		return newPiece; // Retorna a nova peça promovida
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color); // Retorna um Bispo
		if (type.equals("N"))
			return new Knight(board, color); // Retorna um Cavalo
		if (type.equals("Q"))
			return new Queen(board, color); // Retorna uma Rainha
		return new Rook(board, color); // Retorna uma Torre
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); // Remove a peça da posição de origem
		p.increaseMoveCount(); // Incrementa o contador de movimentos da peça
		Piece capturedPiece = board.removePiece(target); // Remove a peça na posição de destino
		board.placePiece(p, target); // Coloca a peça na posição de destino

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece); // Remove a peça capturada da lista de peças no tabuleiro
			capturedPieces.add(capturedPiece); // Adiciona a peça capturada à lista de peças capturadas
		}

		// Movimento especial roque pequeno
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remove a torre da posição de origem do roque
			board.placePiece(rook, targetT); // Coloca a torre na posição de destino do roque
			rook.increaseMoveCount(); // Incrementa o contador de movimentos da torre
		}
		// Movimento especial roque grande
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remove a torre da posição de origem do roque
			board.placePiece(rook, targetT); // Coloca a torre na posição de destino do roque
			rook.increaseMoveCount(); // Incrementa o contador de movimentos da torre
		}

		// Movimento especial en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}

				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		// Retorna a peça capturada

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		// Remove a peça da posição de destino
		p.decreaseMoveCount();
		// Diminui o contador de movimentos da peça
		board.placePiece(p, source);
		// Coloca a peça de volta na posição de origem

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			// Coloca a peça capturada de volta na posição de destino
			capturedPieces.remove(capturedPiece);
			// Remove a peça capturada da lista de peças capturadas
			piecesOnTheBoard.add(capturedPiece);
			// Adiciona a peça capturada de volta ao tabuleiro
		}

		// Movimento especial: Roque pelo lado do rei
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		// Movimento especial: Roque pelo lado da dama
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		// Movimento especial: En passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				// Remove o peão da posição de destino
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
				// Coloca o peão de volta na posição correta
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on sourcposition");
		}
		// Verifica se há uma peça na posição de origem; se não houver, lança uma
		// exceção.

		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		// Verifica se a peça na posição de origem pertence ao jogador atual; se não,
		// lança uma exceção.

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
		// Verifica se a peça na posição de origem tem algum movimento possível; se não,
		// lança uma exceção.
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("the chosen piece can't move to target position");
		}
		// Verifica se a peça na posição de origem pode se mover para a posição de
		// destino; se não, lança uma exceção.
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// Incrementa o turno e alterna o jogador atual entre branco e preto.
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// Retorna a cor do jogador oponente.
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// Cria uma lista de peças no tabuleiro que correspondem à cor fornecida.

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " King on the board");
		// Procura pelo rei da cor fornecida na lista; se não encontrar, lança uma
		// exceção.
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		// Obtém a posição do rei da cor fornecida.

		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		// Cria uma lista de peças no tabuleiro que pertencem ao oponente.

		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			// Obtém os movimentos possíveis de cada peça oponente.

			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
			// Verifica se algum movimento possível das peças oponentes pode capturar o rei;
			// se sim, retorna verdadeiro.
		}
		return false;
		// Se nenhum movimento possível das peças oponentes pode capturar o rei, retorna
		// falso.
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		// Verifica se o rei está em cheque; se não, retorna falso.

		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// Cria uma lista de peças no tabuleiro que correspondem à cor fornecida.

		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			// Obtém os movimentos possíveis de cada peça da cor fornecida.

			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						// Tenta mover a peça para a posição de destino e captura a peça na posição de
						// destino, se houver.

						boolean testCheck = testCheck(color);
						// Verifica se o rei ainda está em cheque após o movimento.

						undoMove(source, target, capturedPiece);
						// Desfaz o movimento.

						if (!testCheck) {
							return false;
						}
						// Se o rei não estiver mais em cheque após o movimento, retorna falso.
					}
				}
			}
		}
		return true;
		// Se nenhum movimento possível pode tirar o rei do cheque, retorna verdadeiro.
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
		// Coloca uma nova peça no tabuleiro na posição especificada e adiciona à lista
		// de peças no tabuleiro.
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
		// Coloca todas as peças brancas na posição inicial.

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}