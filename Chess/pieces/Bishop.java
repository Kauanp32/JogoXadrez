package Chess.pieces;
// Define o pacote onde esta classe está localizada.

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;
// Importa as classes necessárias dos pacotes Boardgame e Chess.

public class Bishop extends ChessPiece {
    // Declara a classe Bishop que estende ChessPiece.

    public Bishop(Board board, Color color) {
        super(board, color);
    }
    // Construtor da classe Bishop que inicializa o tabuleiro e a cor da peça, chamando o construtor da superclasse ChessPiece.

    @Override
    public String toString() {
        return "B";
    }
    // Sobrescreve o método toString para retornar "B", representando o Bispo no tabuleiro.

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // Cria uma matriz de booleanos com o mesmo tamanho do tabuleiro, para armazenar os movimentos possíveis do Bispo.

        Position p = new Position(0, 0);
        // Cria uma nova posição inicializada com valores arbitrários.

        // Movimentos possíveis do Bispo (diagonais).

        // noroeste
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // nordeste
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // sudeste
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // sudoeste
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
        // Retorna a matriz de movimentos possíveis.
    }
}