-- =============================================
-- MIGRAÇÃO V1: Estrutura inicial do banco
-- Sistema de Gestão de Entregas - RápidoJá
-- =============================================

-- Tabela de usuários (clientes, entregadores e administradores)
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      phone_number VARCHAR(20),
                      profile ENUM('CUSTOMER', 'DELIVERY_MAN', 'ADMIN') NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de endereços (pode ser reaproveitada em vários pedidos)
CREATE TABLE address (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         street VARCHAR(150) NOT NULL,
                         number VARCHAR(10),
                         neighborhood VARCHAR(100),
                         zip_code VARCHAR(10) NOT NULL,
                         complement VARCHAR(100),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de pedidos
CREATE TABLE orders (   -- "order" é palavra reservada no MySQL, por isso "orders"
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        description TEXT,
                        creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status ENUM('PENDING', 'ASSIGNED', 'IN_TRANSIT', 'DELIVERED', 'CANCELED') DEFAULT 'PENDING',
                        observation TEXT,

    -- Chaves estrangeiras com papéis distintos
                        customer_id BIGINT NOT NULL,                -- quem criou o pedido
                        delivery_man_id BIGINT NULL,                -- quem entregou (pode ser nulo)
                        address_id BIGINT NOT NULL,                 -- endereço de entrega

    -- Constraints
                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES user(id) ON DELETE RESTRICT,
                        CONSTRAINT fk_order_delivery_man FOREIGN KEY (delivery_man_id) REFERENCES user(id) ON DELETE SET NULL,
                        CONSTRAINT fk_order_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE RESTRICT,

    -- Índices para consultas frequentes
                        INDEX idx_customer (customer_id),
                        INDEX idx_delivery_man (delivery_man_id),
                        INDEX idx_status (status)
);
