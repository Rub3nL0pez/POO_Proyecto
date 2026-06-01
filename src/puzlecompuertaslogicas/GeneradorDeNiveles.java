/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzlecompuertaslogicas;

import Compuertas.*; // Se importa todo porque se van a usar las 4 compuertas      
import java.util.Random;

/**
 * @author ruben
 */
public class GeneradorDeNiveles {
    // Atributos del nivel
    private int nivelActual;
    private boolean entradaA;
    private boolean entradaB;
    private boolean entradaC;
    private boolean entradaD;
    private boolean nivelCompletado;
    private boolean respuestaEsperada; // Estado del Foco Salida

    // Atributos de control de intentos (vida y penalizacion)
    private int intentosRestantes;
    private int intentosMaximos;
    private int fallosSeguidos;

    // Compuertas
    private Compuertas compuertaActual;
    private Compuertas compuertaAux1;
    private Compuertas compuertaAux2;

    // Random para aleatoriedad
    private Random random;
    //Atributos para guardar niveles superados
    private boolean[] histEntradaA = new boolean[50];
    private boolean[] histEntradaB = new boolean[50];
    private boolean[] histEntradaC = new boolean[50];
    private boolean[] histEntradaD = new boolean[50];
    private boolean[] histRespuestaEsperada = new boolean[50];
    private Compuertas[] histCompuertaActual = new Compuertas[50];
    private Compuertas[] histCompuertaAux1 = new Compuertas[50];
    private Compuertas[] histCompuertaAux2 = new Compuertas[50];
    private boolean[] nivelYaRegistrado = new boolean[50];

    // Constructor
    public GeneradorDeNiveles() {
        this.nivelActual = 1;
        this.entradaA = false;
        this.entradaB = false;
        this.entradaC = false;
        this.entradaD = false;
        this.fallosSeguidos = 0;
        this.random = new Random();
        this.actualizarEstructuraNivel();
    }

    public void actualizarEstructuraNivel() {
        // Los primeros 4 niveles son tutoriales
        if (this.nivelActual <= 4) {
            switch (this.nivelActual) {
                case 1: this.compuertaActual = new AND("AND", "&&"); break;
                case 2: this.compuertaActual = new OR("OR", "||"); break;
                case 3: this.compuertaActual = new NOT("NOT", "!"); break;
                case 4: this.compuertaActual = new XOR("XOR", "!="); break;
            }
            this.intentosMaximos = 9999;  // Tantos intentos como uno quiera
            this.nivelCompletado = true;  // Se habilita siguiente libremente
        } else {
            // Pasamos del tutorial y se asignan los intentos máximos
            if (this.nivelActual == 5 || this.nivelActual == 6) {
                this.intentosMaximos = 2;
            } else {
                this.intentosMaximos = 3;
            }
            generarCircuito();
        }
        this.intentosRestantes = this.intentosMaximos; // Inicializa vidas
    }

    private Compuertas obtenerCompuertaAleatoria() {
        int r = random.nextInt(4); // Genera 0, 1, 2 o 3
        if (r == 0) return new AND("AND", "&&");
        if (r == 1) return new OR("OR", "||");
        if (r == 2) return new XOR("XOR", "!=");
        return new NOT("NOT", "!"); 
    }
    private Compuertas obtenerCompuertaBinariaAleatoria() {
        int r = random.nextInt(3); // Genera solo 0, 1 o 2 (AND, OR, XOR)
        if (r == 0) return new AND("AND", "&&");
        if (r == 1) return new OR("OR", "||");
        return new XOR("XOR", "!=");
    }
    public void generarCircuito() {
        int lvl = getNivelActual();
        
        // --- CONTROL DE MEMORIA HISTÓRICA ---
        if (lvl < 50 && nivelYaRegistrado[lvl]) {
            this.entradaA = histEntradaA[lvl];
            this.entradaB = histEntradaB[lvl];
            this.entradaC = histEntradaC[lvl];
            this.entradaD = histEntradaD[lvl];
            this.compuertaActual = histCompuertaActual[lvl];
            this.compuertaAux1 = histCompuertaAux1[lvl];
            this.compuertaAux2 = histCompuertaAux2[lvl];
            this.respuestaEsperada = histRespuestaEsperada[lvl];
            this.nivelCompletado = true; // Al ser un nivel ya ganado, arranca desbloqueado
            return; // Finaliza el método de forma segura aquí
        }

        boolean tieneSolucionReal = false;

        // El bucle se repetirá hasta que el circuito sorteado tenga al menos una solución viable en el menú
        do {
            // 1. Sorteo de la estructura base del circuito
            this.compuertaActual = obtenerCompuertaAleatoria();

            if (this.nivelActual == 5 || this.nivelActual == 6) {
                this.compuertaAux1 = obtenerCompuertaBinariaAleatoria(); 
                this.compuertaAux2 = null; 
            } else {
                // Del nivel 7 en adelante (Estructura de Árbol)
                this.compuertaAux1 = obtenerCompuertaAleatoria();        
                this.compuertaAux2 = obtenerCompuertaBinariaAleatoria(); 
            }

            // 2. Sorteo aleatorio de bits de entrada
            this.entradaA = random.nextBoolean();
            this.entradaB = random.nextBoolean();
            this.entradaC = random.nextBoolean();
            this.entradaD = random.nextBoolean();

            // 3. Forzamos el cálculo inicial para establecer la respuesta esperada por el foco
            this.respuestaEsperada = calcularResultadoInterno();

            // 4. CONTROL DE CALIDAD INTERNO: Prohibimos escenarios "muertos" o irresolubles.
            int posicionAEvaluar = 3;

            boolean sirveAND = evaluarRespuestaMatematica("AND", posicionAEvaluar);
            boolean sirveOR  = evaluarRespuestaMatematica("OR", posicionAEvaluar);
            boolean sirveXOR = evaluarRespuestaMatematica("XOR", posicionAEvaluar);
            boolean sirveNOT = evaluarRespuestaMatematica("NOT", posicionAEvaluar);

            // Si al menos una compuerta del menú resuelve con éxito el flujo eléctrico, el puzle es legal
            if (sirveAND || sirveOR || sirveXOR || sirveNOT) {
                tieneSolucionReal = true;
            }

        } while (!tieneSolucionReal); // Si el circuito era imposible, el bucle descarta todo y vuelve a sortear

        // REGISTRAMOS EL CIRCUITO NUEVO EN EL HISTORIAL 
        if (lvl < 50) {
            histEntradaA[lvl] = this.entradaA;
            histEntradaB[lvl] = this.entradaB;
            histEntradaC[lvl] = this.entradaC;
            histEntradaD[lvl] = this.entradaD;
            histCompuertaActual[lvl] = this.compuertaActual;
            histCompuertaAux1[lvl] = this.compuertaAux1;
            histCompuertaAux2[lvl] = this.compuertaAux2;
            histRespuestaEsperada[lvl] = this.respuestaEsperada;
            nivelYaRegistrado[lvl] = true; // Queda guardado para siempre
        }

        this.nivelCompletado = false; // Bloqueado de forma segura hasta que el usuario responda
    }

    public boolean calcularResultadoInterno() {
        // 1. Niveles Tutoriales (1 al 4)
        if (this.nivelActual >= 1 && this.nivelActual <= 4) {
            this.respuestaEsperada = this.compuertaActual.operar(this.entradaA, this.entradaB);
            return this.respuestaEsperada;
        } 
        // 2. Estructura en cascada (Niveles 5 y 6) - LÓGICA DIRECTA SIN INVERSIÓN
        else if (this.nivelActual == 5 || this.nivelActual == 6) {
            // COMPUERTA 1 (compuertaActual): Si es NOT, se duplica entradaA para simular operador unario
            boolean resParcial = (this.compuertaActual instanceof NOT) 
                ? this.compuertaActual.operar(this.entradaA, this.entradaA)
                : this.compuertaActual.operar(this.entradaA, this.entradaB);
            
            // Calculamos con la compuertaAux1 (Que ya tenemos garantizado que no es NOT)
            boolean resFinal = this.compuertaAux1.operar(resParcial, this.entradaC);
            
            this.respuestaEsperada = resFinal;
            return this.respuestaEsperada;
        } 
        // 3. Estructura en árbol (Niveles 7 en adelante)
        else {
            // RAMA IZQUIERDA (Compuerta 1): Si es NOT, ignora la Entrada B
            boolean ramaIzq = (this.compuertaActual instanceof NOT) 
                ? this.compuertaActual.operar(this.entradaA, this.entradaA)
                : this.compuertaActual.operar(this.entradaA, this.entradaB);
            
            // RAMA DERECHA (Compuerta 2): Si es NOT, ignora la Entrada D
            boolean ramaDer = (this.compuertaAux1 instanceof NOT)
                ? this.compuertaAux1.operar(this.entradaC, this.entradaC)
                : this.compuertaAux1.operar(this.entradaC, this.entradaD);
            
            // Convergencia final calculada directamente con la compuertaAux2 segura
            this.respuestaEsperada = this.compuertaAux2.operar(ramaIzq, ramaDer);
            return this.respuestaEsperada;
        }
    }

    
    public boolean evaluarRespuestaMatematica(String nombrePropuesto, int posicionOculta) {
        // Creamos la instancia temporal de la compuerta que seleccionó el usuario
        Compuertas compuertaUsuario;
        if ("AND".equalsIgnoreCase(nombrePropuesto)) {
            compuertaUsuario = new AND("AND", "&&");
        } else if ("OR".equalsIgnoreCase(nombrePropuesto)) {
            compuertaUsuario = new OR("OR", "||");
        } else if ("XOR".equalsIgnoreCase(nombrePropuesto)) {
            compuertaUsuario = new XOR("XOR", "!=");
        } else {
            compuertaUsuario = new NOT("NOT", "!");
        }

        // --- SIMULACIÓN ESTRUCTURA CASCADA (Nivel 5 y 6) ---
        if (this.nivelActual == 5 || this.nivelActual == 6) {
            // Definimos qué usar para la primera compuerta (arriba)
            Compuertas c1 = (posicionOculta == 1) ? compuertaUsuario : this.compuertaActual;
            
            boolean resParcial = (c1 instanceof NOT)
                ? c1.operar(this.entradaA, this.entradaA)
                : c1.operar(this.entradaA, this.entradaB);
                
            // Definimos qué usar para la compuerta de la derecha (unión)
            Compuertas cFinal = (posicionOculta == 3) ? compuertaUsuario : this.compuertaAux1;
            
            boolean resFinal = cFinal.operar(resParcial, this.entradaC);
            
            boolean resultadoSimulado = resFinal;
            
            return resultadoSimulado == this.respuestaEsperada;
        } 
        // --- SIMULACIÓN ESTRUCTURA ÁRBOL (Nivel 7+) ---
        else {
            Compuertas c1 = (posicionOculta == 1) ? compuertaUsuario : this.compuertaActual;
            Compuertas c2 = (posicionOculta == 2) ? compuertaUsuario : this.compuertaAux1;
            Compuertas c3 = (posicionOculta == 3) ? compuertaUsuario : this.compuertaAux2;
            
            boolean ramaIzq = (c1 instanceof NOT)
                ? c1.operar(this.entradaA, this.entradaA)
                : c1.operar(this.entradaA, this.entradaB);
                
            boolean ramaDer = (c2 instanceof NOT)
                ? c2.operar(this.entradaC, this.entradaC)
                : c2.operar(this.entradaC, this.entradaD);
                
            boolean resultadoSimulado = c3.operar(ramaIzq, ramaDer);
            
            return resultadoSimulado == this.respuestaEsperada;
        }
    }

    public int verificar(boolean esCorrecto) {
        if (this.nivelActual <= 4) {
            this.nivelCompletado = true;
            return 0; 
        }

        // Si la evaluación matemática determinó que la simulación es correcta, se aprueba
        if (esCorrecto) {
            this.nivelCompletado = true;
            this.fallosSeguidos = 0; // Limpia racha negativa
            return 0; 
        } else {
            this.intentosRestantes--;
            this.nivelCompletado = false;
            
            if (this.intentosRestantes <= 0) {
                this.fallosSeguidos++; // Suma puzle perdido completo
                
                // Penalización por racha negativa
                if (this.fallosSeguidos >= 2 && this.nivelActual > 5) {
                    this.nivelActual--; 
                    this.fallosSeguidos = 0;
                    this.actualizarEstructuraNivel(); 
                    return 3; // Descenso de nivel
                }
                
                // Si no desciende, se le da otro circuito fresco
                generarCircuito();
                this.intentosRestantes = this.intentosMaximos;
                return 2; // Puzle reiniciado
            }
            return 1; // Intento incorrecto, le quedan vidas
        }
    }

    public void cargarNivelEspecifico(int nivel) {
        this.nivelActual = nivel;
        this.fallosSeguidos = 0;
        this.actualizarEstructuraNivel();
    }

    // NAVEGACIÓN MANUAL
    public boolean siguienteNivel() {
        if (this.nivelActual > 4 && !this.nivelCompletado) {
            return false; // Bloqueo estricto si no resolvió el puzle
        }
        this.nivelActual++;
        this.fallosSeguidos = 0; 
        actualizarEstructuraNivel();
        return true;
    }

    public boolean anteriorNivel() {
        if (this.nivelActual > 1) {
            this.nivelActual--;
            this.fallosSeguidos = 0;
            actualizarEstructuraNivel();
            return true;
        }
        return false;
    }

    // --- GETTERS Y SETTERS
    
    public int getNivelActual() {
        return this.nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public void setHistEntradaA(boolean[] histEntradaA) {
        this.histEntradaA = histEntradaA;
    }

    public void setHistEntradaB(boolean[] histEntradaB) {
        this.histEntradaB = histEntradaB;
    }

    public void setHistEntradaC(boolean[] histEntradaC) {
        this.histEntradaC = histEntradaC;
    }

    public void setHistEntradaD(boolean[] histEntradaD) {
        this.histEntradaD = histEntradaD;
    }

    public void setRespuestaEsperada(boolean[] histRespuestaEsperada) {
        this.histRespuestaEsperada = histRespuestaEsperada;
    }

    public void setNivelYaRegistrado(boolean[] nivelYaRegistrado) {
        this.nivelYaRegistrado = nivelYaRegistrado;
    }
    public Compuertas getCompuertaActual() {
        return this.compuertaActual;
    }

    public Compuertas getCompuertaAux1() {
        return this.compuertaAux1;
    }

    public Compuertas getCompuertaAux2() {
        return this.compuertaAux2;
    }
    public int getIntentosRestantes() {
    return this.intentosRestantes;
}
    public boolean isEntradaA() {
        return this.entradaA;
    }

    public boolean isEntradaB() {
        return this.entradaB;
    }

    public boolean isEntradaC() {
        return this.entradaC;
    }

    public boolean isEntradaD() {
        return this.entradaD;
    }
    // --- SETTERS PARA CAMBIAR EL ESTADO DE LAS ENTRADAS ---

    public void setEntradaA(boolean entradaA) {
        this.entradaA = entradaA;
    }

    public void setEntradaB(boolean entradaB) {
        this.entradaB = entradaB;
    }

    public void setEntradaC(boolean entradaC) {
        this.entradaC = entradaC;
    }

    public void setEntradaD(boolean entradaD) {
        this.entradaD = entradaD;
    }
    // GETTER PARA LA RESPUESTA ESPERADA DEL FOCO 
    
    public boolean isRespuestaEsperada() {
        return this.respuestaEsperada;
    }
    public void setHistRespuestaEsperada(boolean[] histRespuestaEsperada) {
        this.histRespuestaEsperada = histRespuestaEsperada;
    }
    
    // INTENTOS MÁXIMOS 
    
    public int getIntentosMaximos() {
        return this.intentosMaximos;
    }

    private Compuertas convertirStringAObjetoCompuerta(String nombreCompuerta) {
        if (nombreCompuerta == null || nombreCompuerta.equals("---") || nombreCompuerta.isEmpty()) {
            return null;
        }
        
        switch (nombreCompuerta.toUpperCase()) {
            case "AND":
                return new AND("AND", "&&"); 
            case "OR":
                return new OR("OR", "||");  
            case "XOR":
                return new XOR("XOR", "!="); 
            case "NOT":
                return new NOT("NOT", "!"); 
            default:
                return null;
        }
    }

    /**
     * Reconstruye el circuito del nivel actual basándose en los nombres 
     * de compuertas almacenados en el archivo del usuario logueado.
     */
    public void restaurarCompuertasDesdeHistorial(String[] hist1, String[] hist2, String[] hist3) {
        int lvl = getNivelActual();
        
        if (lvl < hist1.length) {
            this.compuertaActual = convertirStringAObjetoCompuerta(hist1[lvl]);
            this.compuertaAux1   = convertirStringAObjetoCompuerta(hist2[lvl]);
            this.compuertaAux2   = convertirStringAObjetoCompuerta(hist3[lvl]);
        }
    } 
}
