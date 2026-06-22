-- =============================================
-- MIGRAÇÃO V2: Dados iniciais para testes
-- Sistema de Gestão de Entregas - RápidoJá
-- =============================================

-- 1. Inserir usuários (clientes, entregadores e admin)
-- A senha é "123456" codificada com BCrypt (hash gerado)
INSERT INTO user (name, email, password, phone_number, profile) VALUES
                                                                    ('João Silva', 'joao@email.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '11999999999', 'CUSTOMER'),
                                                                    ('Maria Oliveira', 'maria@email.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '11988888888', 'DELIVERY_MAN'),
                                                                    ('Carlos Admin', 'admin@email.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '11977777777', 'ADMIN');

-- 2. Inserir endereços
INSERT INTO address (street, number, neighborhood, zip_code, complement) VALUES
                                                                             ('Rua das Flores', '123', 'Centro', '12345-678', 'Apto 101'),
                                                                             ('Avenida Paulista', '1000', 'Bela Vista', '01310-100', 'Conjunto 42');

-- 3. Inserir pedidos
-- Pedido 1: criado por João (customer_id=1), ainda sem entregador (delivery_man_id=NULL)
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
    ('Pedido de teste – Livro de Java', 'PENDING', 1, NULL, 1);

-- Pedido 2: criado por João, já atribuído à Maria (delivery_man_id=2)
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
    ('Pedido de teste – Smartphone', 'ASSIGNED', 1, 2, 2);

-- Pedido 3: criado por João, em trânsito
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
    ('Pedido de teste – Fone de ouvido', 'IN_TRANSIT', 1, 2, 1);

-- Pedido 4: entregue
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
    ('Pedido de teste – Monitor', 'DELIVERED', 1, 2, 2);

-- Pedido 5: cancelado
INSERT INTO orders (description, status, customer_id, delivery_man_id, address_id) VALUES
    ('Pedido de teste – Teclado', 'CANCELED', 1, NULL, 1);
