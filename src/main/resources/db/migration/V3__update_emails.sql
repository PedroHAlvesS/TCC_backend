-- =============================================
-- MIGRAÇÃO V3: Atualizar emails dos usuários
-- Sistema de Gestão de Entregas - RápidoJá
-- =============================================

UPDATE user SET email = 'joao_cliente@email.com' WHERE id = 1;
UPDATE user SET email = 'maria_entregadora@email.com' WHERE id = 2;
UPDATE user SET email = 'teste_admin@rapidoja.com' WHERE id = 3;
