package padroescomportamentais.command;

public class Processo {

    private String numero;
    private String situacao;

    public Processo(String numero) {
        this.numero = numero;
        this.situacao = "Em andamento";
    }

    public String getNumero() {
        return numero;
    }

    public String getSituacao() {
        return situacao;
    }

    public void suspender() {
        this.situacao = "Suspenso";
    }

    public void arquivar() {
        this.situacao = "Arquivado";
    }

    public void reativar() {
        this.situacao = "Em andamento";
    }

    public void restaurarSituacao(String situacao) {
        this.situacao = situacao;
    }
}
