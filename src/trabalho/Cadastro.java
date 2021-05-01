package trabalho;

import org.json.JSONObject;

public interface Cadastro<T> {
    void cadastra(T t);

    int quantidade();

    T getPorPosicao(int posicao);

    T getPorId(int id);

    boolean removePorId(int id);

    void carrega();

    void persiste();

    JSONObject toJSONObject();

    @Override
    String toString();
}
