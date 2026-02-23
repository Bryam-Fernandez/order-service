package com.example.order_service.dto;

public class ResumenPedidosDTO {
    private long total;
    private long pendientes;
    private long completados;
    private long cancelados;
    
    public ResumenPedidosDTO(long total, long pendientes, long completados, long cancelados, long cancelados2) {
        this.total = total;
        this.pendientes = pendientes;
        this.completados = completados;
        this.cancelados = cancelados;
    }
    
    // Getters
    public long getTotal() { return total; }
    public long getPendientes() { return pendientes; }
    public long getCompletados() { return completados; }
    public long getCancelados() { return cancelados; }
}