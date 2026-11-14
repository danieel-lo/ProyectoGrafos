/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectografos.Grafos;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
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
        // Validamos que el peso sea mayor que cero
        if (peso <= 0) {
            System.out.println("El peso debe ser mayor que 0");
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
                if(matriz[actual][j]!=0){
                    //disminuimos el grado de entrada del vertice destino
                    gradosEntrada[j]--;
                    //si el grado de entrada llega a 0 lo agregamos a la cola 
                    if(gradosEntrada[j]==0){
                        cola.add(j);
                    }
                    
                }
            }
        }
        //verificamos si se procesaron todos los vertices 
        if(posicion!=numVertices){
            System.out.println("El grafo contiene ciclos");
            return new int[0];
        }
        //devolvemos el orden topologico 
        return orden;
    }

}
