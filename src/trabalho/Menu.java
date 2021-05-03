package trabalho;

import java.util.Scanner;

public class Menu {
    private Scanner in;
    private CadastroElementosComposicao ce;
    private CadastroComposicoes cc;

    public Menu() {
        in = new Scanner(System.in);
        ce = new CadastroElementosComposicao();
        cc = new CadastroComposicoes(ce);
        ce.carrega();
        cc.carrega();
        if (ce.quantidade() == 0) {
            System.out.println("Um novo cadastro dos elementos de composição no pátio foi criado!");
            popularCE();
        }
    }

    public void menu() {
        boolean estouNoMenu = true;

        while (estouNoMenu) {
            System.out.println("Digite um numero correspondente com o que deseja fazer: ");
            System.out.println("1 - Criar uma composição");
            System.out.println("2 - Editar uma composição");
            System.out.println("3 - Listar todas as composições");
            System.out.println("4 - Desfazer uma composição");
            System.out.println("5 - Encerrar o programa");
            int numeroOpcao = in.nextInt();

            switch (numeroOpcao) {
                case 1 -> criarComposicao();
                case 2 -> editarComposicao();
                case 3 -> listarComposicao();
                case 4 -> desfazerComposicao();
                case 5 -> {
                    System.out.println("Encerrando o programa");
                    estouNoMenu = false;
                    cc.persiste();
                    ce.persiste();
                }
                default -> System.out.println("Opção invalida, tente novamente...");
            }
        }
    }

    private void criarComposicao() {
        int idDaComposicao;
        while (true){
            System.out.println("Digite o identificador da composição que deseja criar: ");
            idDaComposicao = in.nextInt();
            if (cc.getPorId(idDaComposicao) != null) {
                System.out.println("Já foi cadastrada uma composição com esse id, favor escolher uma nova opção.");
            } else {
                break;
            }
        }
        ce.listarLocomotivaLivre();
        Locomotiva locomotiva;
        while (true) {
            System.out.println("Digite o identificador da locomotiva que deseja usar para criar a composição: ");
            int idDaLocomotiva = in.nextInt();
            ElementoComposicao e = ce.getPorId(idDaLocomotiva);
            if (e instanceof Locomotiva && e.getIdComposicao() == -1) {
                locomotiva = (Locomotiva) e;
                break;
            } else {
                System.out.print(e instanceof Locomotiva? "Id invalido, " : "Locomotiva já está numa composição, ");
                System.out.println("tente novamente.");
            }
        }

        Composicao composicao = new Composicao(idDaComposicao);    //cria a composição
        cc.cadastra(composicao);

        composicao.engataLocomotiva(locomotiva);                    //engata a locomotiva na composição
    }

    private void editarComposicao() {
        if (cc.quantidade() == 0) {
            System.out.println("Não há composições no cadastro.");
            return;
        }
        System.out.println("Digite o identificador da composição que deseja editar: ");
        int idDaComposicao = in.nextInt();
        if (cc.getPorId(idDaComposicao) == null) {
            System.out.println("Composição não encontrada.");
            return;
        }
        boolean editandoComposicao = true;
        while (editandoComposicao) {
            System.out.println("Digite um numero correspondente com o que deseja fazer: ");
            System.out.println("1 - Inserir locomotiva");
            System.out.println("2 - Inserir vagão");
            System.out.println("3 - Remover o ultimo elemento da composição");
            System.out.println("4 - Listar locomotivas livres");
            System.out.println("5 - Listar vagões livres");
            System.out.println("6 - Encerrar a edição da composição");

            int numeroOpcao = in.nextInt();

            switch (numeroOpcao) {
                case 1 -> {
                    ce.listarLocomotivaLivre();
                    System.out.println("Digite o id da locomotiva");
                    int idDaLocomotiva = in.nextInt();
                    cc.getPorId(idDaComposicao).engataLocomotiva((Locomotiva) ce.getPorId(idDaLocomotiva));
                }
                case 2 -> {
                    ce.listarVagaoLivre();
                    System.out.println("Digite o id do vagão");
                    int idDoVagao = in.nextInt();
                    cc.getPorId(idDaComposicao).engataVagao((Vagao) ce.getPorId(idDoVagao));
                }
                case 3 -> {
                    boolean b = cc.getPorId(idDaComposicao).desengataUltimoElemento();//remove o ultimo elemento da composição
                    System.out.println(b ? "Ultimo elemento removido." : "Não foi possível remover o ultimo elemento.");
                }
                case 4 -> ce.listarLocomotivaLivre();
                case 5 -> ce.listarVagaoLivre();
                case 6 -> editandoComposicao = false;
                default -> System.out.println("Digitou um valor de edição errado, tente novamente");
            }
        }

    }

    private void listarComposicao() {
        if (cc.quantidade() == 0) {
            System.out.println("Não existem composições no cadastro.");
        } else {
            System.out.println(cc);
        }
    }

    private void desfazerComposicao() {
        if (cc.quantidade() == 0) {
            System.out.println("Não há composições no cadastro.");
        }
        System.out.println(cc);
        System.out.println("Digite o id da composição que deseja desfazer: ");
        int idDaComposicaoDesfazer = in.nextInt();
        boolean removeu = cc.removePorId(idDaComposicaoDesfazer);
        System.out.println(removeu? "Composição removida." : " Não foi possível remover a composição.");
    }

    private void popularCE() {
        ce.cadastra(new Locomotiva(1, 200000d, 21));
        ce.cadastra(new Locomotiva(2, 150000d, 15));
        ce.cadastra(new Locomotiva(3, 250000d, 16));
        ce.cadastra(new Locomotiva(4, 180000d, 18));
        ce.cadastra(new Locomotiva(5, 190000d, 20));

        ce.cadastra(new Vagao(100, 10000));
        ce.cadastra(new Vagao(200, 80000));
        ce.cadastra(new Vagao(300, 12000));
        ce.cadastra(new Vagao(400, 14000));
        ce.cadastra(new Vagao(500, 8000));
        ce.cadastra(new Vagao(600, 21000));
        ce.cadastra(new Vagao(700, 32000));
        ce.cadastra(new Vagao(800, 13000));
        ce.cadastra(new Vagao(900, 15000));
        ce.cadastra(new Vagao(1000, 10000));
        ce.cadastra(new Vagao(1100, 18000));
        ce.cadastra(new Vagao(1200, 20000));
        ce.cadastra(new Vagao(1300, 10000));

        ce.cadastra(new VagaoPassageiro(19000, 50));
        ce.cadastra(new VagaoPassageiro(10000, 70));
        ce.cadastra(new VagaoPassageiro(11000, 68));
        ce.cadastra(new VagaoPassageiro(12000, 50));
        ce.cadastra(new VagaoPassageiro(13000, 75));

        ce.persiste();
    }
}