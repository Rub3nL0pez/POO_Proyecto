/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puzlecompuertaslogicas;

/**
 * Clase principal que arranca la aplicación.
 * @author ruben
 */
public class SimuladorDeCompuertasLogicas {

    /**
     * Método main: Lo primero que ejecuta Java al dar "Play".
     */
    public static void main(String[] args) {
        // Hacemos que la pantalla de Login y Registro sea visible en el hilo correcto
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginRegistro pantallaLogin = new LoginRegistro();
                pantallaLogin.setLocationRelativeTo(null); // Centra la ventana en el monitor
                pantallaLogin.setVisible(true); // Hace aparecer la interfaz gráfica
            }
        });
    }
}