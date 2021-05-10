package dam2021.projecte.aplicacioandroid.ui.reserves;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "reserves")
public class ReservaXML {

    @ElementList(required = true, inline = true)
    private ArrayList<Reserva> reserves;

    public ReservaXML() {
        this.reserves = new ArrayList<>();
    }

    public ArrayList<Reserva> getReserves() {
        return reserves;
    }

}
