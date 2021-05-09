package dam2021.projecte.aplicacioandroid.ui.home;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "esdeveniments")
public class EsdevenimentsXML {

    @ElementList(required = true, inline = true)
    private ArrayList<Esdeveniments> esdeveniments;

    public EsdevenimentsXML() {
        this.esdeveniments = new ArrayList<Esdeveniments>();
    }

    public ArrayList<Esdeveniments> getEsdeveniments() {
        return esdeveniments;
    }

}
