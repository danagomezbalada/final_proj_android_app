package dam2021.projecte.aplicacioandroid.ui.cercar;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

import dam2021.projecte.aplicacioandroid.ui.home.Esdeveniment;

@Root(name = "categories")
public class CategoriaXML {

    @ElementList(required = true, inline = true)
    private ArrayList<Categoria> categories;

    public CategoriaXML() {
        this.categories = new ArrayList<>();
    }

    public ArrayList<Categoria> getCategories() {
        return categories;
    }

}
