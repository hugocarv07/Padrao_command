package padroescomportamentais.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TarefaTest {

    Processo processo;

    @BeforeEach
    void setUp() {
        processo = new Processo("0001");
    }

    @Test
    void processoComecaEmAndamento() {
        assertEquals("0001", processo.getNumero());
        assertEquals("Em andamento", processo.getSituacao());
    }

    @Test
    void tarefaExecutarEcancelarDiretamente() {
        Tarefa suspensao = new SuspensaoProcessoTarefa(processo);

        suspensao.executar();
        assertEquals("Suspenso", processo.getSituacao());

        suspensao.cancelar();
        assertEquals("Em andamento", processo.getSituacao());
    }

    @Test
    void cancelarAntesDeExecutarNaoAlteraOProcesso() {
        processo.arquivar();

        new SuspensaoProcessoTarefa(processo).cancelar();
        new ArquivamentoProcessoTarefa(processo).cancelar();
        new ReativacaoProcessoTarefa(processo).cancelar();

        assertEquals("Arquivado", processo.getSituacao());
    }

    @Test
    void cancelarRestauraASituacaoQueEstavaAntesDaExecucao() {
        processo.suspender();
        Tarefa arquivamento = new ArquivamentoProcessoTarefa(processo);

        arquivamento.executar();
        arquivamento.cancelar();

        assertEquals("Suspenso", processo.getSituacao());
    }

    @Test
    void cadaTarefaGuardaASuaPropriaSituacaoAnterior() {
        Tarefa suspensao = new SuspensaoProcessoTarefa(processo);
        Tarefa arquivamento = new ArquivamentoProcessoTarefa(processo);

        suspensao.executar();
        arquivamento.executar();

        arquivamento.cancelar();
        assertEquals("Suspenso", processo.getSituacao());
        suspensao.cancelar();
        assertEquals("Em andamento", processo.getSituacao());
    }
}
