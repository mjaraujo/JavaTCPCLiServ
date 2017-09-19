package fileclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenServer implements Runnable {

    private final int porta;

    public ListenServer(int porta) {
        this.porta = porta;
    }

    public void iniciar() {
        try {
            //Criar a conex�o/ouvinte servidor
            ServerSocket escutarPorta = new ServerSocket(porta);
            //Aguardar por novas conex�es
            while (true) {
                Socket cliente = escutarPorta.accept();
                new Conexao(cliente);
             //   escutarPorta.close();
            }
        } catch (IOException ex) {
            System.out.println("Servidor ServerSocket IO: " + ex.getMessage());
        }
    }

    /**
     * @return the porta
     */
    public int getPorta() {
        return porta;
    }

    @Override
    public void run() {
        iniciar();
    }

}
