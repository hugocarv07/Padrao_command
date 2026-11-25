package padroescomportamentais.command;

public class ReativacaoProcessoTarefa implements Tarefa {

    private Processo processo;
    private String situacaoAnterior;

    public ReativacaoProcessoTarefa(Processo processo) {
        this.processo = processo;
    }

    public void executar() {
        this.situacaoAnterior = this.processo.getSituacao();
        this.processo.reativar();
    }

    public void cancelar() {
        if (this.situacaoAnterior != null) {
            this.processo.restaurarSituacao(this.situacaoAnterior);
        }
    }
}
