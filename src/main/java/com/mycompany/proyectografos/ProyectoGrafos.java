/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectografos;

import com.mycompany.proyectografos.Grafos.Grafo;

/**
 *
 * @author Jorge Molina
 */
public class ProyectoGrafos {

    public static void main(String[] args) {
        //creamos un grafo llamado edificios 
        Grafo grafoEdificios = new Grafo(8, false, true);
        //registramos los 8 vertices
        grafoEdificios.insertarVertices(8);
        //asignamos nombres a los edificios
        grafoEdificios.asignarNombreVertice(0, "Biblioteca");
        grafoEdificios.asignarNombreVertice(1, "CAD");
        grafoEdificios.asignarNombreVertice(2, "CISCO");
        grafoEdificios.asignarNombreVertice(3, "Registro Escolar");
        grafoEdificios.asignarNombreVertice(4, "Comedor");
        grafoEdificios.asignarNombreVertice(5, "Cafeteria");
        grafoEdificios.asignarNombreVertice(6, "Polideportivo");
        grafoEdificios.asignarNombreVertice(7, "CEDIPE");
        //creamos las conexiones entre edificios 
        grafoEdificios.insertarArista(0, 1); //Biblioteca al CAD
        grafoEdificios.insertarArista(0, 5); //Biblioteca a la Cafetería
        grafoEdificios.insertarArista(1, 2); //CAD al CISCO
        grafoEdificios.insertarArista(1, 6); //CAD al Polideportivo
        grafoEdificios.insertarArista(2, 3); //CISCO al Registro Escolar
        grafoEdificios.insertarArista(3, 4); //Registro Escolar al Comedor
        grafoEdificios.insertarArista(4, 5); //Comedor a la Cafetería
        grafoEdificios.insertarArista(4, 6); //Comedor al Polideportivo
        grafoEdificios.insertarArista(6, 7); //Polideportivo al CEDIPE

        //Requerimiento 1
        //aqui aplicamos bfs al grafo de los edificios
        grafoEdificios.bfs(0, 6);

        //Requerimiento 2
        //aplicamos el dfs a bibloteca 
        grafoEdificios.dfs(0);

        //Requerimiento 3
        //creamos el grafo tareas de mantenimiento
        Grafo grafoTareas = new Grafo(6, true, true);
        //registramos 6 vertices 
        grafoTareas.insertarVertices(6);
        //asignamos los nombres de las tareas 
        grafoTareas.asignarNombreVertice(0, "A");
        grafoTareas.asignarNombreVertice(1, "B");
        grafoTareas.asignarNombreVertice(2, "C");
        grafoTareas.asignarNombreVertice(3, "D");
        grafoTareas.asignarNombreVertice(4, "E");
        grafoTareas.asignarNombreVertice(5, "F");
        //agregamos las dependecias 
        grafoTareas.insertarArista(0, 1); //A->B
        grafoTareas.insertarArista(0, 2); //A->C
        grafoTareas.insertarArista(1, 3); //B->D
        grafoTareas.insertarArista(2, 4); //C->E
        grafoTareas.insertarArista(3, 5); //D->F
        grafoTareas.insertarArista(4, 5); //E->F

        //obtenemos el orden topologico del grafo de las tareas 
        int[] orden = grafoTareas.ordenTopologico();
        //imprimimos los resultados 
        System.out.println("Orden topologico:");
        for (int i = 0; i < orden.length; i++) {
            System.out.print((char) ('A' + orden[i]) + ", ");
        }
        
        
     System.out.println("------------------------------------");
        
     
     // Se asignan las distancias en metros entre edificios para Prim- Kruska
     grafoEdificios.establecerPonderacion(0, 1, 120); // Biblioteca - CAD
     grafoEdificios.establecerPonderacion(0, 5, 80); // Biblioteca - Cafeteroa
     grafoEdificios.establecerPonderacion(1, 2, 100); // CAD - CISCO
     grafoEdificios.establecerPonderacion(1, 6, 200); // CAD - Polideportivo 
     grafoEdificios.establecerPonderacion(2, 3, 90); // CAD - Registro escolar 
     grafoEdificios.establecerPonderacion(3, 4, 110); // Registro escolar - Comedor
     grafoEdificios.establecerPonderacion(4, 5, 70); // Comedor - Cafeteria
     grafoEdificios.establecerPonderacion(4, 6, 150); // Comedor -  Polideportivo 
     grafoEdificios.establecerPonderacion(6, 7, 530); //  Polideportivo  - CEDIPE
    
     // Requerimientos 6 
     // Algoritmo de prim iniciando desde biblioteca = 0;
     grafoEdificios.prim(0);
     
    System.out.println("------------------------------------");     
     
     // Requerimientos 7 
     grafoEdificios.Kruskal();
     
     
     
     System.out.println("------------------------------------");
     
     Grafo grafoPruebaCaminoCorto = new Grafo(8, true, true);
     grafoPruebaCaminoCorto.insertarVertices(8);
     
     //asignamos nombres a los edificios
     grafoPruebaCaminoCorto.asignarNombreVertice(0, "Biblioteca");
     grafoPruebaCaminoCorto.asignarNombreVertice(1, "CAD");
     grafoPruebaCaminoCorto.asignarNombreVertice(2, "CISCO");
     grafoPruebaCaminoCorto.asignarNombreVertice(3, "Registro Escolar");
     grafoPruebaCaminoCorto.asignarNombreVertice(4, "Comedor");
     grafoPruebaCaminoCorto.asignarNombreVertice(5, "Cafeteria");
     grafoPruebaCaminoCorto.asignarNombreVertice(6, "Polideportivo");
     grafoPruebaCaminoCorto.asignarNombreVertice(7, "CEDIPE");
     
     //creamos las conexiones entre edificios 
     grafoPruebaCaminoCorto.insertarArista(0, 1); //Biblioteca al CAD
     grafoPruebaCaminoCorto.insertarArista(1, 2); //Cad a la Cisco
     grafoPruebaCaminoCorto.insertarArista(2, 4); //Cisco al Comedor
     grafoPruebaCaminoCorto.insertarArista(1, 3); //CAD al Registro E
     grafoPruebaCaminoCorto.insertarArista(4, 3); //Comedro al Registro Escolar
     grafoPruebaCaminoCorto.insertarArista(3, 5); //Registro Escolar al Cafeteria
     grafoPruebaCaminoCorto.insertarArista(4, 5); //Comedor a la Cafetería
        
     // establecemos las ponderaciones de las aristas
     grafoPruebaCaminoCorto.establecerPonderacion(0, 1, 4); // A -> B
     grafoPruebaCaminoCorto.establecerPonderacion(1, 2, 3); // B -> C
     grafoPruebaCaminoCorto.establecerPonderacion(2, 4, 5); // C -> E
     grafoPruebaCaminoCorto.establecerPonderacion(1, 3, 2); // B -> D
     grafoPruebaCaminoCorto.establecerPonderacion(4, 3, 2); // E -> D
     grafoPruebaCaminoCorto.establecerPonderacion(3, 5, 4); // D -> F
     grafoPruebaCaminoCorto.establecerPonderacion(4, 5, 4); // E -> F
     
     System.out.println("CAMINOS Cortos: ");
     grafoPruebaCaminoCorto.caminosCortosDAG(0);
     
     
     System.out.println("------------------------------------");
     System.out.println("algoritmo dijkstra ");
     grafoEdificios.dijkstra(0);
     
    }
}
