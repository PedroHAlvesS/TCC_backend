-- =============================================
-- MIGRAÇÃO V3: Adicionar campo is_enabled para soft delete
-- =============================================

ALTER TABLE user ADD COLUMN is_enabled BOOLEAN DEFAULT TRUE NOT NULL;
