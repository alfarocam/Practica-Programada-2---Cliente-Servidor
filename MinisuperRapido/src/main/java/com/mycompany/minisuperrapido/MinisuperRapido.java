/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.minisuperrapido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author Camila Alfaro Rivera
 */
public class MinisuperRapido extends JFrame {
    //variables para el inventario/partes de la interfaz
    //guarda el inventario de productos
    private final Inventario inventario; 
    //lugar para poner el codigo del producto
    private JTextField txtCodigo; 
    //lugar para poner la cantidad del producto
    private JTextField txtCantidad; 
    //parte de texto en donde se ven los productos en el carrito
    private JTextArea txtAreaCarrito; 
    //Combobox-seleccion de productos
    private JComboBox<String> cmbProductos; 
    //variable para almacenar el total de la compra
    private double totalCompra = 0; 

    //metodo main que da inicio al sistema
    public static void main(String[] args) {
        //instancia del sistema y hace que sea la ventana del sistema
        MinisuperRapido app = new MinisuperRapido(); 
        app.setVisible(true); 
    }

    // Constructor 
    public MinisuperRapido() {
        /*se inicializa el inventario, se llama al metodo para agregar productos, 
        se llama el metodo de crear la interfaz grafica*/
   
        inventario = new Inventario(); 
        inicializarInventario(); 
        crearInterfaz(); 
    }

    //Metodo para iniciar el inventario con productos
    private void inicializarInventario() {
        //5 productos del inventario
        inventario.agregarProducto(new Producto(1, "Leche", 2000, 4)); 
        inventario.agregarProducto(new ProductoDescuento(2, "Arroz", 3000, 2, 0.05)); // con descuento
        inventario.agregarProducto(new Producto(3, "Frijoles", 3500, 3)); 
        inventario.agregarProducto(new ProductoDescuento(4, "Pan", 1000, 5, 0.20)); //con descuento
        inventario.agregarProducto(new Producto(5, "Cangreburger", 20000, 2)); 
    }

    // Metodo para crear la interfaz del sistema
    private void crearInterfaz() {
        //tamaño y nombre de la ventana
        setTitle("**Sistema de Cobro Minisúper Rápido**"); 
        setSize(500, 400); 
        //Cierra el sistema al cerrar la ventana y el layout de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new FlowLayout()); 

        //componentes de la interfaz
        //selección de productos
        JPanel panelSeleccion = new JPanel(new FlowLayout()); 

        //JComboBox para seleccionar productos
        cmbProductos = new JComboBox<>();
        cmbProductos.addItem("Seleccione un producto"); 
        // agrega productos al JComboBox
        for (Producto producto : inventario.getProductos()) {
            cmbProductos.addItem(producto.getCodigo() + " - " + producto.getNombre() + " - ₡" + producto.getPrecio());
        }

        //campo de texto para codigo y cantidad
        txtCodigo = new JTextField(10); // codigo del producto
        txtCantidad = new JTextField(5); //cantidad del producto
        JButton btnAgregar = new JButton("Agregar al Carrito"); // Boton agregar al carrito
        //color del boton
        btnAgregar.setBackground(Color.YELLOW); 
        btnAgregar.setForeground(Color.BLACK); 

        btnAgregar.setOpaque(true);
        btnAgregar.setBorderPainted(false);
        txtAreaCarrito = new JTextArea(15, 40); 
        txtAreaCarrito.setEditable(false); // esto hace que el texto no sea editable

        //metodo o accion del boton para agregar productos al carrito
        btnAgregar.addActionListener((ActionEvent e) -> {
            agregarAlCarrito(); 
        });

        //accion del JComboBox para seleccionar un producto
        cmbProductos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //verifica si realmente se selecciono un producto
                if (e.getStateChange() == ItemEvent.SELECTED && cmbProductos.getSelectedIndex() > 0) {
                    // el producto seleccionado
                    String selectedItem = (String) cmbProductos.getSelectedItem(); 
                     //encuentra el codigo del producto
                    String codigoStr = selectedItem.split(" - ")[0];
                    // pone el codigo en el campo del codigo
                    txtCodigo.setText(codigoStr); 
                }
            }
        });

        //imagen del carrito
        ImageIcon carritoIcon = new ImageIcon(getClass().getResource("/carrito.png")); 
        Image imagen = carritoIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        carritoIcon = new ImageIcon(imagen); 
        //JLabel con la imagen del carrito
        JLabel lblCarrito = new JLabel(carritoIcon);
        add(lblCarrito); 

        //selección de productos
        JPanel panelProductos = new JPanel(new FlowLayout());
        panelProductos.add(new JLabel("Seleccione un producto:")); 
        panelProductos.add(cmbProductos); 
        add(panelProductos); 

        //Panel de codigo y cantidad
        JPanel panelCodCant = new JPanel(new FlowLayout());
        panelCodCant.add(new JLabel("Código del Producto:")); 
        panelCodCant.add(txtCodigo); 
        panelCodCant.add(new JLabel("Cantidad:")); 
        panelCodCant.add(txtCantidad); 
        add(panelCodCant); 
        add(btnAgregar); 
        add(new JScrollPane(txtAreaCarrito));

        //icono de la ventana
        setIconImage(carritoIcon.getImage());
        setLocationRelativeTo(null);
    }

    // Metodo para agregar productos al carrito
    private void agregarAlCarrito() {
        try {
            //obtiene el codigo y la cantidad ingresados
            int codigo = Integer.parseInt(txtCodigo.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            //la cantidad sea mayor a cero
            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
            }

            //bsca el producto en el inventario
            Producto producto = inventario.buscarProducto(codigo);

            //verificar que el producto exista y que haya suficiente cantidad
            if (producto != null && producto.getCantidad() >= cantidad) {
                // Calcular el precio final con el metodo getPrecio() 
                double precioFinal = producto.getPrecio() * cantidad;
                //Suma del precio final al total de la compra
                totalCompra += precioFinal; 

                //info del producto agregado al carrito
                txtAreaCarrito.append(producto.getNombre() + " - Cantidad: " + cantidad + 
                                    " - Precio unitario: ₡" + producto.getPrecio() + 
                                    " - Subtotal: ₡" + precioFinal + "\n");
                //quita el producto agregado del inventario
                producto.reducirCantidad(cantidad); 
                txtAreaCarrito.append("--------------------------------------------\n");
                txtAreaCarrito.append("Total de la compra: ₡" + totalCompra + "\n\n");

                //"limpia" campos luegos de agregar
                txtCodigo.setText(""); 
                txtCantidad.setText(""); 
                cmbProductos.setSelectedIndex(0); 
            } else {
                JOptionPane.showMessageDialog(this, "Producto no disponible o cantidad solicitada mayor al stock",
                                            "Error de Inventario", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código y cantidad válidos",
                                        "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                                        "Error de Cantidad", JOptionPane.ERROR_MESSAGE);
        }
    }
}