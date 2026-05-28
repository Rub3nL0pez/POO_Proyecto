/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Compuertas;

/**
 *
 * @author ruben
 */
public abstract class Compuertas {
    //Nombre puede ser AND, OR, NOT, XOR, pero como normalmente asociamos esos nombres a simbolos se crearan simbolos tambien(&&,||, etc)
    private String nombre;
    private String simbolo;
    //Constructor
    public Compuertas(String nombre, String Simbolo){
        this.nombre = nombre;
        this.simbolo = simbolo;
    }
    //setter y getter
      public void setNombre(String nombre) {
        this.nombre = nombre;
    }
      
      public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
      public String getNombre() {
        return nombre;
    }

      public String getSimbolo() {
        return simbolo;
    }
    //metodo que defina las comportamiento, entradas y salidas, se define en esta clase para que hereden las clases hijas AND, OR, NOT y XOR
      public abstract boolean operar(boolean a, boolean b);
   

  
}
