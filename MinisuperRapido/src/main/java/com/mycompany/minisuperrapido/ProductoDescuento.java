/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minisuperrapido;

/**
 *
 * @author Camila Alfaro Rivera
 */
public class ProductoDescuento extends Producto {
    //clase heredada
    // ProductoDescuento aplica un descuento sobre el precio del producto
   private final double descuento;

    public ProductoDescuento(int codigo, String nombre, double precio, int cantidad, double descuento) {
        super(codigo, nombre, precio, cantidad);
        this.descuento = descuento;
    }

    @Override
    public double getPrecio() {
        return super.getPrecio() * (1 - descuento); //aqui se aplica el descuento
    }
}