package padroescomportamentais.command;

import java.util.ArrayList;
import java.util.List;

public class Secretaria {

    private List<Tarefa> tarefas = new ArrayList<Tarefa>();

    public void executarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa inválida");
        }
        this.tarefas.add(tarefa);
        tarefa.executar();
    }

    public void cancelarUltimaTarefa() {
        if (tarefas.size() != 0) {
            Tarefa tarefa = this.tarefas.get(this.tarefas.size() - 1);
            tarefa.cancelar();
            this.tarefas.remove(this.tarefas.size() - 1);
        }
    }

    public int quantidadeTarefas() {
        return this.tarefas.size();
    }

}
