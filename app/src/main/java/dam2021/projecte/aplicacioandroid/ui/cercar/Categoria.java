package dam2021.projecte.aplicacioandroid.ui.cercar;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "categoria")
public class Categoria {

    @Element(name = "id")
    private int id;
    @Element(name = "nom")
    private String nom;

    public Categoria() {
    }

    public Categoria(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
