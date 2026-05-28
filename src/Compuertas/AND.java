/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Compuertas;

/**
 *
 * @author ruben
 */
public class AND extends Compuertas {

    public AND(String nombre, String Simbolo) {
        super("AND", "&&");
    }
//Implementacion del metodo de la clase padre(operar)  
    @Override
    public boolean operar(boolean a, boolean b) {
        return a && b;
    }
   
}
