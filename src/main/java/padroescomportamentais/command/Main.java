package padroescomportamentais.command;

public class Main {

    public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();
        Processo processo = new Processo("0001234-56.2026.8.13.0145");

        secretaria.executarTarefa(new SuspensaoProcessoTarefa(processo));
        System.out.println("Após suspensão: " + processo.getSituacao());

        secretaria.executarTarefa(new ArquivamentoProcessoTarefa(processo));
        System.out.println("Após arquivamento: " + processo.getSituacao());

        secretaria.cancelarUltimaTarefa();
        System.out.println("Arquivamento cancelado: " + processo.getSituacao());

        secretaria.cancelarUltimaTarefa();
        System.out.println("Suspensão cancelada: " + processo.getSituacao());
    }
}
