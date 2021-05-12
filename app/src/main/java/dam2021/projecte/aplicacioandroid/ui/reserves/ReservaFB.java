package dam2021.projecte.aplicacioandroid.ui.reserves;

/**
 * Classe auxiliar que utilitzem per escriure objectes a Realtime Database de Firebase
 * amb els camps que volem i amb el format que volem
 */
public class ReservaFB {

    private String email;
    private int id_activitat;
    private String data;
    private String codi_transaccio;
    private int estat;

    public ReservaFB() {
    }

    public ReservaFB(String email, int id_activitat, String data, String codi_transaccio, int estat) {
        this.email = email;
        this.id_activitat = id_activitat;
        this.data = data;
        this.codi_transaccio = codi_transaccio;
        this.estat = estat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_activitat() {
        return id_activitat;
    }

    public void setId_activitat(int id_activitat) {
        this.id_activitat = id_activitat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCodi_transaccio() {
        return codi_transaccio;
    }

    public void setCodi_transaccio(String codi_transaccio) {
        this.codi_transaccio = codi_transaccio;
    }

    public int getEstat() {
        return estat;
    }

    public void setEstat(int estat) {
        this.estat = estat;
    }

}
