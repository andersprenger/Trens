package trabalho;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class CadastroComposicoes implements Cadastro<Composicao> {
    private ArrayList<Composicao> cadastro;
    private CadastroElementosComposicao patio;

    public CadastroComposicoes (CadastroElementosComposicao patio) {
        cadastro = new ArrayList<>();
        this.patio = patio;
    }

    public void cadastra (Composicao c) {
        cadastro.add(c);
    }

    public int quantidade () {
        return cadastro.size();
    }

    public Composicao getPorPosicao (int index) {
        if (index < 0) {
            return null;
        } else if (index >= quantidade()) {
            return null;
        } else {
            return cadastro.get(index);
        }
    }

    public Composicao getPorId (int id) {
        for (Composicao c : cadastro) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean removePorId (int id) {
        for (Composicao c : cadastro) {
            if (c.getId() == id) {
                cadastro.remove(c);
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

    public void persiste() {
        String fileName = "composicoes.json";
        Path path = Path.of(fileName).toAbsolutePath();
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            writer.print(this.toJSONObject().toString());
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void carrega () {
        String fileName = "composicoes.json";
        Path path = Path.of(fileName).toAbsolutePath();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            String jsonString = sc.nextLine();
            JSONObject jsonObject = new JSONObject(jsonString);
            loadFromJSONObject(jsonObject);
        } catch (IOException | JSONException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    private void loadFromJSONObject (JSONObject jsonObject) throws JSONException {
        //Loading array of composições from the jsonObject instantiated from the file
        JSONArray arrayComposicoes = jsonObject.getJSONArray("composicoes");
        for (int i = 0; i < arrayComposicoes.length(); i++) {
            Composicao composicao = new Composicao((JSONObject) arrayComposicoes.get(i));
            //Loading array of elementos de composição from the array of composições
            JSONObject jsonComposicao = arrayComposicoes.getJSONObject(i);
            JSONArray arrayElementosDaComposicao = jsonComposicao.getJSONArray("elementosComposicao");
            for (int j = 0; j < arrayElementosDaComposicao.length(); j++) {
                //Loading an elemento de composição from the array of elementos de composição
                JSONObject jsonElemento = arrayElementosDaComposicao.getJSONObject(i);
                //Getting the elemento de composição in cadastro by it's id
                int id = jsonElemento.getInt("id");
                ElementoComposicao elementoComposicao = patio.getPorId(id);
                //Adding elemento de composição into cadastro
                if (elementoComposicao instanceof Locomotiva) {
                    composicao.engataLocomotiva((Locomotiva) elementoComposicao);
                } else {
                    composicao.engataVagao((Vagao) elementoComposicao);
                }
            }
            cadastra(composicao);
        }
    }

    public JSONObject toJSONObject () {
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();
        for (Composicao c : cadastro) {
            array.put(c.toJSONObject());
        }

        jsonObject.put("composicoes", array);

        return jsonObject;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Cadastro Composições:\n");
        for (Composicao c : cadastro) {
            str.append(c.toString());
            str.append("\n");
        }
        return str.toString();
    }
}
