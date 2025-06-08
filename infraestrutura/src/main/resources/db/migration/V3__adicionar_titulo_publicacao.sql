-- Adiciona coluna titulo na tabela PUBLICACAO
ALTER TABLE PUBLICACAO
ADD COLUMN TITULO VARCHAR(200) NOT NULL DEFAULT 'Sem título';

-- Remove o valor default após adicionar a coluna
ALTER TABLE PUBLICACAO
ALTER COLUMN TITULO DROP DEFAULT; 