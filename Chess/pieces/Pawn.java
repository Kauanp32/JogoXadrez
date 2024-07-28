package Chess.pieces;
// Define o pacote onde esta classe está localizada.

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;
// Importa as classes necessárias dos pacotes Boardgame e Chess.

public class Pawn extends ChessPiece {
    // Declara a classe Pawn que estende ChessPiece.

    private ChessMatch chessMatch;
    // Declara um campo privado para armazenar a instância atual do jogo de xadrez.

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    // Construtor da classe Pawn que chama o construtor da superclasse ChessPiece e inicializa o campo chessMatch.

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // Cria uma matriz de booleanos com o mesmo tamanho do tabuleiro, para armazenar os movimentos possíveis do Peão.

        Position p = new Position(0, 0);
        // Cria uma nova posição inicializada com valores arbitrários.

        if (getColor() == Color.WHITE) {
            // Se a cor do Peão for branca:
            p.setValues(position.getRow() - 1, position.getColumn());
            // Define a posição uma linha acima da posição atual.
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição existir e não houver uma peça nela, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            // Define as posições duas linhas acima e uma linha acima da posição atual.
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se ambas as posições existirem e não houver peças nelas, e o Peão ainda não tiver se movido, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição diagonal esquerda acima existir e houver uma peça do oponente nela, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição diagonal direita acima existir e houver uma peça do oponente nela, marca a posição como possível movimento.
            }

            // Movimento especial en passant para peão branco
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                    // Se a posição à esquerda do Peão for vulnerável ao en passant, marca a posição como possível movimento.
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;
                    // Se a posição à direita do Peão for vulnerável ao en passant, marca a posição como possível movimento.
                }
            }

        } else {
            // Se a cor do Peão for preta:
            p.setValues(position.getRow() + 1, position.getColumn());
            // Define a posição uma linha abaixo da posição atual.
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição existir e não houver uma peça nela, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            // Define as posições duas linhas abaixo e uma linha abaixo da posição atual.
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se ambas as posições existirem e não houver peças nelas, e o Peão ainda não tiver se movido, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição diagonal esquerda abaixo existir e houver uma peça do oponente nela, marca a posição como possível movimento.
            }
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                // Se a posição diagonal direita abaixo existir e houver uma peça do oponente nela, marca a posição como possível movimento.
            }

            // Movimento especial en passant para peão preto
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;
                    // Se a posição à esquerda do Peão for vulnerável ao en passant, marca a posição como possível movimento.
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;
                    // Se a posição à direita do Peão for vulnerável ao en passant, marca a posição como possível movimento.
                }
            }
        }
        return mat;
        // Retorna a matriz de movimentos possíveis.
    }
    // Sobrescreve o método toString para retornar "p", representando o Peão no tabuleiro.
    @Override
    public String toString() {
        return "p";
    }
}