/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minisuperrapido;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Camila Alfaro Rivera
 */
public class Inventario {
    //lista que guarda todos los productos en el inventario
    private List<Producto> productos; 
    // map que relaciona el codigo de cada producto con su objeto producto
    private Map<Integer, Producto> mapaProductos;

    //Constructor 
    public Inventario() {
    //inicia la lista de productos como un array list vacio
        productos = new ArrayList<>();
    //inicia el map de productos como un HashMap vacio
        mapaProductos = new HashMap<>();
    }

    //meotodo para agregar un producto al inventario
    public void agregarProducto(Producto producto) {
    //Añade el producto a la lista de productos
        productos.add(producto);
    // relaciona el codigo del producto con el objeto producto en el map
        mapaProductos.put(producto.getCodigo(), producto);
    }

    //metodo para buscar un producto en el inventario por su codigo
    public Producto buscarProducto(int codigo) {
    // Devuelve el producto que va con el codigo o null si no existe
        return mapaProductos.get(codigo);
    }

    //metodo para obtener la lista de todos los productos en el inventario
    public List<Producto> getProductos() {
    //da la lista de productos
        return productos;
    }
}
