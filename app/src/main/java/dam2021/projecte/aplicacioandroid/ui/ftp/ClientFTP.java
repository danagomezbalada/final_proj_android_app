package dam2021.projecte.aplicacioandroid.ui.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ClientFTP {

    public static String server = "projectedamjds.insjoanbrudieu.cat";
    public static int portNumber = 15741;
    public static String user = "projectedamjds";
    public static String password = "projecte2021";
    public static String versio = "versio.txt";

}
