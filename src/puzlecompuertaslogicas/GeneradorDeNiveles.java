/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzlecompuertaslogicas;
import Compuertas.*; //Se importa todo porque se van a usar las 4 compuertas      
import java.util.Random;
/**
 *
 * @author ruben
 */
public class GeneradorDeNiveles {
    // Atributos del Simulador, separamos para tener identificar donde operan
    //Atributos del nivel
  private int nivelActual;
  private boolean entradaA;
  private boolean entradaB;
  private boolean entradaC;
  private boolean entradaD;
  private boolean nivelCompletado;
  private boolean respuestaEsperada;
   //Atributos de control de intentos(vida y penalizacion)
  private int intentosRestantes;
  private int intentosMaximos;
  private int fallosSeguidos;
   //Compuertas
  private Compuertas compuertaActual;
  private Compuertas compuertaAux1;
  private Compuertas compuertaAux2;
   //Random, se va a usar para generar aleatoriamoente
  private Random random;
//Constructor
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
   public void actualizarEstructuraNivel(){
 //Los primeros 4 niveles son tutoriales de las distintas compuertas utilizadas
  if(this.nivelActual <=4 ){
      switch(this.nivelActual){
          case 1: this.compuertaActual = new AND("AND","&&"); break;
          case 2: this.compuertaActual = new OR("OR","||") ; break;
          case 3: this.compuertaActual = new NOT("NOT","!"); break;
          case 4: this.compuertaActual = new XOR("XOR","!="); break;
      }
  //Como es el tutorial tenemos que dejar que naveguen libremente
    this.intentosMaximos = 9999;  // tantos intentos como uno quiera
    this.nivelCompletado = true; // se habilita siguiente
   }else{
    //pasamos del tutorial y se asignan los intentos maximos
            if (this.nivelActual == 5 || this.nivelActual == 6) {
                this.intentosMaximos = 2;
               }else{
                this.intentosMaximos = 3;
            }
            generarCircuito();
         }
    this.intentosRestantes = this.intentosMaximos; //inicializa
    
  }
    //crear compuertas
   private Compuertas obtenerCompuertaAleatoria() {
        int r = random.nextInt(3);
        if (r == 0) return new AND("AND","&&");
        if (r == 1) return new OR("OR","||");
        return new XOR("XOR","!=");
    }
    //crear el circuito del nivel
   private void generarCircuito(){
       this.compuertaActual = obtenerCompuertaAleatoria();
       this.compuertaAux1 = obtenerCompuertaAleatoria();
        this.compuertaAux2 = obtenerCompuertaAleatoria();
        
        // Generación de bits de entrada (0 o 1)
        this.entradaA = random.nextBoolean();
        this.entradaB = random.nextBoolean();
        this.entradaC = random.nextBoolean();
        this.entradaD = random.nextBoolean();
        
        //Buscar la solucion 
        this.respuestaEsperada = calcularResultadoInterno();
        this.nivelCompletado = false; // para no avanzar hasta lograr completar
   }
   private boolean calcularResultadoInterno() {
        if (this.nivelActual == 5 || this.nivelActual == 6) {
            // Estructura en cascada (3 entradas): (A op1 B) op2 C
            boolean resParcial = this.compuertaActual.operar(this.entradaA, this.entradaB);
            boolean resFinal = this.compuertaAux1.operar(resParcial, this.entradaC);
            // El nivel 6 emula un inversor total a la salida (NAND / NOR dinámicas)
            return (this.nivelActual == 6) ? !resFinal : resFinal;
        } else {
            // Estructura en árbol (4 entradas): (A op1 B) op3 (C op2 D)
            boolean ramaIzq = this.compuertaActual.operar(this.entradaA, this.entradaB);
            boolean ramaDer = this.compuertaAux1.operar(this.entradaC, this.entradaD);
            return this.compuertaAux2.operar(ramaIzq, ramaDer);
        }
    }
   public int verificar(boolean respuestaUsuario) {
        if (this.nivelActual <= 4) {
            this.nivelCompletado = true;
            return 0; 
        }

        if (respuestaUsuario == this.respuestaEsperada) {
            this.nivelCompletado = true;
            this.fallosSeguidos = 0; // Se limpia el historial negativo si logra resolverlo
            return 0; 
        } else {
            this.intentosRestantes--;
            this.nivelCompletado = false;
            
            if (this.intentosRestantes <= 0) {
                this.fallosSeguidos++; // Se usan todos los intentos
                
                // Si encadena 2 puzles perdidos consecutivamente, el sistema lo penaliza con un descenso
                if (this.fallosSeguidos >= 2 && this.nivelActual > 5) {
                    this.nivelActual--; 
                    this.fallosSeguidos = 0; // Reset de racha negativa
                    this.actualizarEstructuraNivel(); 
                    return 3; 
                }
                
                // Si no desciende, se le da otra oportunidad sustituyendo el circuito por uno nuevo
                generarCircuito();
                this.intentosRestantes = this.intentosMaximos;
                return 2; 
            }
            return 1; 
        }
    }
   public void cargarNivelEspecifico(int nivel) {
        this.nivelActual = nivel;
        this.fallosSeguidos = 0;
        this.actualizarEstructuraNivel();
    }
   // NAVEGACIÓN MANUAL (Controlada por botones Siguiente/Anterior)
    public boolean siguienteNivel() {
        if (this.nivelActual > 4 && !this.nivelCompletado) {
            return false; // Bloqueo estricto si no se ha resuelto el puzle actual
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
    //Getter y Setter
    public int getNivelActual() { 
        return nivelActual; }
    public boolean isEntradaA() {
        return entradaA; }
    public void setEntradaA(boolean entradaA) {
        this.entradaA = entradaA; }
    public boolean isEntradaB() { 
        return entradaB; }
    public void setEntradaB(boolean entradaB) {
        this.entradaB = entradaB; }
    public boolean isEntradaC() { 
        return entradaC; }
    public void setEntradaC(boolean entradaC) {
        this.entradaC = entradaC; }
    public boolean isEntradaD() { 
        return entradaD; }
    public void setEntradaD(boolean entradaD) {
        this.entradaD = entradaD; }
    public Compuertas getCompuertaActiva() {
        return compuertaActual; }
    public Compuertas getCompuertaAux1() {
        return compuertaAux1; }
    public Compuertas getCompuertaAux2() { 
        return compuertaAux2; }
    public boolean isNivelCompletado() { 
        return nivelCompletado; }
    public int getIntentosRestantes() {
        return intentosRestantes; }
    public int getIntentosMaximos() {
        return intentosMaximos; }
    public boolean isRespuestaEsperada() {
        return respuestaEsperada; }
    public boolean esUltimoNivelTutorial() {
        return this.nivelActual == 4; }
       
   
}

  

   
  

