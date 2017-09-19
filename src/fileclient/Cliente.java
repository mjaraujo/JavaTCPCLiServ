package fileclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

import javax.swing.JOptionPane;

public class Cliente {

    ClientForm formCli;
    private int porta;
    private String ip;
    private String caminhoDownload;

    public Cliente(int porta, String ip, ClientForm formCli, String caminho) {
        this.porta = porta;
        this.ip = ip;
        this.formCli = formCli;
        this.caminhoDownload = caminho;
    }

    private void solicitarLista() {
        enviar("getLista");
    }

    public void enviar(String mensagem) {
        Socket s = null;
        DataInputStream entrada;
        try {
            //Estabelecer conex�o
            s = new Socket(ip, porta);

            //Estabelecimento dos canais de comunica��o
            DataOutputStream saida = new DataOutputStream(s.getOutputStream());

            //Enviar dado
            saida.writeUTF(mensagem);
            if (mensagem.equals("getLista")) {
                entrada = new DataInputStream(s.getInputStream());
                DefaultListModel listModel = new DefaultListModel();

                String readUTF = entrada.readUTF();
                String[] linhas = readUTF.split(":");
                for (String linha : linhas) {
                    listModel.addElement(linha);
                }
                formCli.jList1.setModel(listModel);

            }
            if (mensagem.startsWith("getArquivo")) {
                entrada = new DataInputStream(s.getInputStream());
                OutputStream out = new FileOutputStream(caminhoDownload + File.separator + formCli.jList1.getSelectedValue());
                byte[] bytes = new byte[16 * 1024];

                int count;
                while ((count = entrada.read(bytes)) > 0) {
                    out.write(bytes, 0, count);
                }
                out.flush();
                out.close();
                JOptionPane.showMessageDialog(formCli, "Arquivo salvo no diretório Downloads");
                
            }
                
            saida.close();
            s.close();
        } catch (IOException ex) {
            System.out.println("Cliente Socket2 IO: " + ex.getMessage());
        } finally {

        }
    }

    /**
     * @return the porta
     */
    public int getPorta() {
        return porta;
    }

    /**
     * @param porta the porta to set
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the caminhoDownload
     */
    public String getCaminhoDownload() {
        return caminhoDownload;
    }

    /**
     * @param caminhoDownload the caminhoDownload to set
     */
    public void setCaminhoDownload(String caminhoDownload) {
        this.caminhoDownload = caminhoDownload;
    }

}
