package de.backinbash.geometrischeformen.formen;

import android.graphics.Color;

/**
 *
 * @author lorenz
 */
public class Kreis extends GeometrischeFigur{
    private double radius;

    public Kreis(){

    }

    public Kreis(double radius){
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double berechneFlaecheninhalt() {
        double flaecheninhalt = Math.PI * radius *radius;
        return flaecheninhalt;
    }

    public String toString(){
        return "Kreis "+getRadius();
    }
}