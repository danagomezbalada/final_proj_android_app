package dam2021.projecte.aplicacioandroid.ui.cercar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "activitat_categoria")
public class ActivitatCategoria {

    @Element(name = "id_activitat")
    private int idActivitat;
    @Element(name = "id_categoria")
    private int idCategoria;

    public ActivitatCategoria() {
    }

    public ActivitatCategoria(int idActivitat, int idCategoria) {
        this.idActivitat = idActivitat;
        this.idCategoria = idCategoria;
    }

    public int getIdActivitat() {
        return idActivitat;
    }

    public void setIdActivitat(int idActivitat) {
        this.idActivitat = idActivitat;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
