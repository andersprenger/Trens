package trabalho;

import org.json.JSONObject;

public class Locomotiva extends ElementoComposicao {
    private double pesoMaximo;
    private int qtdadeMaxVagoes;

    public Locomotiva(int id, int idComposicao, double pesoMaximo, int qtdadeMaxVagoes) {
        super(id, idComposicao);
        this.pesoMaximo = pesoMaximo;
        this.qtdadeMaxVagoes = qtdadeMaxVagoes;
    }

    public Locomotiva(int id, double pesoMaximo, int qtdadeMaxVagoes) {
        super(id);
        this.pesoMaximo = pesoMaximo;
        this.qtdadeMaxVagoes = qtdadeMaxVagoes;
    }

    public Locomotiva(JSONObject jsonObject) {
        super(jsonObject);
        this.pesoMaximo = jsonObject.getDouble("pesoMaximo");
        this.qtdadeMaxVagoes = jsonObject.getInt("qtdadeMaxVagoes");
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public int getQtdadeMaxVagoes() {
        return qtdadeMaxVagoes;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        jsonObject.put("pesoMaximo", getPesoMaximo());
        jsonObject.put("qtdadeMaxVagoes", getQtdadeMaxVagoes());
        return jsonObject;
    }
}
