package trabalho;

import java.util.ArrayList;

public class CadastroComposicoes {
    private ArrayList<Composicao> composicoes;

    public CadastroComposicoes() {
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
}
