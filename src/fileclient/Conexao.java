package fileclient;

import fileclient.ManipuladorEntradas.RETORNO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao extends Thread {

    ManipuladorEntradas mpEntradas = new ManipuladorEntradas();
    DataInputStream entrada;
    DataOutputStream saida;
    Socket cliente;

    public Conexao(Socket clienteSocket) throws IOException {
        cliente = clienteSocket;
        OutputStream resp = new FileOutputStream("/tmp/out");

        entrada = new DataInputStream(cliente.getInputStream());
        

        RETORNO processar = mpEntradas.processar(entrada.readUTF());
        if (processar == RETORNO.ENVIAR_RESPOSTA_LISTA) {
            for (String s : mpEntradas.getRespostaLista()) {
                resp.write(s.getBytes(Charset.forName("UTF-8")));
                resp.write('\n');
            }
        }
        saida = new DataOutputStream(clienteSocket.getOutputStream());
        saida.writeUTF(resp.toString());
        this.start();
    }

    public void run() {
        try {
            String dado = entrada.readUTF();
            saida.writeUTF("Dados recebidos...");
            entrada.close();
            saida.close();
        } catch (IOException ex) {
            System.out.println("Conexao::run1 IO: " + ex.getMessage());
        } finally {
            try {
                cliente.close();
            } catch (IOException ex) {
                System.out.println("Conexao::run2 IO: " + ex.getMessage());
            }
        }
    }

}
