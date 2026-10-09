package org.example.fifafx;

public class Jugador {
    private String nombre;
    private String posicion;
    private int dorsal;
    private String equipo;

    public Jugador(String nombre, String posicion, int dorsal, String equipo) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo;
    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public int getDorsal() { return dorsal; }
    public String getEquipo() { return equipo; }
}
