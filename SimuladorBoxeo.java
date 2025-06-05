// Apellido y Nombre: DUBA GASTÓN, Carrera: LIC. EN CIENCIAS DE DATOS, Año: 2025
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


// Clase Boxeador
// Algunos atributos los define el usuario al momento de crear un boxeador,
// el resto se calculan a partir de éstos.
class Boxeador {
    protected String nombre;
    protected double peso;
    protected double altura;
    protected int fuerza;
    protected int velocidad;
    protected int resistencia;
    protected int vida;
    protected int vidaMaxima;
    protected int puntos;
    
    // Constructor
    public Boxeador(String nombre, double peso, double altura, int fuerza, int velocidad, int resistencia) {
        this.nombre = nombre;
        this.peso = peso;
        this.altura = altura;
        this.fuerza = fuerza;
        this.velocidad = velocidad;
        this.resistencia = resistencia;
        this.vidaMaxima = 100 + (resistencia * 2); // vida máxima segun la resistencia
        this.vida = this.vidaMaxima;
        this.puntos = 0;
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getPuntos() { return puntos; }
    public double getPeso() { return peso; }
    
    //Información del boxeador
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Peso: " + peso + " kg" + " | Altura: " + altura + " cm" );
        System.out.println("Fuerza: " + fuerza + " | Velocidad: " + velocidad + " | Resistencia: " + resistencia);
        System.out.println("Vida: " + vida + "/" + vidaMaxima);
    }

    //Funciones del boxeador para las simulaciones de las peleas
    //
    //Calcular el daño de cada ataque
    public int calcularDano() {
        Random rand = new Random();
        int danoBase = (fuerza + resistencia) / 2;
        int variacion = rand.nextInt(10) - 5; // variación del daño de -5 a +4 para darle un componente aleatorio
        return Math.max(1, danoBase + variacion);//me aseguro que sea > 0
    }
    
    //Calcular la defensa ante cada ataque
    public int calcularDefensa() {
        Random rand = new Random();
        int defensaBase = (velocidad + resistencia) / 3;
        int variacion = rand.nextInt(5); // variación de la defensa de 1 a 5 para darle un componente aleatorio
        return defensaBase + variacion;
    }
    
    //Calcular daño recibido
    public void recibirDano(int dano) {
        int defensa = calcularDefensa();
        int danoReal = Math.max(1, dano - defensa); 
        this.vida -= danoReal;
        if (this.vida < 0) this.vida = 0;
    }
    
    //Sumar el puntaje del round
    public void ganarPuntos(int puntos) { this.puntos += puntos; }
    
    //Verificar si está KO
    public boolean estaNoqueado() { return vida <= 0;}

    //Volver  la vida al e nivel inicial luego de terminar una pelea
    public void restaurarVida() {
    this.vida = this.vidaMaxima;
}
}

// Clases que heredan de Boxeador
// Según el peso, se determina la categoría a la que pertenece el boxeador.
// Las categorías heredan de la clase Boxeador y tienen algunos atributos
// característicos de cada una.

class BoxeadorPesoPesado extends Boxeador {
    public BoxeadorPesoPesado(String nombre, double peso, double altura, int fuerza, int velocidad, int resistencia) {
        super(nombre, peso, altura,  fuerza, velocidad, resistencia);

        // Bonificaciones para peso pesado
        this.fuerza += 5; 
        this.velocidad -= 2;
        this.resistencia += 3;
        this.vidaMaxima = 100 + (this.resistencia * 2);
        this.vida = this.vidaMaxima;
    }
    
    @Override //Actualizo el display de la información para esta clase
    public void mostrarInfo() {
        System.out.println("\n=== BOXEADOR PESO PESADO ===");
        System.out.println(" ");
        super.mostrarInfo();
        System.out.println("\nBonus: +5 Fuerza, -2 Velocidad, +3 Resistencia");
    }
}

class BoxeadorPesoPluma extends Boxeador {
    public BoxeadorPesoPluma(String nombre, double peso, double altura,int fuerza, int velocidad, int resistencia) {
        super(nombre, peso, altura,fuerza, velocidad, resistencia);
        
        // Bonificaciones para peso pluma
        this.fuerza -= 2; 
        this.velocidad += 5; 
        this.resistencia += 1;
        this.vidaMaxima = 100 + (this.resistencia * 2);
        this.vida = this.vidaMaxima;
    }
    
    @Override //Actualizo el display de la información para esta clase
    public void mostrarInfo() {
        System.out.println("\n=== BOXEADOR PESO PLUMA ===");
        System.out.println(" ");
        super.mostrarInfo();
        System.out.println("\nBonus: -2 Fuerza, +5 Velocidad, +1 Resistencia");
    }
}

// Función principal
//
// -Muestro la pantalla de bienvenida, la presentación del proyecto y el menú principal del programa.
// -Tengo la creación de boxeadores y el simulador de la pelea round a round.
// -Obtengo el listado de boxeadores creados por los usuarios
//
public class SimuladorBoxeo {
    private static final Scanner scanner = new Scanner(System.in);
    private static final  Boxeador[] historialBoxeadores = new Boxeador[20]; // array de boxeadores
    private static int contadorBoxeadores = 0;
    private static final String SEPARADOR = "==================================================";
    
    public static void main(String[] args) {
        mostrarBienvenida();
        mostrarDescripcionProyecto();
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    simularPelea();
                    break;
                case 2:
                    mostrarRegistro();
                    break;
                case 3:
                    System.out.println("Gracias por usar el Simulador de Boxeo!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
        
        scanner.close();
    }
    
    //Pantalla de bienvenida
    public static void mostrarBienvenida() {
       System.out.println(SEPARADOR);
        System.out.println("           SIMULADOR DE BOXEO           "  );
       System.out.println(SEPARADOR);
        System.out.println("Gaston Duba");
        System.out.println("Licenciatura en Cs. de Datos - UCASAL");
        System.out.println("Programacion I - Examen Final Integrador");
        System.out.println("2025");
       System.out.println(SEPARADOR);
        System.out.println();
    }
    
    //Resumen del proyecto
    public static void mostrarDescripcionProyecto() {
        System.out.println( "RESUMEN DEL PROYECTO:");
        System.out.println("Este simulador permite crear boxeadores con diferentes atributos y simular combates");
        System.out.println("El sistema calcula el ataque, la defensa y determina el ganador por K.O o puntos.");
        System.out.println();
        System.out.println("Presione ENTER para continuar");
        scanner.nextLine();
        System.out.println();
    }
    
    //menú principal
    public static void mostrarMenu() {
        System.out.println("============= MENU PRINCIPAL ================");
        System.out.println("1.  Simular Nueva Pelea");
        System.out.println("2.  Ver Listado de Boxeadores");
        System.out.println("3.  Salir");
        System.out.println(SEPARADOR);
        System.out.print("Seleccione una opcion (1-3): ");
    }
    
    //Simulador
    public static void simularPelea() {
        System.out.println("NUEVA PELEA");
        System.out.println(SEPARADOR);
        
        //Crear el primer boxeador
        System.out.println("                    ");
        System.out.println("--- DATOS DEL PRIMER BOXEADOR ---");
        System.out.println("                    ");
        Boxeador boxeador1 = crearBoxeador();
        
        // Crear el segundo boxeador
        System.out.println("                    ");
        System.out.println("--- DATOS DEL SEGUNDO BOXEADOR ---");
        System.out.println("                    ");
        Boxeador boxeador2 = crearBoxeador();
        
        // Agrego los boxeadores creados a la lista
        if (contadorBoxeadores < historialBoxeadores.length - 1) {//me aseguro que haya lugar en el array
            historialBoxeadores[contadorBoxeadores++] = boxeador1;
            historialBoxeadores[contadorBoxeadores++] = boxeador2;
        }
        
        // información antes de la pelea
        System.out.println("\nCONTENDIENTES");
        System.out.println(SEPARADOR);
        boxeador1.mostrarInfo();
        System.out.println(" ");
        System.out.println("          VS          ");

        boxeador2.mostrarInfo();
        
        System.out.println("\nPresione ENTER para comenzar la pelea");
        scanner.nextLine();
        
        // simulación
        ejecutarPelea(boxeador1, boxeador2);
        boxeador1.restaurarVida();
        boxeador2.restaurarVida();

    }
    
    //crear un boxeador
    static Boxeador crearBoxeador() {
        System.out.print("Ingrese el nombre del boxeador: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese el peso (kg): ");
        double peso = scanner.nextDouble();
        
        System.out.print("Ingrese la altura (cm): ");
        double altura = scanner.nextDouble();
        
        System.out.print("Ingrese fuerza (1-20): ");
        int fuerza = validarRango(1, 20);
        
        System.out.print("Ingrese velocidad (1-20): ");
        int velocidad = validarRango(1, 20);
        
        System.out.print("Ingrese resistencia (1-20): ");
        int resistencia = validarRango(1, 20);
        scanner.nextLine();

        //Calculo la categoría de del boxeador creado
        if (peso > 90) {
        System.out.println("\nBoxeador clase PESO PESADO");
        return new BoxeadorPesoPesado(nombre, peso, altura, fuerza, velocidad, resistencia);
        } else if (peso < 70) {
            System.out.println("\nBoxeador clase PESO PLUMA");
            return new BoxeadorPesoPluma(nombre, peso, altura, fuerza, velocidad, resistencia);
        } else {
            System.out.println("\nBoxeador clase PESO MEDIO");
            return new Boxeador(nombre, peso, altura, fuerza, velocidad, resistencia);
        }
       
    }
    
    //Validación de inputs
   public static int validarRango(int min, int max) {
    int valor = 0;
    boolean entradaValida = false;

        do {
            System.out.print("Ingrese un valor entre " + min + " y " + max + ": ");
            try {
                valor = scanner.nextInt();
                if (valor >= min && valor <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("Valor fuera de rango.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                scanner.nextLine();
            }
        } while (!entradaValida);

        return valor;
    }

    
    //Ejecutar la pelea
    static void ejecutarPelea(Boxeador boxeador1, Boxeador boxeador2) {
        int round = 1;
        final int MAX_ROUNDS = 12; //Duración máxima del combate
        
        System.out.println("INICIO DEL COMBATE");
        System.out.println(SEPARADOR);
        
        //bucle de la pelea (hasta 12 rounds o K.O)
        while (round <= MAX_ROUNDS && !boxeador1.estaNoqueado() && !boxeador2.estaNoqueado()) {
            System.out.println("ROUND " + round + " ");
            System.out.println(SEPARADOR);
            
            //En cada round:
            //
            // simulo ataques del round
            simularRound(boxeador1, boxeador2, round);

            // muestro estado después del round
            mostrarEstadoRound(boxeador1, boxeador2, round);
            
            // verifico K.O
            if (boxeador1.estaNoqueado() || boxeador2.estaNoqueado()) {break;}
            round++; //avanzo al siguiente round
            
            //pausa entre round y round
            System.out.println("Presione ENTER para continuar");
            scanner.nextLine();
        }
        
        //mostrar ganador
        determinarGanador(boxeador1, boxeador2, round > MAX_ROUNDS);
    }
    
    // Método para simular cada round
    static void simularRound(Boxeador boxeador1, Boxeador boxeador2, int round) {

        // cálculos de ataque previos
        int ataque1 = boxeador1.calcularDano();
        int ataque2 = boxeador2.calcularDano();
        
        // determino quién ataca primero según la velocidad de cada uno
        boolean primeroAtaca1 = boxeador1.velocidad >= boxeador2.velocidad;
        
        if (primeroAtaca1) {
            boxeador2.recibirDano(ataque1);
            System.out.println(boxeador1.getNombre() + " golpea por " + ataque1 + " puntos");
            
            if (!boxeador2.estaNoqueado()) {
                boxeador1.recibirDano(ataque2);
                System.out.println( boxeador2.getNombre() + " responde con " + ataque2 + " puntos");
            }
        } else {
            boxeador1.recibirDano(ataque2);
            System.out.println( boxeador2.getNombre() + " golpea por " + ataque2 + " puntos");
            
            if (!boxeador1.estaNoqueado()) {
                boxeador2.recibirDano(ataque1);
                System.out.println( boxeador1.getNombre() + " responde con " + ataque1 + " puntos");
            }
        }
        
        // asigno puntos del round (el que haga más daño gana el round)
        if (!boxeador1.estaNoqueado() && !boxeador2.estaNoqueado()) {
            if (ataque1 > ataque2) {
                boxeador1.ganarPuntos(10);
                boxeador2.ganarPuntos(9);
                System.out.println( boxeador1.getNombre() + " gana el round");
            } else if (ataque2 > ataque1) {
                boxeador2.ganarPuntos(10);
                boxeador1.ganarPuntos(9);
                System.out.println("" + boxeador2.getNombre() + " gana el round");
            } else {
                boxeador1.ganarPuntos(10);
                boxeador2.ganarPuntos(10);
                System.out.println("Round empatado");
            }
        }
    }
    
    //Función mostrar el estado después de cada round.
    //Muestra cuanta vida les queda y cuantos puntos van acumulando
    //Muestra si alguno de los boxeadores quedó fuera de combate
    static void mostrarEstadoRound(Boxeador boxeador1, Boxeador boxeador2, int round) {
    System.out.println("\nESTADO DESPUES DEL ROUND " + round + ":");
    System.out.println("-------------------------------------------------------");
    System.out.printf("| %-20s | Vida: %3d/%3d | Puntos: %3d |%n", 
                     boxeador1.getNombre(), boxeador1.getVida(), boxeador1.getVidaMaxima(), boxeador1.getPuntos());
    System.out.printf("| %-20s | Vida: %3d/%3d | Puntos: %3d |%n", 
                     boxeador2.getNombre(), boxeador2.getVida(), boxeador2.getVidaMaxima(), boxeador2.getPuntos());
    System.out.println("-------------------------------------------------------");
    
    // Verificar y anunciar nocaut
    if (boxeador1.estaNoqueado()) {
        System.out.println("\nK.O! " + boxeador1.getNombre() + " fuera de combate");
    } else if (boxeador2.estaNoqueado()) {
        System.out.println("\nK.O! " + boxeador2.getNombre() + " fuera de combate");
    }
    }

    
    // Función para determinar el ganador
    // Basado en si alguno quedó sin vida o por diferencia de puntos si se complearon los 12 rounds
    static void determinarGanador(Boxeador boxeador1, Boxeador boxeador2, boolean porPuntos) {
        System.out.println(SEPARADOR);
        System.out.println("FIN DEL COMBATE");
        System.out.println(SEPARADOR);
        
        if (boxeador1.estaNoqueado()) {
            System.out.println("GANADOR POR K.O: " + boxeador2.getNombre());
            System.out.println(" " + boxeador1.getNombre() + " ha sido noqueado.");
        } else if (boxeador2.estaNoqueado()) {
            System.out.println("GANADOR POR K.O: " + boxeador1.getNombre());
            System.out.println(" " + boxeador2.getNombre() + " ha sido noqueado.");
        } else if (porPuntos) {
            // Decisión por puntos
            System.out.println("COMBATE DEFINIDO POR PUNTOS");
            System.out.println(SEPARADOR);
            System.out.println(boxeador1.getNombre() + ": " + boxeador1.getPuntos() + " puntos");
            System.out.println(boxeador2.getNombre() + ": " + boxeador2.getPuntos() + " puntos");
            
            if (boxeador1.getPuntos() > boxeador2.getPuntos()) {
                System.out.println("\nGANADOR POR PUNTOS: " + boxeador1.getNombre());
            } else if (boxeador2.getPuntos() > boxeador1.getPuntos()) {
                System.out.println("\nGANADOR POR PUNTOS: " + boxeador2.getNombre());
            } else {
                System.out.println("\nEMPATE");
            }
        }
        
        System.out.println(SEPARADOR);
    }
    
    // Función para mostrar lista de boxeadores creados
    // Recorro el array y voy mostrando en consola cada uno
    public static void mostrarRegistro() {
        System.out.println("REGISTRO DE BOXEADORES");
        System.out.println(SEPARADOR);
        
        if (contadorBoxeadores == 0) {
            System.out.println("No hay boxeadores registrados");
            System.out.println("Simule una pelea para comenzar a crear el registro");
        } else {
            System.out.println("Total de boxeadores registrados: " + contadorBoxeadores);
            System.out.println();
            
            // Recorrer arreglo de boxeadores
            for (int i = 0; i < contadorBoxeadores; i++) {
                System.out.println("--- BOXEADOR #" + (i + 1) + " ---");
                historialBoxeadores[i].mostrarInfo();
                System.out.println();
            }
        }
        
        System.out.println("Presione ENTER para volver al menu principal");
        scanner.nextLine();
    }
}