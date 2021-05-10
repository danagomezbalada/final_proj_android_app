package dam2021.projecte.aplicacioandroid.ui.home;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "esdeveniments")
public class EsdevenimentXML {

    @ElementList(required = true, inline = true)
    private ArrayList<Esdeveniment> esdeveniments;

    public EsdevenimentXML() {
        this.esdeveniments = new ArrayList<>();
    }

    public ArrayList<Esdeveniment> getEsdeveniments() {
        return esdeveniments;
    }

}
