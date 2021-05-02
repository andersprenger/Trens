package trabalho;

import java.util.Scanner;

public class Menu {
    private Scanner in;
    private CadastroElementosComposicao cec;
    private CadastroComposicoes cc;

    public Menu() {
        in = new Scanner(System.in);
        cec = new CadastroElementosComposicao();
        cc = new CadastroComposicoes(cec);
        cc.carrega();
        cec.carrega();
    }

    public void startaMenu() {

        boolean estouNoMenu = true;

        while (estouNoMenu) {
            System.out.println("Digite um numero correspondente com o que deseja fazer: ");
            System.out.println("1 - Criar uma composicao");
            System.out.println("2 - Editar uma composicao");
            System.out.println("3 - Listar todas as composicoes");
            System.out.println("4 - Desfazer uma composicao");
            System.out.println("5 - Encerrar o programa");
            int desejoFazerNoMenu = in.nextInt();

            switch (desejoFazerNoMenu) {
                case 1:
                    System.out.println("Digite o identificador da composicao que deseja criar: ");
                    int idDaComposicao = in.nextInt();

                    cec.listaLocomotivaLivre();

                    System.out.println("Decida qual a locomotiva que deseja engatar como a primeira da composicao pelo id dela: ");
                    int idDaLocomotiva = in.nextInt();


                    Locomotiva locomotiva = (Locomotiva) cec.getPorId(idDaLocomotiva);

                    criaComposicao(idDaComposicao, locomotiva);

                    break;
                case 2:
                    System.out.println("Digite o identificador da composicao que deseja editar: ");
                    int idDaComposicaoEditavel = in.nextInt();

                    editaComposicao(idDaComposicaoEditavel);

                    break;
                case 3:
                    System.out.println(cc.toString());

                    break;
                case 4:
                    System.out.println("Digite o id da composicao que deseja desfazer");
                    int idDaComposicaoDesfazer = in.nextInt();

                    desfazComposicao(idDaComposicaoDesfazer);

                    break;
                case 5:
                    System.out.println("Encerrando o programa");
                    estouNoMenu = false;
                    cc.persiste();
                    cec.persiste();
                    break;
                default:
                    System.out.println("Digitou uma opcao inexistente no menu, tente novamente");
                    break;
            }
        }
    }

    public void criaComposicao(int idDaComposicao, Locomotiva locomotiva) {
        Composicao comp = new Composicao(idDaComposicao);    //cria a composicao
        cc.cadastra(comp);

        comp.engataLocomotiva(locomotiva);                    //engata a locomotiva na composicao
    }

    public void editaComposicao(int idDaComposicao) {
        boolean editandoComposicao = true;
        while (editandoComposicao) {
            System.out.println("Digite um numero correspondente com o que deseja fazer: ");
            System.out.println("1 - Inserir locomotiva");
            System.out.println("2 - Inserir vagao");
            System.out.println("3 - Remover o ultimo elemento da composicao");
            System.out.println("4 - Listar locomotivas livres");
            System.out.println("5 - Listar vagoes livres");
            System.out.println("6 - Encerrar a edicao da composicao");

            int desejoEditarComposicao = in.nextInt();

            switch (desejoEditarComposicao) {
                case 1:
                    cec.listaLocomotivaLivre();
                    System.out.println("Digite o id da locomotiva");
                    int idDaLocomotiva = in.nextInt();
                    cc.getPorId(idDaComposicao).engataLocomotiva((Locomotiva) cec.getPorId(idDaLocomotiva));


                    break;
                case 2:
                    cec.listaVagaoLivre();
                    System.out.println("Digite o id do vagao");
                    int idDoVagao = in.nextInt();

                    cc.getPorId(idDaComposicao).engataVagao((Vagao) cec.getPorId(idDoVagao));

                    break;
                case 3:
                    cc.getPorId(idDaComposicao).desengataUltimoElemento();      //remove o ultimo elemento da composicao

                    break;
                case 4:
                    cec.listaLocomotivaLivre();

                    break;
                case 5:
                    cec.listaVagaoLivre();

                    break;
                case 6:
                    editandoComposicao = false;
                    break;
                default:
                    System.out.println("Digitou um valor de edicao errado, tente novamente");
                    break;
            }
        }

    }

    public void desfazComposicao(int idDaComposicao) {
        cc.removePorId(idDaComposicao);
    }
}