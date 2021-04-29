package trabalho;

import org.json.JSONObject;

public class VagaoPassageiro extends Vagao {
    private int quantidadePassageiros;

    public VagaoPassageiro(int id, int idComposicao, int quantidadePassageiros) {
        super(id, idComposicao, quantidadePassageiros * 70);
        this.quantidadePassageiros = quantidadePassageiros;
    }

    public VagaoPassageiro(int id, int quantidadePassageiros) {
        super(id, quantidadePassageiros * 70);
        this.quantidadePassageiros = quantidadePassageiros;
    }

    public VagaoPassageiro(JSONObject jsonObject) {
        super(jsonObject);
        this.quantidadePassageiros = jsonObject.getInt("quantidadePassageiro");
    }

    public int getQuantidadePassageiros() {
        return quantidadePassageiros;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        jsonObject.put("quantidadePassageiro", getQuantidadePassageiros());
        return jsonObject;
    }

    @Override
    public String toString() {
        return "VagaoPassageiro {" +
                "id=" + getId() +
                ", idComposicao=" + getIdComposicao() +
                ", capacidadeCarga=" + getCapacidadeCarga() +
                ", quantidadePassageiros=" + getQuantidadePassageiros() +
                '}';
    }
}
