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
public class Usuario implements Serializable  {
    //atributos
    private String nombre;
    private String password;
    private int nivelActual;
    private int nivelMaximo;
    private int puntaje;

    public Usuario(String nombre, String password) {
        //se inicializa un usuario 
        this.nombre = nombre;
        this.password = password;
        this.nivelActual = 1;
        this.nivelMaximo = 1;
        this.puntaje = 0;
    }
    //en caso de avanzar de nivel
    public void avanzarNivel(){
        this.nivelActual++;
        if(this.nivelActual > this.nivelMaximo){
            this.nivelMaximo= this.nivelActual;
        }
    }
    //en caso de volver un nivel para repasar
    public void retrocederVoluntario(){
        if(this.nivelActual > 1){
            this.nivelActual--;
        }
    }
    //en caso de volver por perder las vidas
    public void retrocederFallo(){
        if(this.nivelActual > 1){
            this.nivelActual--;
            this.nivelMaximo = this.nivelActual;
        }
    }
    //verificacion de cuenta
    public boolean verificarPassword(String passwordUsuario){
        return this.password.equals(passwordUsuario);
    }
    //suma de puntos
    public void sumarPuntos(int puntos){
        if(puntos > 0){
            this.puntaje += puntos;
        }
    }
    //resta de puntos
    public void restarPuntos(int puntos){
        if(puntos > 0){
            this.puntaje -= puntos;
        }
        if(this.puntaje < 0){
            this.puntaje = 0;
        }
    }
    //getter y setter
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
}
