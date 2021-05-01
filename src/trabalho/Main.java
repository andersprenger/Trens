package trabalho;

public class Main {

    public static void main(String[] args) {
	    CadastroElementosComposicao ce = new CadastroElementosComposicao();
	    CadastroComposicoes cc = new CadastroComposicoes(ce);
/*
		ce.cadastra(new Locomotiva(1,200000d,21));
	    ce.cadastra(new Locomotiva(2,150000d,15));
	    ce.cadastra(new Locomotiva(3,250000d,16));
	    ce.cadastra(new Locomotiva(4,180000d,18));
	    ce.cadastra(new Locomotiva(5,190000d,20));

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

	    ce.persiste();*/
		ce.carrega();
		//cc.carrega(ce);

		Composicao c1 = new Composicao(1);
		c1.engataLocomotiva((Locomotiva) ce.getPorId(1));
		c1.engataVagao((Vagao) ce.getPorId(1100));
		c1.engataVagao((Vagao) ce.getPorId(11000));
		c1.engataVagao((Vagao) ce.getPorId(12000));
		cc.cadastra(c1);

		Composicao c2 = new Composicao(2);
		c2.engataLocomotiva((Locomotiva) ce.getPorId(2));
		c2.engataVagao((Vagao) ce.getPorId(500));
		c2.engataVagao((Vagao) ce.getPorId(19000));
		c2.engataVagao((Vagao) ce.getPorId(13000));
		cc.cadastra(c2);

		//System.out.println(ce);
		System.out.println(cc);
		cc.persiste();

    }
}
