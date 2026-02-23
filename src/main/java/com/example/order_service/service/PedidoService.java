package com.example.order_service.service;

import com.example.order_service.enu.EstadoPedido;
import com.example.order_service.model.Pedido;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final OrderRepository orderRepository;

    public PedidoService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ============ CRUD BÁSICO ============

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public Pedido crear(Pedido pedido) {
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setFechaActualizacion(LocalDateTime.now());
        return orderRepository.save(pedido);
    }

    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(pedidoActualizado.getEstado());
        pedido.setTotal(pedidoActualizado.getTotal());
        pedido.setFechaActualizacion(LocalDateTime.now());
        return orderRepository.save(pedido);
    }

    public void eliminar(Long id) {
        orderRepository.deleteById(id);
    }

    // ============ MÉTODOS ESPECÍFICOS ============

    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(nuevoEstado);
        pedido.setFechaActualizacion(LocalDateTime.now());
        return orderRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return orderRepository.findByClienteIdOrderByFechaCreacionDesc(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorEstado(EstadoPedido estado) {
        return orderRepository.findByEstado(estado);
    }
}