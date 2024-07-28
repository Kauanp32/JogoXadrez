package SistemaXadrez;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;
// Importa as classes necessárias de diferentes pacotes.

public class Xadrez {
    // Declara a classe pública Xadrez.

    public static void main(String[] args) {
        // Método principal onde a execução do programa começa.

        Scanner sc = new Scanner(System.in);
        // Cria um objeto Scanner para ler a entrada do usuário.

        ChessMatch chessMatch = new ChessMatch();
        // Cria uma nova partida de xadrez.

        List<ChessPiece> captured = new ArrayList<>();
        // Cria uma lista para armazenar as peças capturadas.

        while (!chessMatch.getCheckMate()) {
            // Loop principal do jogo que continua até que haja um cheque-mate.

            try {
                UI.clearScreen();
                // Limpa a tela.

                UI.printMatch(chessMatch, captured);
                // Imprime o estado atual da partida e as peças capturadas.

                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);
                // Pede ao usuário para inserir a posição da peça que deseja mover.

                boolean[][] possibleMoves = chessMatch.possibleMove(source);
                // Obtém os movimentos possíveis para a peça selecionada.

                UI.clearScreen();
                // Limpa a tela novamente.

                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                // Imprime o tabuleiro com os movimentos possíveis destacados.

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);
                // Pede ao usuário para inserir a posição para onde deseja mover a peça.

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                // Executa o movimento de xadrez e obtém qualquer peça que tenha sido capturada.

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }
                // Se uma peça foi capturada, adiciona à lista de peças capturadas.

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }
                // Se uma peça foi promovida, solicita ao usuário que insira o tipo da peça para a promoção.
                // Continua solicitando até que um valor válido seja inserido.
                // Substitui a peça promovida pelo tipo escolhido.

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
                // Captura exceções relacionadas ao jogo de xadrez e exibe a mensagem de erro.
                // Espera que o usuário pressione Enter antes de continuar.

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
                // Captura exceções de entrada inválida e exibe a mensagem de erro.
                // Espera que o usuário pressione Enter antes de continuar.
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
        // Limpa a tela e imprime o estado final da partida e as peças capturadas.
    }
}