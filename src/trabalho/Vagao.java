package trabalho;

import org.json.JSONObject;

public class Vagao extends ElementoComposicao {
    private int capacidadeCarga;

    public Vagao(int id, int idComposicao, int capacidadeCarga) {
        super(id, idComposicao);
        this.capacidadeCarga = capacidadeCarga;
    }

    public Vagao(int id, int capacidadeCarga) {
        super(id);
        this.capacidadeCarga = capacidadeCarga;
    }

    public Vagao(JSONObject jsonObject) {
        super(jsonObject);
        this.capacidadeCarga = jsonObject.getInt("capacidadeCarga");
    }

    public int getCapacidadeCarga() {
        return capacidadeCarga;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        jsonObject.put("capacidadeCarga", getCapacidadeCarga());
        return jsonObject;
    }

    @Override
    public String toString() {
        return "Vagao{" +
                "id=" + getId() +
                ", idComposicao=" + getIdComposicao() +
                ", capacidadeCarga=" + capacidadeCarga +
                '}';
    }
}
