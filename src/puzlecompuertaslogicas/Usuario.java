/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzlecompuertaslogicas;
import java.io.Serializable;

/**
 *
 * @author ruben
 */
public class Usuario implements Serializable {
    // Atributos básicos
    private String nombre;
    private String password;
    private int nivelActual;
    private int nivelMaximo;
    private int puntaje;

    // --- ATRIBUTOS DE HISTORIALES (AGREGADOS CORRECTAMENTE AQUÍ) ---
    private boolean[] histEntradaA;
    private boolean[] histEntradaB;
    private boolean[] histEntradaC;
    private boolean[] histEntradaD;
    private boolean[] histRespuestaEsperada;
    private boolean[] nivelYaRegistrado;
    private int[] histCompuertasOcultasVisual;
    
    private String[] histNombreCompuertaActual;
    private String[] histNombreCompuertaAux1;
    private String[] histNombreCompuertaAux2;

    // Constructor
    public Usuario(String nombre, String password) {
        // Se inicializa un usuario 
        this.nombre = nombre;
        this.password = password;
        this.nivelActual = 1;
        this.nivelMaximo = 1;
        this.puntaje = 0;
        
        // Damos vida a los arrays con tamaño 50
        this.histEntradaA = new boolean[50];
        this.histEntradaB = new boolean[50];
        this.histEntradaC = new boolean[50];
        this.histEntradaD = new boolean[50];
        this.histRespuestaEsperada = new boolean[50];
        this.nivelYaRegistrado = new boolean[50];
        this.histCompuertasOcultasVisual = new int[50];
        
        this.histNombreCompuertaActual = new String[50];
        this.histNombreCompuertaAux1 = new String[50];
        this.histNombreCompuertaAux2 = new String[50];
    }
    
    // En caso de avanzar de nivel
    public void avanzarNivel(){
        this.nivelActual++;
        if(this.nivelActual > this.nivelMaximo){
            this.nivelMaximo = this.nivelActual;
        }
    }
    
    // En caso de volver un nivel para repasar
    public void retrocederVoluntario(){
        if(this.nivelActual > 1){
            this.nivelActual--;
        }
    }
    
    // En caso de volver por perder las vidas
    public void retrocederFallo(){
        if(this.nivelActual > 1){
            this.nivelActual--;
            this.nivelMaximo = this.nivelActual;
        }
    }
    
    // Verificacion de cuenta
    public boolean verificarPassword(String passwordUsuario){
        return this.password.equals(passwordUsuario);
    }
    
    // Suma de puntos
    public void sumarPuntos(int puntos){
        if(puntos > 0){
            this.puntaje += puntos;
        }
    }
    
    // Resta de puntos
    public void restarPuntos(int puntos){
        if(puntos > 0){
            this.puntaje -= puntos;
        }
        if(this.puntaje < 0){
            this.puntaje = 0;
        }
    }
    
    // Método puente para compatibilidad con la sincronización del constructor de la vista
    public int getNivelAlcanzado() {
        return this.nivelActual;
    }
    
    public void setNivelAlcanzado(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    // --- GETTERS Y SETTERS BÁSICOS ---
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getNivelActual() { return nivelActual; }
    public void setNivelActual(int nivelActual) { this.nivelActual = nivelActual; }

    public int getNivelMaximo() { return nivelMaximo; }
    public void setNivelMaximo(int nivelMaximo) { this.nivelMaximo = nivelMaximo; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    // --- GETTERS Y SETTERS DE LOS HISTORIALES ---
    public boolean[] getHistEntradaA() { return histEntradaA; }
    public void setHistEntradaA(boolean[] histEntradaA) { this.histEntradaA = histEntradaA; }

    public boolean[] getHistEntradaB() { return histEntradaB; }
    public void setHistEntradaB(boolean[] histEntradaB) { this.histEntradaB = histEntradaB; }

    public boolean[] getHistEntradaC() { return histEntradaC; }
    public void setHistEntradaC(boolean[] histEntradaC) { this.histEntradaC = histEntradaC; }

    public boolean[] getHistEntradaD() { return histEntradaD; }
    public void setHistEntradaD(boolean[] histEntradaD) { this.histEntradaD = histEntradaD; }

    public boolean[] getHistRespuestaEsperada() { return histRespuestaEsperada; }
    public void setHistRespuestaEsperada(boolean[] histRespuestaEsperada) { this.histRespuestaEsperada = histRespuestaEsperada; }

    public boolean[] getNivelYaRegistrado() { return nivelYaRegistrado; }
    public void setNivelYaRegistrado(boolean[] nivelYaRegistrado) { this.nivelYaRegistrado = nivelYaRegistrado; }

    public int[] getHistCompuertasOcultasVisual() { return histCompuertasOcultasVisual; }
    public void setHistCompuertasOcultasVisual(int[] histCompuertasOcultasVisual) { this.histCompuertasOcultasVisual = histCompuertasOcultasVisual; }

    public String[] getHistNombreCompuertaActual() { return histNombreCompuertaActual; }
    public void setHistNombreCompuertaActual(String[] histNombreCompuertaActual) { this.histNombreCompuertaActual = histNombreCompuertaActual; }

    public String[] getHistNombreCompuertaAux1() { return histNombreCompuertaAux1; }
    public void setHistNombreCompuertaAux1(String[] histNombreCompuertaAux1) { this.histNombreCompuertaAux1 = histNombreCompuertaAux1; }

    public String[] getHistNombreCompuertaAux2() { return histNombreCompuertaAux2; }
    public void setHistNombreCompuertaAux2(String[] histNombreCompuertaAux2) { this.histNombreCompuertaAux2 = histNombreCompuertaAux2; }
}