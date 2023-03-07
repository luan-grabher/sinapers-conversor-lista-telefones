package Model;

import Model.Entity.Contato;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import main.Arquivo;

public class Arquivo_Model {

    private final Long numeroPadrao = new Long(90000000);

    private final int col_Nome_start = 0;
    private final int col_Nome_final = 61;

    private final int col_Telefone_start = 62;
    private final int col_Telefone_final = 93;

    private final int col_Fone_start = 103;
    private final int col_Fone_final = 136;

    private final int col_Celular_start = 134;

    private List<Contato> contatos = new ArrayList<>();
    private String filePath;

    public boolean LerArquivo(String localArquivo) {
        try {
            filePath = new File(localArquivo).getAbsolutePath();
            String texto = Arquivo.ler(localArquivo);
            String[] texto_linhas = texto.split("\n");
            //Percorre Linhas arquivo
            for (String texto_linha : texto_linhas) {
                if (texto_linha.length() > col_Fone_start + 10) {
                    //Puxa valores
                    texto_linha = texto_linha.trim();

                    String nome = "";
                    String telefone = "";
                    String fone = "";
                    String celular = "";

                    try {
                        int size = texto_linha.length();

                        nome = texto_linha.substring(col_Nome_start, col_Nome_final).trim();
                        if (size >= col_Telefone_start) {
                            telefone = texto_linha.substring(col_Telefone_start, size < col_Telefone_final ? size : col_Telefone_final).replaceAll("\\D+", "").trim();
                            if (size >= col_Fone_start) {
                                fone = texto_linha.substring(col_Fone_start, size < col_Fone_final ? size : col_Fone_final).replaceAll("\\D+", "").trim();
                                if (size >= col_Celular_start) {
                                    celular = texto_linha.substring(col_Celular_start).replaceAll("\\D+", "").trim();
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                    if (nomeValido(nome)) {
                        telefone = formataNumero(telefone);
                        fone = formataNumero(fone);
                        celular = formataNumero(celular);

                        String num = !celular.contains(numeroPadrao.toString()) ? celular : !fone.contains(numeroPadrao.toString()) ? fone : !telefone.contains(numeroPadrao.toString()) ? telefone : "";

                        contatos.add(new Contato(nome, num));
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean nomeValido(String nome) {
        String nomeLc = nome.toLowerCase();

        return !(nomeLc.equals("")
                | nomeLc.contains("contribuinte")
                | nomeLc.contains("/")
                | nomeLc.contains(":"));
    }

    private String formataNumero(String numero) {
        Long nLong = Long.valueOf(0);
        String nStr = "";
        try {
            nLong = Long.valueOf(numero); //remove zeros a frente
            nStr = nLong.toString();
        } catch (Exception e) {
        }
        Integer ddd = 51;
        Long num = numeroPadrao;

        switch (nStr.length()) {
            case 8:
            case 9:
                num = nLong;
                break;
            case 10:
            case 11:
                ddd = Integer.valueOf(nStr.substring(0, 2));
                num = Long.valueOf(nStr.substring(2, nStr.length()));
                break;
        }

        if (nStr.length() > 11) {
            ddd = Integer.valueOf(nStr.substring(0, 2));
            num = Long.valueOf(nStr.substring(2, 11));
        }

        if (ddd >= 80) {
            try {
                num = Long.valueOf(ddd + num.toString().substring(0, 7));
            } catch (Exception e) {
                num = numeroPadrao;
            }
            ddd = 51;
        }

        if (num <= 99999999 & num >= 70000000) {
            num += numeroPadrao*10;
        }

        return "" + ddd + "" + num;
    }

    public boolean salvarArquivo() {
        String textoFinal = "";
        for (int i = 0; i < contatos.size(); i++) {
            Contato contato = contatos.get(i);
            textoFinal += "".equals(textoFinal) ? "" : "\n";
            textoFinal += contato.getNome() + ";" + contato.getTelefone();
        }

        return Arquivo.salvar(filePath.replaceAll(".txt", ".csv").replaceAll(".TXT", " .csv"), textoFinal);
    }
}
