package SistemaXadrez;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.Color;
// Importações das classes necessárias para manipulação de coleções, exceções, entrada do usuário e lógica do xadrez.

public class UI {
    // Declaração da classe pública UI.

    // Definições de constantes ANSI para colorir a saída do console.
    public static final String ANSI_RESET = "\u0018[0m";
    public static final String ANSI_BLACK = "\u0018[30m";
    public static final String ANSI_RED = "\u0018[31m";
    public static final String ANSI_GREEN = "\00018[32m";
    public static final String ANSI_YELLOW = "\u0018 [33m";
    public static final String ANSI_BLUE = "\u0018 [34m";
    public static final String AIST_PURPLE = "\00018[35m";
    public static final String ANSI_CYAN = "\0018 [36m";
    public static final String ANSI_WHITE = "\u0018 [37m";
    public static final String ANSI_BLACK_BACKGROUND = "\0018 [40m";
    public static final String ANSI_RED_BACKGROUND = "\u0018 [41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u0018[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\0018[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u0018[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u0018[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u0018[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u0018[47m";

    // Método para limpar a tela do console.
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Método para ler uma posição de xadrez do usuário.
    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    // Método para imprimir o estado atual da partida de xadrez.
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        if (chessMatch.getCheck()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK!");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    // Método para imprimir o tabuleiro de xadrez.
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println(" a b c d e f g h");
    }

    // Método para imprimir o tabuleiro de xadrez com os movimentos possíveis destacados.
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + "  ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println(" a b c d e f g h");
    }

    // Método para imprimir uma peça de xadrez no console, opcionalmente com um fundo colorido.
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    // Método para imprimir as peças capturadas.
    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
}