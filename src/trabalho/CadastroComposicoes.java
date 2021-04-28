package trabalho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CadastroComposicoes {
    private ArrayList<Composicao> composicoes;

    public CadastroComposicoes () {
        composicoes = new ArrayList<>();
    }

    public void cadastra (Composicao c) {
        composicoes.add(c);
    }

    public int quantidade () {
        return composicoes.size();
    }

    public Composicao getPorPosicao (int index) {
        if (index < 0) {
            return null;
        } else if (index >= quantidade()) {
            return null;
        } else {
            return composicoes.get(index);
        }
    }

    public Composicao getPorId (int id) {
        for (Composicao c : composicoes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean removePorId (int id) {
        for (Composicao c : composicoes) {
            if (c.getId() == id) {
                composicoes.remove(c);
                int ultimaPosicao = c.getQtdadeVagoes() + c.getQtdadeLocomotivas() - 1;
                for (int i = ultimaPosicao; i >= 0; i--) {
                    if (c.getVagao(i) != null) {
                        c.desengataVagao(c.getVagao(i));
                    } else {
                        c.desengataLocomotiva(c.getLocomotiva(i));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public JSONObject toJSONObject () {
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();
        for (Composicao c : composicoes) {
            array.put(c.toJSONObject());
        }

        jsonObject.put("composicoes", array);

        return jsonObject;
    }
}
