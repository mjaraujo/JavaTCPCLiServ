/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileclient;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author marcio
 */
public class ManipuladorEntradas {

    private String resposta;
    private ArrayList<String> respostaLista;

    public enum RETORNO {
        ENVIAR_RESPOSTA(0),
        ENVIAR_RESPOSTA_LISTA(1),
        FALHOU(2);
        private int value;

        RETORNO(int value) {
            this.value = value;
        }

        public void exit() {
            System.exit(value);
        }
    }

    private RETORNO retorno;

    public ManipuladorEntradas() {
    }

    public RETORNO processar(String entrada) {
        if (entrada.equals("getLista")) {
            setRespostaLista(listarArquivos());
            return RETORNO.ENVIAR_RESPOSTA_LISTA;
        }
        return RETORNO.FALHOU;
    }

    private ArrayList<String> listarArquivos() {
        ArrayList<String> listaArquivos = new ArrayList<>();
        File folder = new File("/home/marcio/Documentos/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                listaArquivos.add(file.getName());
            }
        }
        return listaArquivos;
    }

    /**
     * @return the retorno
     */
    public RETORNO getRetorno() {
        return retorno;
    }

    /**
     * @param retorno the retorno to set
     */
    public void setRetorno(RETORNO retorno) {
        this.retorno = retorno;
    }

    /**
     * @return the resposta
     */
    public String getResposta() {
        return resposta;
    }

    /**
     * @param resposta the resposta to set
     */
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    /**
     * @return the respostaLista
     */
    public ArrayList<String> getRespostaLista() {
        return respostaLista;
    }

    /**
     * @param respostaLista the respostaLista to set
     */
    public void setRespostaLista(ArrayList<String> respostaLista) {
        this.respostaLista = respostaLista;
    }
}
