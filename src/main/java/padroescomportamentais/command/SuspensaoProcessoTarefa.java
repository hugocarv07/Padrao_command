package padroescomportamentais.command;

public class SuspensaoProcessoTarefa implements Tarefa {

    private Processo processo;
    private String situacaoAnterior;

    public SuspensaoProcessoTarefa(Processo processo) {
        this.processo = processo;
    }

    public void executar() {
        this.situacaoAnterior = this.processo.getSituacao();
        this.processo.suspender();
    }

    public void cancelar() {
        if (this.situacaoAnterior != null) {
            this.processo.restaurarSituacao(this.situacaoAnterior);
        }
    }
}
