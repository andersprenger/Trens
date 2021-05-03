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

public class CadastroElementosComposicao implements Cadastro<ElementoComposicao> {
    private ArrayList<ElementoComposicao> cadastro;

    public CadastroElementosComposicao() {
        this.cadastro = new ArrayList<>();
    }

    @Override
    public void cadastra(ElementoComposicao e) {
        cadastro.add(e);
    }

    @Override
    public int quantidade() {
        return cadastro.size();
    }

    @Override
    public ElementoComposicao getPorPosicao(int index) {
        if (index < 0) {
            return null;
        } else if (index >= quantidade()) {
            return null;
        } else {
            return cadastro.get(index);
        }
    }

    @Override
    public ElementoComposicao getPorId(int id) {
        for (ElementoComposicao e : cadastro) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean removePorId(int id) {
        ElementoComposicao e = getPorId(id);
        //First e != null checks if the search above found the ElementoComposicao with the id passed as parameter.
        //If it found the ElementoComposicao, cadastro.remove(e) is executed.
        return e != null && cadastro.remove(e);
    }

    public void listarLocomotivaLivre()
    {
        for (ElementoComposicao elemento : cadastro)
        {
            if (elemento instanceof Locomotiva && elemento.getIdComposicao() == -1)
            {
                System.out.println(elemento);
            }
        }
    }

    public void listarVagaoLivre()
    {
        for (ElementoComposicao elemento : cadastro) {
            if (elemento instanceof Vagao && elemento.getIdComposicao() == -1) {
                System.out.println(elemento);
            }
        }
    }

    @Override
    public void carrega() {
        String fileName = "elementos.json";
        Path path = Path.of(fileName).toAbsolutePath();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            String jsonString = sc.nextLine();
            JSONObject jsonObject = new JSONObject(jsonString);
            loadFromJSONObject(jsonObject);
        } catch (IOException | JSONException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    private void loadFromJSONObject(JSONObject jsonObject) throws JSONException {
        JSONArray array = jsonObject.getJSONArray("elementosComposicao");
        for (int i = 0; i < array.length(); i++) {
            JSONObject e = (JSONObject) array.get(i);
            if (e.getString("tipo").equals("Locomotiva")) {
                cadastra(new Locomotiva(e));
            } else if (e.getString("tipo").equals("Vagao")) {
                cadastra(new Vagao(e));
            } else {
                cadastra(new VagaoPassageiro(e));
            }
        }
    }

    @Override
    public void persiste() {
        String fileName = "elementos.json";
        Path path = Path.of(fileName).toAbsolutePath();
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            writer.print(this.toJSONObject().toString());
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();
        for (ElementoComposicao e : cadastro) {
            array.put(e.toJSONObject());
        }

        jsonObject.put("elementosComposicao", array);

        return jsonObject;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Cadastro de Elementos de Composicao\n");
        for (ElementoComposicao e : cadastro) {
            str.append(e.toString());
            str.append('\n');
        }
        return str.toString();
    }

}
