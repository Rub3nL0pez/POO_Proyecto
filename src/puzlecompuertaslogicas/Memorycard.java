/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzlecompuertaslogicas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author ruben
 */
public class Memorycard {
    private static final String ARCHIVO_GUARDADO = "usuarios_db.dat";
    //Carga la lista completa de usuarios registrados desde el disco
    public static ArrayList<Usuario> cargarTodosLosUsuarios() {
        File archivo = new File(ARCHIVO_GUARDADO);
        if (!archivo.exists()) {
            return new ArrayList<>(); // Retorna lista vacía si es la primera ejecución
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error crítico al cargar la base de datos de usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //lista actualizada de usuarios
    private static void guardarListaUsuarios(ArrayList<Usuario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_GUARDADO))) {
            oos.writeObject(lista);
            System.out.println("Base de datos actualizada ");
        } catch (IOException e) {
            System.err.println("Error al guardar datos de usuarios: " + e.getMessage());
        }
    }
    //Registra un usuario nuevo
    public static void guardarOActualizarUsuario(Usuario usuarioAEditar) {
        ArrayList<Usuario> lista = cargarTodosLosUsuarios();
        int indiceEncontrado = -1;

        // Buscamos mediante el nombre (ignorando mayúsculas) si ya está registrado
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNombre().equalsIgnoreCase(usuarioAEditar.getNombre())) {
                indiceEncontrado = i;
                break;
            }
        }
        if (indiceEncontrado != -1) {
            // Si ya existía, reemplazamos sus datos viejos con el progreso nuevo
            lista.set(indiceEncontrado, usuarioAEditar);
        } else {
            // Si no existía, significa que es un registro nuevo y lo sumamos a la lista
            lista.add(usuarioAEditar);
        }

        // Guardamos la lista actualizada físicamente en el disco
        guardarListaUsuarios(lista);}
        //Obtiene la lista de todos los usuarios registrados, pero ordenada
        public static ArrayList<Usuario> obtenerRankingUsuarios() {
        ArrayList<Usuario> lista = cargarTodosLosUsuarios();
        
        // Ordenación eficiente de mayor a menor puntaje
        lista.sort((u1, u2) -> Integer.compare(u2.getPuntaje(), u1.getPuntaje()));
        
        return lista;
    }
    
}
