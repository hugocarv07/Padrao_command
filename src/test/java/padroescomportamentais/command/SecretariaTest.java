package padroescomportamentais.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecretariaTest {

    Secretaria secretaria;
    Processo processo;

    @BeforeEach
    void setUp() {
        secretaria = new Secretaria();
        processo = new Processo("0001");
    }

    @Test
    void deveSuspenderProcesso() {
        Tarefa suspensao = new SuspensaoProcessoTarefa(processo);
        secretaria.executarTarefa(suspensao);

        assertEquals("Suspenso", processo.getSituacao());
    }

    @Test
    void deveArquivarProcesso() {
        Tarefa arquivamento = new ArquivamentoProcessoTarefa(processo);
        secretaria.executarTarefa(arquivamento);

        assertEquals("Arquivado", processo.getSituacao());
    }

    @Test
    void deveReativarProcesso() {
        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(processo));
        secretaria.executarTarefa(new ReativacaoProcessoTarefa(processo));

        assertEquals("Em andamento", processo.getSituacao());
    }

    @Test
    void deveCancelarArquivamentoVoltandoParaSuspenso() {
        Tarefa suspensao = new SuspensaoProcessoTarefa(processo);
        Tarefa arquivamento = new ArquivamentoProcessoTarefa(processo);

        secretaria.executarTarefa(suspensao);
        secretaria.executarTarefa(arquivamento);

        secretaria.cancelarUltimaTarefa();

        assertEquals("Suspenso", processo.getSituacao());
    }

    @Test
    void deveCancelarReativacaoVoltandoParaArquivado() {
        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(processo));
        secretaria.executarTarefa(new ReativacaoProcessoTarefa(processo));

        secretaria.cancelarUltimaTarefa();

        assertEquals("Arquivado", processo.getSituacao());
    }

    @Test
    void deveCancelarTarefasEmOrdemInversa() {
        secretaria.executarTarefa(new SuspensaoProcessoTarefa(processo));
        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(processo));
        secretaria.executarTarefa(new ReativacaoProcessoTarefa(processo));

        secretaria.cancelarUltimaTarefa();
        assertEquals("Arquivado", processo.getSituacao());
        secretaria.cancelarUltimaTarefa();
        assertEquals("Suspenso", processo.getSituacao());
        secretaria.cancelarUltimaTarefa();
        assertEquals("Em andamento", processo.getSituacao());
    }

    @Test
    void cancelarSemTarefasNaoFazNada() {
        secretaria.cancelarUltimaTarefa();

        assertEquals("Em andamento", processo.getSituacao());
        assertEquals(0, secretaria.quantidadeTarefas());
    }

    @Test
    void cancelarRemoveATarefaDoHistorico() {
        secretaria.executarTarefa(new SuspensaoProcessoTarefa(processo));
        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(processo));
        assertEquals(2, secretaria.quantidadeTarefas());

        secretaria.cancelarUltimaTarefa();

        assertEquals(1, secretaria.quantidadeTarefas());
    }

    @Test
    void cancelarMaisVezesQueTarefasMantemOEstadoInicial() {
        secretaria.executarTarefa(new SuspensaoProcessoTarefa(processo));

        secretaria.cancelarUltimaTarefa();
        secretaria.cancelarUltimaTarefa();
        secretaria.cancelarUltimaTarefa();

        assertEquals("Em andamento", processo.getSituacao());
        assertEquals(0, secretaria.quantidadeTarefas());
    }

    @Test
    void tarefaNulaEhRejeitada() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> secretaria.executarTarefa(null));

        assertEquals("Tarefa inválida", ex.getMessage());
        assertEquals(0, secretaria.quantidadeTarefas());
    }

    @Test
    void secretariaPodeAtenderProcessosDiferentes() {
        Processo outro = new Processo("0002");

        secretaria.executarTarefa(new SuspensaoProcessoTarefa(processo));
        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(outro));
        secretaria.cancelarUltimaTarefa();

        assertEquals("Suspenso", processo.getSituacao());
        assertEquals("Em andamento", outro.getSituacao());
    }
}
