package Chess.pieces;
// Define o pacote onde esta classe está localizada.

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;
// Importa as classes necessárias dos pacotes Boardgame e Chess.

public class Rook extends ChessPiece {
    // Declara a classe Rook que estende ChessPiece.

    public Rook(Board board, Color color) {
        super(board, color);
    }
    // Construtor da classe Rook que chama o construtor da superclasse ChessPiece, passando o tabuleiro e a cor da peça como parâmetros.

    @Override
    public String toString() {
        return "R";
    }
    // Sobrescreve o método toString para retornar "R", representando a Torre no tabuleiro.

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // Cria uma matriz de booleanos com o mesmo tamanho do tabuleiro, para armazenar os movimentos possíveis da Torre.

        Position p = new Position(0, 0);
        // Cria uma nova posição inicializada com valores arbitrários.

        // Movimentos acima (acima)
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
        // Retorna a matriz de movimentos possíveis.
        return mat;
    }
}