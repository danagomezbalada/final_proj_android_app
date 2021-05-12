package dam2021.projecte.aplicacioandroid.ui.home;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "esdeveniment")
public class Esdeveniment {

    @Element(name = "id")
    private int id;
    @Element(name = "any")
    private int any;
    @Element(name = "nom")
    private String nom;
    @Element(name = "descripcio")
    private String descripcio;
    @Element(name = "actiu")
    private String actiu;

    public Esdeveniment() {
    }

    public Esdeveniment(int id, int any, String nom, String descripcio, String actiu) {
        this.id = id;
        this.any = any;
        this.nom = nom;
        this.descripcio = descripcio;
        this.actiu = actiu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String isActiu() {
        return actiu;
    }

    public void setActiu(String actiu) {
        this.actiu = actiu;
    }
}
