// Define o pacote onde esta classe está localizada.
package Chess;

//Importa a classe BoardException do pacote Boardgame. Isso indica que ChessException herda de BoardException.
import Boardgame.BoardException;

//Declara a classe ChessException que estende BoardException.
public class ChessException extends BoardException {
    
    // Define um ID de versão serial para a serialização. Isso é usado para verificar a compatibilidade da classe durante o processo de serialização e desserialização.
	private static final long serialVersionUID = 1L;

    // Construtor da classe ChessException que aceita uma mensagem como parâmetro e a passa para o construtor da classe  (BoardException).
    public ChessException(String msg) {
        super(msg);
    }
}