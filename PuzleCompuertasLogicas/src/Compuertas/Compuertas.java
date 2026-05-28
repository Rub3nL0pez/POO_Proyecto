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
 
    //setter y getter
    
    //metodo que defina las entradas y salidas, se define en esta clase para que hereden las clases hijas AND, OR, NOT y XOR
}
