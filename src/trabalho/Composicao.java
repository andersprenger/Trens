package trabalho;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Composicao {
    private int id;
    private ArrayList<ElementoComposicao> composicao;

    public Composicao(int id) {
        this.id = id;
        this.composicao = new ArrayList<>();
    }

    public Composicao(JSONObject jsonObject) {
        this.id = jsonObject.getInt("id");
        this.composicao = new ArrayList<>();
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

    private ElementoComposicao getElementoComposicao(int posicao) {
        if (posicao >= 0 && posicao < composicao.size()) {
            return composicao.get(posicao);
        }
        return null;
    }

    public Locomotiva getLocomotiva(int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        return e instanceof Locomotiva ? (Locomotiva) e : null;
    }

    public Vagao getVagao(int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        return e instanceof Vagao ? (Vagao) e : null;
    }

    private int maximoVagoes() {
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

    private double pesoMaximo() {
        double pesoMaximo = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Locomotiva) {
                pesoMaximo += ((Locomotiva) e).getPesoMaximo();
            } else {
                break;
            }
        }
        pesoMaximo *= (double) (10 - (this.getQtdadeVagoes() - 1)) / 10.0;
        return pesoMaximo;
    }

    private double pesoAtual() {
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

    public boolean engataVagao(Vagao v) {
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

    private boolean desengataElemento(ElementoComposicao e) {
        if (e != null && composicao.get(composicao.size() - 1).getId() == e.getId()) {
            if (composicao.remove(e)) {
                e.setIdComposicao(null);
                return true;
            }
        }
        return false;
    }

    public boolean desengataLocomotiva(Locomotiva l) {
        return desengataElemento(l);
    }

    public boolean desengataVagao(Vagao v) {
        return desengataElemento(v);
    }

    public boolean desengataUltimoElemento() {
        ElementoComposicao e = this.getElementoComposicao(composicao.size() - 1);
        return desengataElemento(e);
    }

    public void desfazer() {
        for (int i = composicao.size() - 1; i >= 0; i--) {
            desengataElemento(getElementoComposicao(i));
        }
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());

        JSONArray arrayComposicao = new JSONArray();
        for (ElementoComposicao e : composicao) {
            arrayComposicao.put(e.toJSONObject());
        }

        jsonObject.put("elementosComposicao", arrayComposicao);
        return jsonObject;
    }

    @Override
    public String toString() {
        StringBuilder arr = new StringBuilder();
        for (ElementoComposicao e : composicao) {
            arr.append(e.toString());
            arr.append("\n");
        }

        return "Composicao {" +
                "id=" + id +
                ", composicao=[\n" + arr +
                "]\n}";
    }
}
