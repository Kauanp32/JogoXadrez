package Chess.pieces;
// Define o pacote onde esta classe está localizada.

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;
// Importa as classes necessárias dos pacotes Boardgame e Chess.

public class Knight extends ChessPiece {
    // Declara a classe Knight que estende ChessPiece.

    public Knight(Board board, Color color) {
        super(board, color);
    }
    // Construtor da classe Knight que chama o construtor da superclasse ChessPiece.

    @Override
    public String toString() {
        return "N";
    }
    // Sobrescreve o método toString para retornar "N", representando o Cavalo no tabuleiro.

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }
    // Método auxiliar privado que verifica se o Cavalo pode se mover para uma determinada posição. Ele pode se mover se a posição estiver vazia ou contiver uma peça do oponente.

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // Cria uma matriz de booleanos com o mesmo tamanho do tabuleiro, para armazenar os movimentos possíveis do Cavalo.

        Position p = new Position(0, 0);
        // Cria uma nova posição inicializada com valores arbitrários.

        // Define as posições possíveis para os movimentos do Cavalo (em forma de "L").
        p.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
        // Retorna a matriz de movimentos possíveis.
    }
}