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

    /**
     * @return o id da composição.
     */
    public int getId() {
        return id;
    }

    /**
     * @return a quantidade de locomotivas na composição.
     */
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

    /**
     * @return a quantidade de vagões na composição.
     */
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

    /**
     * Retorna o elemento de composição na composição caso a posição seja valida.<p/>
     *
     * @param posicao a ser buscada o elemento de composição.
     * @return o elemento de composição na posição se encontrado ou null caso contrario.
     */
    private ElementoComposicao getElementoComposicao(int posicao) {
        if (posicao >= 0 && posicao < composicao.size()) {
            return composicao.get(posicao);
        }
        return null;
    }

    /**
     * Procura uma locomotiva na posição do parâmetro a ser retornada caso encontrada.<p/>
     *
     * @param posicao a ser buscada a locomotiva.
     * @return a locomotiva na posição se encontrada ou null caso contrario.
     */
    public Locomotiva getLocomotiva(int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        return e instanceof Locomotiva ? (Locomotiva) e : null;
    }

    /**
     * Procura uma vagão (Vagao ou VagaoPassageiro) na posição do parâmetro a ser retornada caso encontrada.<p/>
     *
     * @param posicao a ser buscado o vagão.
     * @return vagão na posição se encontrado ou null caso contrario.
     */
    public Vagao getVagao(int posicao) {
        ElementoComposicao e = getElementoComposicao(posicao);
        return e instanceof Vagao ? (Vagao) e : null;
    }

    /**
     * Calcula o máximo de vagões da composição somando o máximo de vagões de todas as locomotivas da composição. <p/>
     *
     * @return máximo de vagões da composição.
     */
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

    /**
     * Calcula e retorna o peso máximo suportado pela composição. <p/>
     *
     * O calculo do peso maximo é o somatório dos pesos máximos de cada locomotiva, menos 10% para
     * cada locomotiva a partir da segunda sobre o somatório dos pesos máximos. <p/>
     *
     * @return o peso maximo dos vagões na composição.
     */
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

    /**
     * Calcula o peso atual da composição somando o peso de todos os elementos da composição. <p/>
     *
     * @return peso da composição.
     */
    private double pesoAtual() {
        double pesoAtual = 0;
        for (ElementoComposicao e : composicao) {
            if (e instanceof Vagao) {
                pesoAtual += ((Vagao) e).getCapacidadeCarga();
            }
        }
        return pesoAtual;
    }

    /**
     * Metodo auxiliar que adiciona o elemento passado por parâmetro na composição
     * se ele tiver o id de nenhuma (-1) ou desta composição em seu idComposicao. <p/>
     *
     * @param e elemento de composição (vagão, vagão de passageiros ou locomotiva) a engatado na composição.
     * @return true se o elemento for engatada e false caso contrario.
     */
    private boolean engataElemento(ElementoComposicao e) {
        if (e.getIdComposicao() != -1 && e.getIdComposicao() != this.getId()) {
            return false;
        } else {
            e.setIdComposicao(this);
            composicao.add(e);
            return true;
        }
    }

    /**
     * Engata (adiciona) uma locomotiva na composição se ela não houver nenhum vagão. <p/>
     *
     * @param l locomotiva a ser adicionada na composição.
     * @return true se a locomotiva for engatada e false caso contrario.
     */
    public boolean engataLocomotiva(Locomotiva l) {
        if (getQtdadeVagoes() > 0) {
            return false;
        } else {
            return engataElemento(l);
        }
    }

    /**
     * Engata (adiciona) um vagão na composicao  <p/>
     *
     * @param v locomotiva a ser adicionada na composição.
     * @return true se a locomotiva for engatada e false caso contrario.
     */
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

    /**
     * Método auxiliar que remove um ElementoComposicao do ArrayList cadastro se ele for o ultimo elemento do array. <p/>
     *
     * @param e elemento a ser removido.
     * @return true se o ElementoComposicao e for removido e false caso contrario.
     */
    private boolean desengataElemento(ElementoComposicao e) {
        if (e!= null && composicao.get(composicao.size() - 1).getId() == e.getId()) {
            if (composicao.remove(e)) {
                e.setIdComposicao(null);
                return true;
            }
        }
        return false;
    }

    /**
     * Desengata uma Locomotiva da composição. <p/>
     *
     * @param l locomotiva a ser removida
     * @return true se a locomotiva for removida e false caso contrario.
     */
    public boolean desengataLocomotiva(Locomotiva l) {
        return desengataElemento(l);
    }

    /**
     * Desengata um Vagão (Vagao ou VagaoPassageiro) da composição. <p/>
     *
     * @param v vagão a ser removido
     * @return true se a vagão for removida e false caso contrario.
     */
    public boolean desengataVagao(Vagao v) {
        return desengataElemento(v);
    }

    /**
     * Desengata todos os vagões e locomotivas desta composição.
     */
    public void desfazer() {
        for (int i = composicao.size() - 1; i >= 0; i--) {
            desengataElemento(getElementoComposicao(i));
        }
    }

    /**
     * Retorna um JSONObject contendo o id e um array com os elementos da composição. <p/>
     *
     * @return jsonObject deste objeto.
     */
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
