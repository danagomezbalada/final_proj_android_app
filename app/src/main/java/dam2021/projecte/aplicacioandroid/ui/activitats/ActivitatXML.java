package dam2021.projecte.aplicacioandroid.ui.activitats;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "activitats")
public class ActivitatXML {

    @ElementList(required = true, inline = true)
    private ArrayList<Activitat> activitats;

    public ActivitatXML() {
        this.activitats = new ArrayList<>();
    }

    public ArrayList<Activitat> getActivitats() {
        return activitats;
    }

}