package com.textadventure.help;

import com.textadventure.exeptions.NoHelpFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Hilfe Klasse, welche hp Dateien lädt und Hilfen ausgeben kann
 */
public class Help {
    private static final HashMap<String, Properties> hilfe = new HashMap<>();

    private static String getFileType(String file, boolean type) {
        for (int i = file.length() - 1; i >= 0; i--) {
            if (file.charAt(i) == '.') {
                if (type) {
                    return file.substring(i + 1);
                } else {
                    return file.substring(0, i);
                }
            }
        }
        if (type) {
            return "";
        } else {
            return file;
        }
    }

    /**
     * Lädt alle hp Dateien in einem Verzeichnis
     */
    public static void load() {
        InputStream input;
        try {
            CodeSource src = Help.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                if (zip.getNextEntry() == null) {
                    File dir = new File("help/");
                    String[] contents = dir.list();
                    if (contents != null) {
                        for (String i : contents) {
                            if (getFileType(i, true).equals("hp")) {
                                try {
                                    input = new FileInputStream("help/" + i);
                                    hilfe.put(getFileType(i, false), new Properties());
                                    hilfe.get(getFileType(i, false)).load(input);
                                } catch (Exception e) {
                                    //Ignore
                                }
                            }
                        }
                    }
                } else {
                    ZipEntry ze;
                    while ((ze = zip.getNextEntry()) != null) {
                        String entryName = ze.getName();
                        if (entryName.endsWith(".hp")) {
                            input = Help.class.getResourceAsStream("/" + entryName);
                            String filename = entryName.substring(entryName.lastIndexOf("/") + 1);
                            hilfe.put(getFileType(filename, false), new Properties());
                            hilfe.get(getFileType(filename, false)).load(input);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Die Funktion gibt einen String mit einer Hilfe zu einem bestimmten Thema zurück
     *
     * @param type      Name der Help Datei in der gesucht werden soll (Ohne Dateiendung)
     * @param parameter Der String nach dem gesucht werden soll, ist der Parameter null wird nach std (Standard Ausgabe) gesucht
     * @return Gibt String mit Hilfe zurück
     * @throws NoHelpFoundException Wird keine Hilfe gefunden wird dies Exception geworfen
     */
    public static String help(String type, String parameter) throws NoHelpFoundException {
        if (parameter == null) {
            parameter = "std";
        }
        try {
            String temp;
            temp = hilfe.get(type).getProperty(parameter);
            if (temp == null) {
                throw new NoHelpFoundException(type, parameter);
            }
            return new String(temp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new NoHelpFoundException(type, parameter);
        }
    }
}
