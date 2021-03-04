package de.backinbash.geometrischeformen.formen;

import android.graphics.Color;

/**
 *
 * @author lorenz
 */
public class Dreieck extends GeometrischeFigur {

    private double grundlinie;
    private double hoehe;

    public Dreieck(){

    }

    public Dreieck(double grundlinie, double hoehe){
        this.grundlinie = grundlinie;
        this.hoehe = hoehe;
    }

    public double getGrundlinie() {
        return grundlinie;
    }

    public void setGrundlinie(double grundlinie) {
        this.grundlinie = grundlinie;
    }

    public double getHoehe() {
        return hoehe;
    }

    public void setHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    @Override
    public double berechneFlaecheninhalt() {
        double flaecheninhalt = (grundlinie * hoehe) / 2;

        return flaecheninhalt;
    }

    public String toString(){
        return "Dreieck "+getHoehe()+" x "+getGrundlinie();
    }
}