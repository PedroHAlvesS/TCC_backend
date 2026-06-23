-- =============================================
-- MIGRAÇÃO V2: Dados iniciais para testes
-- Sistema de Gestão de Entregas - RápidoJá
-- =============================================

-- 1. Inserir usuários (cliente, entregador e administrador)
-- A senha é "admin123" codificada com BCrypt (hash gerado)
INSERT INTO user (name, email, password, phone_number, profile, is_enabled) VALUES
                                                                                ('João Silva', 'joao@email.com', '$2a$10$03ZszFTZ735igzxWrnChMe5inUb0YxnjfhPfkuWSK.dShXASHByQ2', '11999999999', 'CUSTOMER', TRUE),
                                                                                ('Maria Oliveira', 'maria@email.com', '$2a$10$03ZszFTZ735igzxWrnChMe5inUb0YxnjfhPfkuWSK.dShXASHByQ2', '11988888888', 'DELIVERY_MAN', TRUE),
                                                                                ('Admin', 'admin@rapidoja.com', '$2a$10$03ZszFTZ735igzxWrnChMe5inUb0YxnjfhPfkuWSK.dShXASHByQ2', '31999999999', 'ADMIN', TRUE);

-- 2. Inserir endereços
INSERT INTO address (street, number, neighborhood, zip_code, complement) VALUES
                                                                             ('Rua das Flores', '123', 'Centro', '12345-678', 'Apto 101'),
                                                                             ('Avenida Paulista', '1000', 'Bela Vista', '01310-100', 'Conjunto 42');

-- 3. Inserir pedidos (mesmos de antes)
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
                                                                                       ('Pedido de teste – Livro de Java', 'PENDING', 1, NULL, 1),
                                                                                       ('Pedido de teste – Smartphone', 'ASSIGNED', 1, 2, 2),
                                                                                       ('Pedido de teste – Fone de ouvido', 'IN_TRANSIT', 1, 2, 1),
                                                                                       ('Pedido de teste – Monitor', 'DELIVERED', 1, 2, 2),
                                                                                       ('Pedido de teste – Teclado', 'CANCELED', 1, NULL, 1);