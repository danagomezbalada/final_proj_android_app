package dam2021.projecte.aplicacioandroid.ui.reserves;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

import dam2021.projecte.aplicacioandroid.ui.activitats.Activitat;

@Root(name = "reserva")
public class Reserva {

    @Element(name = "id")
    private int id;
    @Element(name = "email")
    private String email;
    @Element(name = "idActivitat")
    private int idActivitat;
    private Activitat activitat;
    @Element(name = "data")
    private Date data;
    @Element(name = "codiTransaccio")
    private String codiTransaccio;
    @Element(name = "estat")
    private int estat;

    public Reserva() {
    }

    public Reserva(int id, String email, int idActivitat, Date data, String codiTransaccio, int estat) {
        this.id = id;
        this.email = email;
        this.idActivitat = idActivitat;
        this.data = data;
        this.codiTransaccio = codiTransaccio;
        this.estat = estat;
    }

    public Reserva(int id, String email, int idActivitat, Activitat activitat, Date data, String codiTransaccio, int estat) {
        this.id = id;
        this.email = email;
        this.idActivitat = idActivitat;
        this.activitat = activitat;
        this.data = data;
        this.codiTransaccio = codiTransaccio;
        this.estat = estat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdActivitat() {
        return idActivitat;
    }

    public void setIdActivitat(int idActivitat) {
        this.idActivitat = idActivitat;
    }

    public Activitat getActivitat() {
        return activitat;
    }

    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCodiTransaccio() {
        return codiTransaccio;
    }

    public void setCodiTransaccio(String codiTransaccio) {
        this.codiTransaccio = codiTransaccio;
    }

    public int getEstat() {
        return estat;
    }

    public void setEstat(int estat) {
        this.estat = estat;
    }

    @Override
    public String toString() {

        switch(estat) {
            case 0:
                return "Pendent";
            case 1:
                return "Confirmada";
            case 2:
                return "Rebutjada";
        }
        return "Error";
    }
}
