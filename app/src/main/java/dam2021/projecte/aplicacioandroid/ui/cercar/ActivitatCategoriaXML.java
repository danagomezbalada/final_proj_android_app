package dam2021.projecte.aplicacioandroid.ui.cercar;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "activitat_categories")
public class ActivitatCategoriaXML {

    @ElementList(required = true, inline = true)
    private ArrayList<ActivitatCategoria> activitatCategories;

    public ActivitatCategoriaXML() {
        this.activitatCategories = new ArrayList<>();
    }

    public ArrayList<ActivitatCategoria> getCategories() {
        return activitatCategories;
    }

}