package com.example.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order_service.enu.EstadoPedido;
import com.example.order_service.model.Pedido;

public interface OrderRepository extends JpaRepository<Pedido, Long> {
	 List<Pedido> findByEstado(EstadoPedido estado);

	List<Pedido> findByClienteIdOrderByFechaCreacionDesc(Long clienteId);
}	
