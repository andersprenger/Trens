package trabalho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Composicao {
    private int id;
    private ArrayList<ElementoComposicao> composicao;

    public Composicao (int id) {
        this.id = id;
        this.composicao = new ArrayList<>();
    }

    public Composicao (JSONObject jsonObject) {
        this.id = jsonObject.getInt("id");
        this.composicao = new ArrayList<>();

        JSONArray arrayComposicao = jsonObject.getJSONArray("array");
        for (int i = 0; i < arrayComposicao.length(); i++) {
            JSONObject e = (JSONObject) arrayComposicao.get(i);
            if (e.getBoolean("ehLocomotiva")) {
                composicao.add(new Locomotiva(e));
            } else {
                composicao.add(new Vagao(e));
            }
        }
    }

    public JSONObject toJSONObject () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());

        JSONArray arrayComposicao = new JSONArray();
        for (ElementoComposicao e : composicao) {
            arrayComposicao.put(e.toJSONObject());
        }

        jsonObject.put("elementosComposicao", arrayComposicao);
        return jsonObject;
    }

    public int getId() {
        return id;
    }

    public int getQtdadeLocomotivas() {
        int count = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Locomotiva) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getQtdadeVagoes() {
        int count = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Vagao) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private ElementoComposicao getElementoComposicao (int posicao) {
        if (posicao >= 0 && posicao < composicao.size()) {
            return composicao.get(posicao);
        }
        return null;
    }

    public Locomotiva getLocomotiva (int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        if (e == null) {
            return null;
        }
        return e instanceof Locomotiva ? (Locomotiva) e : null;
    }

    public Vagao getVagao (int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        if (e == null) {
            return null;
        }
        return e instanceof Vagao ? (Vagao) e : null;
    }

    private int maximoVagoes () {
        int maximoVagoes = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Locomotiva) {
                maximoVagoes += ((Locomotiva) e).getQtdadeMaxVagoes();
            } else {
                break;
            }
        }
        return maximoVagoes;
    }

    private double pesoMaximo () {
        double pesoMaximo = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Locomotiva) {
                pesoMaximo += ((Locomotiva) e).getPesoMaximo();
            } else {
                break;
            }
        }
        return pesoMaximo;
    }

    private double pesoAtual () {
        double pesoAtual = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Vagao) {
                pesoAtual += ((Vagao) e).getCapacidadeCarga();
            }
        }
        return pesoAtual;
    }

    private boolean engataElemento(ElementoComposicao e) {
        if (e.getIdComposicao() != -1 && e.getIdComposicao() != this.getId()) {
            return false;
        } else {
            e.setIdComposicao(this);
            composicao.add(e);
            return true;
        }
    }

    public boolean engataLocomotiva(Locomotiva l) {
        if (getQtdadeVagoes() > 0) {
            return false;
        } else {
            return engataElemento(l);
        }
    }

    public boolean engataVagao (Vagao v) {
        if (getQtdadeLocomotivas() == 0) {
            return false;
        } else if (getQtdadeVagoes() == maximoVagoes()) {
            return false;
        } else if (pesoAtual() + v.getCapacidadeCarga() > pesoMaximo()) {
            return false;
        } else {
            return engataElemento(v);
        }
    }

    private boolean desengataElemento (ElementoComposicao e) {
        if (composicao.get(composicao.size() - 1) == e) {
            if (composicao.remove(e)) {
                e.setIdComposicao(null);
                return true;
            }
        }
        return false;
    }

    public boolean desengataLocomotiva (Locomotiva l) {
        return desengataElemento(l);
    }

    public boolean desengataVagao (Vagao v) {
        return desengataElemento(v);
    }
}
