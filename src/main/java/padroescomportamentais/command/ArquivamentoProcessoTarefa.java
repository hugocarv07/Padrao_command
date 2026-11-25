package padroescomportamentais.command;

public class ArquivamentoProcessoTarefa implements Tarefa {

    private Processo processo;
    private String situacaoAnterior;

    public ArquivamentoProcessoTarefa(Processo processo) {
        this.processo = processo;
    }

    public void executar() {
        this.situacaoAnterior = this.processo.getSituacao();
        this.processo.arquivar();
    }

    public void cancelar() {
        if (this.situacaoAnterior != null) {
            this.processo.restaurarSituacao(this.situacaoAnterior);
        }
    }
}
