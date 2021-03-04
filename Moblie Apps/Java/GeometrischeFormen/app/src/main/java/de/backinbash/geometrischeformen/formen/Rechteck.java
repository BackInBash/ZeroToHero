package de.backinbash.geometrischeformen.formen;

import android.graphics.Color;

/**
 *
 * @author lorenz
 */
public class Rechteck extends GeometrischeFigur {

    private double breite;
    private double hoehe;

    public Rechteck(){

    }
    public Rechteck(double breite, double hoehe){
        this.breite = breite;
        this.hoehe = hoehe;
    }

    public double getBreite() {
        return breite;
    }

    public void setBreite(double breite) {
        this.breite = breite;
    }

    public double getHoehe() {
        return hoehe;
    }

    public void setHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    @Override
    public double berechneFlaecheninhalt() {
        double flaecheninhalt = hoehe * breite;
        return flaecheninhalt;
    }

    public String toString(){
        return "Rechteck "+getHoehe()+" x "+getBreite();
    }
}