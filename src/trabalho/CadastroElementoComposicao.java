package trabalho;

import java.util.ArrayList;

public class CadastroElementoComposicao {
    private ArrayList<ElementoComposicao> elementosDeComposicao;

    public CadastroElementoComposicao () {
        this.elementosDeComposicao = new ArrayList<>();
    }

    public void cadastra (ElementoComposicao e) {
        elementosDeComposicao.add(e);
    }

    public int quantidade () {
        return elementosDeComposicao.size();
    }

    public ElementoComposicao getPorPosicao (int index) {
        if (index < 0) {
            return null;
        } else if (index >= quantidade()) {
            return null;
        } else {
            return elementosDeComposicao.get(index);
        }
    }

    public ElementoComposicao getPorId (int id) {
        for (ElementoComposicao e : elementosDeComposicao) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
