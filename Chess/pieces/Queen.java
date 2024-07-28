package Chess.pieces;
// Define o pacote onde esta classe está localizada.

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;
// Importa as classes necessárias dos pacotes Boardgame e Chess.

public class Queen extends ChessPiece {
    // Declara a classe Queen que estende ChessPiece.

    public Queen(Board board, Color color) {
        super(board, color);
    }
    // Construtor da classe Queen que chama o construtor da superclasse ChessPiece, passando o tabuleiro e a cor da peça como parâmetros.

    @Override
    public String toString() {
        return "Q";
    }
    // Sobrescreve o método toString para retornar "Q", representando a Rainha no tabuleiro.

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // Cria uma matriz de booleanos com o mesmo tamanho do tabuleiro, para armazenar os movimentos possíveis da Rainha.

        Position p = new Position(0, 0);
        // Cria uma nova posição inicializada com valores arbitrários.

        // Movimentos acima (above)
        p.setValues(position.getRow() - 1, position.getColumn());
        // Define a posição acima da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            // Marca a posição como possível movimento.
            p.setRow(p.getRow() - 1);
            // Move a posição uma linha para cima.
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // Se encontrar uma peça do oponente na posição, marca a posição como possível movimento.

        // Movimentos à esquerda (left)
        p.setValues(position.getRow(), position.getColumn() - 1);
        // Define a posição à esquerda da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos à direita (right)
        p.setValues(position.getRow(), position.getColumn() + 1);
        // Define a posição à direita da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos abaixo (below)
        p.setValues(position.getRow() + 1, position.getColumn());
        // Define a posição abaixo da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos noroeste (nw)
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        // Define a posição noroeste da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos nordeste (ne)
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        // Define a posição nordeste da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos sudeste (se)
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        // Define a posição sudeste da posição atual.
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimentos sudoeste (sw)
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        // Define a posição sudoeste da posição atual.
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