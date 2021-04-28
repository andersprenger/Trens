package trabalho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CadastroElementoComposicao {
    private ArrayList<ElementoComposicao> elementosComposicao;

    public CadastroElementoComposicao () {
        this.elementosComposicao = new ArrayList<>();
    }

    public void cadastra (ElementoComposicao e) {
        elementosComposicao.add(e);
    }

    public int quantidade () {
        return elementosComposicao.size();
    }

    public ElementoComposicao getPorPosicao (int index) {
        if (index < 0) {
            return null;
        } else if (index >= quantidade()) {
            return null;
        } else {
            return elementosComposicao.get(index);
        }
    }

    public ElementoComposicao getPorId (int id) {
        for (ElementoComposicao e : elementosComposicao) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public JSONObject toJSONObject () {
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();
        for (ElementoComposicao e : elementosComposicao) {
            array.put(e.toJSONObject());
        }

        jsonObject.put("elementosComposicao", array);

        return jsonObject;
    }
}
