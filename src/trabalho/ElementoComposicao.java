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

    public int getId() {
        return id;
    }

    public int getIdComposicao() {
        return idComposicao;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("composicao", getIdComposicao());
        return jsonObject;
    }
}
