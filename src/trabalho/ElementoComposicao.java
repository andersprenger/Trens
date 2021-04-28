package trabalho;

import org.json.JSONObject;

public abstract class ElementoComposicao {
    private int id;
    private int idComposicao;

    public ElementoComposicao(int id, int idComposicao) {
        this.id = id;
        this.idComposicao = idComposicao;
    }

    public ElementoComposicao(int id) {
        this.id = id;
        this.idComposicao = -1;
    }

    public ElementoComposicao(JSONObject jsonObject) {
        this.id = jsonObject.getInt("id");
        this.idComposicao = jsonObject.getInt("idComposicao");
    }

    public int getId() {
        return id;
    }

    public int getIdComposicao() {
        return idComposicao;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("idComposicao", getIdComposicao());
        return jsonObject;
    }

    @Override
    public abstract String toString();
}
