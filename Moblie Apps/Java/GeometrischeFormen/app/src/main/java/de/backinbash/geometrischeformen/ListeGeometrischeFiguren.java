package de.backinbash.geometrischeformen;


import java.util.ArrayList;

import de.backinbash.geometrischeformen.formen.GeometrischeFigur;

/**
 *
 * @author Markus
 */
public class ListeGeometrischeFiguren {

    private static ArrayList<GeometrischeFigur> ObjListe = new ArrayList<GeometrischeFigur>();

    public static void add(GeometrischeFigur obj) {
        ObjListe.add(obj);
    }

    public static void remove(int id){
        ObjListe.remove(id);
    }

    public static ArrayList<GeometrischeFigur> get() {
        return ObjListe;
    }

    public static double berechneFlächeninhalt(){
        double inhalt = 0;

        for(GeometrischeFigur g: ObjListe){
            inhalt += g.berechneFlaecheninhalt();
        }

        return inhalt;
    }
}