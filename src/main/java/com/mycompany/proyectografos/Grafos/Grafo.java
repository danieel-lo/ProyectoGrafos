/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectografos.Grafos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Usuario
 */
public class Grafo {

    //Es la matriz que guarda las conexiones y sus pesos.
    private int[][] matriz;
    //Cantidad de vértices registrados.
    private int numVertices;
    //Indica si las conexiones tienen dirección.
    private boolean dirigido;
    //Indica si las conexiones tienen peso.
    private boolean ponderado;
    //Lleva el conteo de tiempos de descubrimiento y finalización.
    private int tiempoDFS;
    private String[] nombreVertices;

    public Grafo(int capacidad, boolean dirigido, boolean ponderado) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        matriz = new int[capacidad][capacidad];
        nombreVertices = new String[capacidad];
        numVertices = 0;
        this.dirigido = dirigido;
        this.ponderado = ponderado;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public boolean isDirigido() {
        return dirigido;
    }

    public boolean isPonderado() {
        return ponderado;
    }

    //metodo para agregar vertices 
    public void insertarVertices(int cantidad) {
        // validamos que la cantidad sea positiva y no supoere la cantidad maxima del grafo 
        if (cantidad <= 0 || numVertices + cantidad > matriz.length) {
            System.out.println("Cantidad de vertices no valida");
            return;
        }
        //actualizamos el numero de vertices registrados 
        numVertices += cantidad;
    }

    public void asignarNombreVertice(int indice, String nombre) {
        //validamos que el vertice exista
        if (indice < 0 || indice >= numVertices) {
            System.out.println("El vertice no existe");
            return;
        }
        //validamos que el nombre no este vacío
        if (nombre == null || nombre.isBlank()) {
            System.out.println("El nombre no es valido");
            return;
        }
        //asignamos el nombre al vértice.
        nombreVertices[indice] = nombre;
    }

    //metodo para agregar aristas con un origen y destino 
    public void insertarArista(int origen, int destino) {
        // Validamos que el origen y el destino no sean negativos ni mayores o iguales al número de vértices registrados
        if (origen < 0 || origen >= numVertices || destino < 0 || destino >= numVertices) {
            System.out.println("Uno de los vertices no existe");
            return;
        }
        //creamos la conexion entre los vertices
        matriz[origen][destino] = 1;
        //si el grafo no es dirigido conectamos en ambos sentidos 
        if (!dirigido) {
            matriz[destino][origen] = 1;
        }
    }

    public void establecerPonderacion(int origen, int destino, int peso) {
        //validamos que el grafo permita utilizar los pesos
        if (!ponderado) {
            System.out.println("El grafo no es ponderado");
            return;
        }
        // Validamos que el origen y el destino no sean negativos ni mayores o iguales al número de vértices registrados
        if (origen < 0 || origen >= numVertices || destino < 0 || destino >= numVertices) {
            System.out.println("Uno de los vertices no existe");
            return;
        }
        // El cero representa que no hay arista en la matriz. Se permiten
        // pesos negativos, necesarios para el calculo de camino critico en DAGs.
        if (peso == 0) {
            System.out.println("El peso no puede ser 0");
            return;
        }
        //validamos que exista una conexion entre los vertices que se les quiera agregar un peso 
        if (matriz[origen][destino] == 0) {
            System.out.println("No existe conexion con esos vertices");
            return;
        }
        //asignamos el peso a esa arista 
        matriz[origen][destino] = peso;
        //verificamos si es dirigido, si no lo es asignamos el peso al otro sentido 
        if (!dirigido) {
            matriz[destino][origen] = peso;
        }
    }

    public void imprimirMatriz() {
        //recorremos las filas de la matriz
        for (int i = 0; i < numVertices; i++) {
            //recorremos las columnas de cada fila
            for (int j = 0; j < numVertices; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            //cambiamos de línea al terminar cada fila
            System.out.println();
        }
    }

    public void bfs(int inicio, int destino) {
        //validamos que el origen y el destino existan dentro del grafo
        if (inicio < 0 || inicio >= numVertices || destino < 0 || destino >= numVertices) {
            System.out.println("El origen o el destino no existen");
            return;
        }
        //creamos las distancias y las inicializamos en -1 
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, -1);
        distancias[inicio] = 0;
        //guardamos el vertice desde el cual se descubrió cada uno
        int[] predecesores = new int[numVertices];
        Arrays.fill(predecesores, -1);
        //creamos una cola y agregamos el vertice inicial
        Queue<Integer> cola = new LinkedList<>();
        cola.add(inicio);
        //recorremos los vertices mientras existan elementos en la cola
        System.out.print("Orden de exploracion BFS: ");
        while (!cola.isEmpty()) {
            //sacamos el primer vertice de la cola.
            int actual = cola.poll();
            //mostrarmos el nombre del edificio que estamos visitando 
            System.out.print(nombreVertices[actual] + ", ");
            //si llegamos al destino detenemos la busqueda
            if (actual == destino) {
                break;
            }
            //revisamos los vertices adyacentes al actual
            for (int i = 0; i < numVertices; i++) {
                //si existe conexion y el vertice no ha sido visitado
                if (matriz[actual][i] != 0 && distancias[i] == -1) {
                    //calculamos su distancia en numero de conexiones
                    distancias[i] = distancias[actual] + 1;
                    //registramos desde qué vértice llegamos
                    predecesores[i] = actual;
                    //agregamos el vértice a la cola para explorarlo después
                    cola.add(i);
                }
            }
        }
        if (distancias[destino] == -1) {
            System.out.println("\nNo existe una ruta hacia el destino");
            return;
        }
        //reconstruimos la ruta encontrada
        String ruta = nombreVertices[destino];
        int actual = destino;
        //retrocedemos desde el destino hasta llegar al origen
        while (predecesores[actual] != -1) {
            actual = predecesores[actual];
            ruta = nombreVertices[actual] + " -> " + ruta;
        }
        //mostramos la ruta encontrada
        System.out.println("\nRuta encontrada: " + ruta);
        System.out.println("Total de conexiones: " + distancias[destino]);
    }

    public void dfs(int inicio) {
        //validamos que el vertice inicial exista dentro de lgrafo 
        if (inicio < 0 || inicio >= numVertices) {
            System.out.println("El vertice inicial no existe");
            return;
        }
        //creamos el arreglo de colores
        int[] colores = new int[numVertices];
        //gardamos cada predecesor en caa vertce
        int[] predecesores = new int[numVertices];
        Arrays.fill(predecesores, -1);
        //registramos los tiempos de descubrimiento y finalizacion 
        int[] descubrimiento = new int[numVertices];
        int[] finalizacion = new int[numVertices];
        //reiniciamos el contador de dfs
        tiempoDFS = 0;
        dfsVisitar(inicio, colores, predecesores, descubrimiento, finalizacion);

        System.out.println("\nEdificio | Predecesor | Descubrimiento | Finalizacion");
        for (int i = 0; i < numVertices; i++) {
            //mostramos unicamente los edificios recorridos 
            if (colores[i] != 0) {
                //si no tiene predecesir mostramos ninguno
                String predecesor = "Ninguno";
                if (predecesores[i] != -1) {
                    predecesor = nombreVertices[predecesores[i]];
                }
                //mostramos los resultados del recorrido
                System.out.println(nombreVertices[i] + " | " + predecesor + " | " + descubrimiento[i] + " | " + finalizacion[i]);
            }
        }
    }

    private void dfsVisitar(int actual, int[] colores, int[] predecesores, int[] descubrimiento, int[] finalizacion) {
        //marcamos el vertice acutal en gris
        colores[actual] = 1;
        //incrementamos el tiempó y registramos el descubrimiento
        tiempoDFS++;
        descubrimiento[actual] = tiempoDFS;
        //mostramos el nombre del edificio que estamos visitando
        System.out.print(nombreVertices[actual] + ", ");
        //recorremos los vertices adyacentes al actual
        for (int i = 0; i < numVertices; i++) {
            //validamos que exista conexion y que el vertice no haya sido visitado
            if (matriz[actual][i] != 0 && colores[i] == 0) {
                //guardamos desde que vertice llegamos
                predecesores[i] = actual;
                //visitamos el siguiente vertice en profundidad
                dfsVisitar(i, colores, predecesores, descubrimiento, finalizacion);
            }
        }
        //marcamos el vertice como explorado en este caso en negro 
        colores[actual] = 2;
        //incrementamos el tiempo y registramos su finalizacion
        tiempoDFS++;
        finalizacion[actual] = tiempoDFS;
    }

    public int[] ordenTopologico() {
        //validamos que el grafo sea dirigido 
        if (!dirigido) {
            System.out.println("El grafo debe ser dirigido");
            return new int[0];
        }
        //creamos un arreglo para guardar los grados de entrada de cada vertice 
        int[] gradosEntrada = new int[numVertices];
        //recorremos las filas 
        for (int i = 0; i < numVertices; i++) {
            //recorremos las columnas 
            for (int j = 0; j < numVertices; j++) {
                //si existe una arista i a j aumentamos el grado de entrada de j 
                if (matriz[i][j] != 0) {
                    gradosEntrada[j]++;
                }
            }
        }
        //creamos la cola para guardar los vertices con grado de entrada de 0
        Queue<Integer> cola = new ArrayDeque<>();
        //reccoremos los grados de entrada de todos los vertices 
        for (int i = 0; i < numVertices; i++) {
            //si el grado de entrada es de 0 lo agregamos a la cola
            if (gradosEntrada[i] == 0) {
                cola.add(i);
            }
        }
        //creamos un arreglo para guardar el orden de los vertices
        int[] orden = new int[numVertices];
        //indica cuantos vertices hemos guardado 
        int posicion = 0;
        //procesasamos los vertices mientras la cola tenga elementos 
        while (!cola.isEmpty()) {
            //sacamos el primer vertice de la cola 
            int actual = cola.poll();
            //guardamos el vertice en el arreglo y avanzamos la posicion 
            orden[posicion] = actual;
            //aumentamos el contador de posiciones 
            posicion++;
            //recorremos la conexiones del vertice actual
            for (int j = 0; j < numVertices; j++) {
                //verificamos si existe una arista del vertice actual hacia j
                if (matriz[actual][j] != 0) {
                    //disminuimos el grado de entrada del vertice destino
                    gradosEntrada[j]--;
                    //si el grado de entrada llega a 0 lo agregamos a la cola 
                    if (gradosEntrada[j] == 0) {
                        cola.add(j);
                    }

                }
            }
        }
        //verificamos si se procesaron todos los vertices 
        if (posicion != numVertices) {
            System.out.println("El grafo contiene ciclos");
            return new int[0];
        }
        //devolvemos el orden topologico 
        return orden;
    }

    /**
     * Calcula los caminos de menor peso desde un vertice inicial en un DAG.
     * Recorre los vertices en orden topologico y relaja cada arista saliente.
     */
    public void caminosCortosDAG(int inicio) {
        //primero verifica que sea dirigido y ponderado
        if (!dirigido || !ponderado) {
            System.out.println("Caminos cortos en DAG requiere un grafo dirigido y ponderado");
            return;
        }
        //verifica que el origen si exista
        if (inicio < 0 || inicio >= numVertices) {
            System.out.println("El vertice inicial no existe");
            return;
        }
        //extrae el orden lineal con el metodo de ordentopologico
        int[] orden = ordenTopologico();
        if (orden.length != numVertices) {
            // ordenTopologico ya informa si el grafo tiene ciclos.
            return;
        }

        //variable con valor infinito
        final long infinito = Long.MAX_VALUE;
        long[] distancia = new long[numVertices]; //arreglo con espacio para cada vertice
        int[] predecesor = new int[numVertices]; //arreglo para guardar predecesores
        Arrays.fill(distancia, infinito); //seteamos todos loas distancias en infinito
        Arrays.fill(predecesor, -1); //seteamos todos los predecesores como null
        distancia[inicio] = 0; //seteamos el origen en 0

        // En un DAG con relajar una vez cada vertice en orden topologico.
        for (int u : orden) {
            if (distancia[u] == infinito) {
                continue;
            }
            for (int v = 0; v < numVertices; v++) {
                if (matriz[u][v] != 0) {
                    long alternativa = distancia[u] + matriz[u][v];
                    if (alternativa < distancia[v]) {
                        distancia[v] = alternativa;
                        predecesor[v] = u;
                    }
                }
            }
        }

        System.out.println("Caminos cortos desde " + nombreVertices[inicio] + ":");
        for (int destino = 0; destino < numVertices; destino++) {
            if (distancia[destino] == infinito) {
                System.out.println(nombreVertices[destino] + ": no es alcanzable");
                continue;
            }

            List<String> ruta = new ArrayList<>();
            for (int actual = destino; actual != -1; actual = predecesor[actual]) {
                ruta.add(nombreVertices[actual]);
            }
            Collections.reverse(ruta);
            System.out.println(nombreVertices[destino] + "  costo: " + distancia[destino] + " | ruta: " + String.join(" - ", ruta));
        }
    }

    // clase auxiliar para representar una arista con peso (Kruskal)
    private static class Arista implements Comparable<Arista> {

        int origen;
        int destino;
        int peso;

        public Arista(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(Arista otra) {
            return Integer.compare(this.peso, otra.peso);
        }
    }

    public void Kruskal() {
        if (dirigido || !ponderado) {
            System.out.println("Kruskal requiere un grafo no dirigido y ponderado");
            return;
        }

        // Lista que guarda las aristas del MST
        List<Arista> resultadoA = new ArrayList<>();

        // Cada arista es un conjunto unico al comienzo 
        int[] conjunto = new int[numVertices];
        for (int v = 0; v < numVertices; v++) {
            conjunto[v] = v;
        }

        // Gurda todas las aristas en una lista para ser ordenadas
        List<Arista> aristas = new ArrayList<>();
        for (int u = 0; u < numVertices; u++) {
            for (int v = u + 1; v < numVertices; v++) {
                if (matriz[u][v] != 0) {
                    aristas.add(new Arista(u, v, matriz[u][v]));
                }
            }
        }
        
        // ordena las aristas de menor a mayor
        Collections.sort(aristas);
        
        for(Arista arista : aristas){
            int u = arista.origen;
            int v = arista.destino;
            
            //Se verifica que no sea un ciclo
            if (encontrarConjunto(conjunto,u) != encontrarConjunto(conjunto, v)){
                // Se agrega la arista en conjunto
                resultadoA.add(arista);
                // hace la union de ambos conjuntos
                unirConjuntos(conjunto, u, v);
            }
        }
        
        System.out.println("Arbol de expansion minima (MST) por kruskal");
        int costoTotal = 0;
        for (Arista arista : resultadoA){
            System.out.println(nombreVertices[arista.origen] + " <-> "+ nombreVertices[arista.destino] +"    Distancia:  " + arista.peso + " m");
            costoTotal += arista.peso;
        }
        System.out.println("Total de cableado necesario: " + costoTotal + "m");
    }
    
    // Metodos auxiliares Kruskal
    // Metodo para encontrar el "lider" del conjunto
    private int encontrarConjunto(int[] conjunto, int v){
        if(conjunto[v]==v){
            return v;
        }
        return conjunto[v]= encontrarConjunto(conjunto, conjunto[v]);
    }

    // une dos conjuntos despues de ser confirmado en Kruskal
    private void unirConjuntos(int[] conjunto, int u, int v){
        int raizU = encontrarConjunto(conjunto, u);
        int raizV = encontrarConjunto(conjunto, v);
        conjunto[raizU] = raizV;
    }
    
    // Algoritmo de PRIM (MST)
    public void prim(int inicio){
        if(dirigido || !ponderado){
            System.out.println("Prim necesita un grafo no dirigido y ponderado");
            return;
        }
        if(inicio<0 || inicio >= numVertices){
            System.out.println("El vertice no existe");
            return;
        }
        
        // Guarda el peso minimo de cada vertice
        int[] llave = new int[numVertices];
        // Guarda el padre de cada vertice 
        int[] padre = new int[numVertices];
        // indica que vertices siguen por agregar al arbol
        boolean[] enCola = new boolean[numVertices];
        
        // Se inicializa todos en "Infinito", padres "nil" y todos en cola
        for(int u=0; u<numVertices; u++){
            llave[u]=Integer.MAX_VALUE;
            padre[u]=-1;
            enCola[u] = true;
        }
        
        // La llave del vertice raiz se pone en 0
        llave[inicio]= 0;
        
        for(int i =0; i<numVertices;i++){
            int u = extraerMinimo(llave, enCola);
            
           if(u==-1){
               break;
           } 
           
           // se saca de la cola
           enCola[u] = false;
           
           // Se rrecoren los vecinos de v a u
           for(int v = 0; v< numVertices; v++){
               // si existe conexion, v sigue en la cola y el peso de (u,v) es menor a la llave
               if(matriz[u][v] != 0 && enCola[v] && matriz[u][v] < llave[v]){
                   padre[v] = u;
                   llave[v] =matriz[u][v];
               }
           }
        }
        
        // Se muestra las conexiones y el total del cableado 
        System.out.println("Arbol de expansion minima (MST) por Prim ");
        int costoTotal = 0;
        for (int i = 0; i < numVertices; i++){
            if(padre[i] != -1){
            System.out.println(nombreVertices[padre[i]] + " <--> " + nombreVertices[i] + "    Distancia:  " + llave[i] + "m");
            costoTotal += llave[i];
            }
        }
        System.out.println("Total de cableado necesario: " + costoTotal + "m");
    }
    
    // Metodo auxiliar para extraer el minimo en Prim
    private int extraerMinimo(int[] llave, boolean[] enCola){
      int minimo = Integer.MAX_VALUE;
      int indiceMinimo = -1;
      
      for(int v=0; v< numVertices; v++){
          if (enCola[v] && llave[v]<minimo){
              minimo = llave[v];
              indiceMinimo = v;
          }
      }
      return indiceMinimo;
    }
}
