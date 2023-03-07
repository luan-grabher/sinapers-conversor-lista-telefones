
package Control;

import Model.Arquivo_Model;
import javax.swing.JOptionPane;

public class Arquivo_Control {
    public static void converter(String localArquivo){
        //Valida arquivo
        if(SelectorOS.Arquivo.verifica(localArquivo, "txt")){
            String mensagem = "";
            Arquivo_Model model = new Arquivo_Model();
            if(model.LerArquivo(localArquivo)){
                if(model.salvarArquivo()){
                    mensagem = "Sucesso! O arquivo convertido foi salvo na mesma pasta do arquivo original!";
                }else{
                    mensagem = "Erro ao salvar o arquivo, você está com oa rquivo aberto?";
                }
            }else{
                mensagem = "Ocorreu um erro ao ler o arquivo.";
            }
            JOptionPane.showMessageDialog( null ,
                    mensagem ,
                    "Informação" , JOptionPane.INFORMATION_MESSAGE );
        }
    }
    
    
}
