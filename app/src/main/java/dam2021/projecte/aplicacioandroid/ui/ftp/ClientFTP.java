package dam2021.projecte.aplicacioandroid.ui.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ClientFTP {

    public static String SERVER = "projectedamjds.insjoanbrudieu.cat";
    public static int PORT_NUMBER = 15741;
    public static String USER = "projectedamjds";
    public static String PASSWORD = "projecte2021";
    public static String VERSIO = "versio.txt";
    public static String ESDEVENIMENTS = "esdeveniments.xml";
    public static String CATEGORIES = "categories.xml";
    public static String ACTIVITATS = "activitats.xml";
    public static String RESERVES = "reserves.xml";
    public static String ACTIVITAT_CATEGORIA = "activitat_categoria.xml";

}
