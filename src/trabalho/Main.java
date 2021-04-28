package trabalho;

public class Main {

    public static void main(String[] args) {
	    CadastroElementoComposicao ce = new CadastroElementoComposicao();

		/*
		ce.cadastra(new Locomotiva(1,200d,21));
	    ce.cadastra(new Locomotiva(2,150d,15));
	    ce.cadastra(new Locomotiva(3,250d,16));
	    ce.cadastra(new Locomotiva(4,180d,18));
	    ce.cadastra(new Locomotiva(5,190d,20));

	    ce.cadastra(new Vagao(100, 10));
	    ce.cadastra(new Vagao(200, 8));
	    ce.cadastra(new Vagao(300, 12));
	    ce.cadastra(new Vagao(400, 14));
	    ce.cadastra(new Vagao(500, 8));
	    ce.cadastra(new Vagao(600, 21));
	    ce.cadastra(new Vagao(700, 32));
	    ce.cadastra(new Vagao(800, 13));
	    ce.cadastra(new Vagao(900, 15));
	    ce.cadastra(new Vagao(1000, 10));
	    ce.cadastra(new Vagao(1100, 18));
	    ce.cadastra(new Vagao(1200, 20));
	    ce.cadastra(new Vagao(1300, 10));
	    */

	    //ce.persiste();
		ce.carrega();
		System.out.println(ce);
    }
}
