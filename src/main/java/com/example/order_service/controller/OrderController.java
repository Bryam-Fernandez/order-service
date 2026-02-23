package com.example.order_service.controller;

import com.example.order_service.dto.ResumenPedidosDTO;
import com.example.order_service.enu.EstadoPedido;
import com.example.order_service.model.Pedido;
import com.example.order_service.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class OrderController {

    private final PedidoService pedidoService;

    public OrderController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // ============ CRUD BÁSICO ============

    /**
     * Listar todos los pedidos
     */
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Buscar pedido por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Crear un nuevo pedido
     */
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.crear(pedido);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    /**
     * Actualizar un pedido existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.actualizar(id, pedido);
        return ResponseEntity.ok(pedidoActualizado);
    }

    /**
     * Eliminar un pedido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ============ MÉTODOS ESPECÍFICOS ============

    /**
     * Actualizar solo el estado de un pedido
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPedido estado) {
        Pedido pedido = pedidoService.actualizarEstado(id, estado);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Listar pedidos por cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Listar pedidos por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> listarPorEstado(@PathVariable EstadoPedido estado) {
        List<Pedido> pedidos = pedidoService.listarPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Listar pedidos de un cliente por estado
     */
    @GetMapping("/cliente/{clienteId}/estado/{estado}")
    public ResponseEntity<List<Pedido>> listarPorClienteYEstado(
            @PathVariable Long clienteId,
            @PathVariable EstadoPedido estado) {
        // Este método requeriría implementarlo en el servicio
        List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId).stream()
                .filter(p -> p.getEstado() == estado)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Obtener resumen de pedidos de un cliente
     */
    @GetMapping("/cliente/{clienteId}/resumen")
    public ResponseEntity<ResumenPedidosDTO> obtenerResumen(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId);
        
        long totalPedidos = pedidos.size();
        long pendientes = pedidos.stream().filter(p -> p.getEstado() == EstadoPedido.PENDIENTE).count();
        long preparacion = pedidos.stream().filter(p -> p.getEstado() == EstadoPedido.EN_PREPARACION).count();
        long listo = pedidos.stream().filter(p -> p.getEstado() == EstadoPedido.LISTO).count();
        long cancelados = pedidos.stream().filter(p -> p.getEstado() == EstadoPedido.ENTREGADO).count();
       
        
        ResumenPedidosDTO resumen = new ResumenPedidosDTO(
            totalPedidos,
            pendientes,
            preparacion,
            listo,
            cancelados
        );
        
        return ResponseEntity.ok(resumen);
    }
}

