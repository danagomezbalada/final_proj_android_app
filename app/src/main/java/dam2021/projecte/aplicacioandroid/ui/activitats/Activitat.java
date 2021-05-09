package dam2021.projecte.aplicacioandroid.ui.activitats;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.sql.Date;

@Root(name = "activitat")
public class Activitat {

    @Element(name = "id")
    private int id;
    @Element(name = "titol")
    private String titol;
    @Element(name = "data")
    private Date data;
    @Element(name = "ubicacio")
    private String ubicacio;
    @Element(name = "descripcio")
    private String descripcio;
    @Element(name = "departament")
    private String departament;
    @Element(name = "ponent")
    private String ponent;
    @Element(name = "preu")
    private Double preu;
    @Element(name = "placesTotals")
    private int placesTotals;
    @Element(name = "placesActuals")
    private int placesActuals;
    @Element(name = "idEsdeveniment")
    private int idEsdeveniment;
    @Element(name = "dataIniciMostra")
    private Date dataIniciMostra;
    @Element(name = "dataFiMostra")
    private Date dataFiMostra;

    public Activitat() {
    }

    public Activitat(int id, String titol, Date data, String ubicacio, String descripcio, String departament, Double preu, int placesTotals, int placesActuals, int idEsdeveniment, Date dataIniciMostra, Date dataFiMostra) {
        this.id = id;
        this.titol = titol;
        this.data = data;
        this.ubicacio = ubicacio;
        this.descripcio = descripcio;
        this.departament = departament;
        this.preu = preu;
        this.placesTotals = placesTotals;
        this.placesActuals = placesActuals;
        this.idEsdeveniment = idEsdeveniment;
        this.dataIniciMostra = dataIniciMostra;
        this.dataFiMostra = dataFiMostra;
    }

    public Activitat(int id, String titol, Date data, String ubicacio, String descripcio, String departament, String ponent, Double preu, int placesTotals, int placesActuals, int idEsdeveniment, Date dataIniciMostra, Date dataFiMostra) {
        this.id = id;
        this.titol = titol;
        this.data = data;
        this.ubicacio = ubicacio;
        this.descripcio = descripcio;
        this.departament = departament;
        this.ponent = ponent;
        this.preu = preu;
        this.placesTotals = placesTotals;
        this.placesActuals = placesActuals;
        this.idEsdeveniment = idEsdeveniment;
        this.dataIniciMostra = dataIniciMostra;
        this.dataFiMostra = dataFiMostra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getPonent() {
        return ponent;
    }

    public void setPonent(String ponent) {
        this.ponent = ponent;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public int getPlacesTotals() {
        return placesTotals;
    }

    public void setPlacesTotals(int placesTotals) {
        this.placesTotals = placesTotals;
    }

    public int getPlacesActuals() {
        return placesActuals;
    }

    public void setPlacesActuals(int placesActuals) {
        this.placesActuals = placesActuals;
    }

    public int getIdEsdeveniment() {
        return idEsdeveniment;
    }

    public void setIdEsdeveniment(int idEsdeveniment) {
        this.idEsdeveniment = idEsdeveniment;
    }

    public Date getDataIniciMostra() {
        return dataIniciMostra;
    }

    public void setDataIniciMostra(Date dataIniciMostra) {
        this.dataIniciMostra = dataIniciMostra;
    }

    public Date getDataFiMostra() {
        return dataFiMostra;
    }

    public void setDataFiMostra(Date dataFiMostra) {
        this.dataFiMostra = dataFiMostra;
    }
}
