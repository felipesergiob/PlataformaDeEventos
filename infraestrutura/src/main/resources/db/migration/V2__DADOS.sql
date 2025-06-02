-- Populando tabela USUARIO
INSERT INTO USUARIO (NOME, EMAIL, SENHA, DATA_CRIACAO, FOTO_PERFIL, SEGUIDORES) VALUES
  ('Ana Souza', 'ana.souza@email.com', 'senha123', '2025-06-01 10:00:00', 'ana.jpg', 2),
  ('Carlos Silva', 'carlos.silva@email.com', 'senha123', '2025-06-01 11:00:00', 'carlos.jpg', 1),
  ('Mariana Oliveira', 'mariana.oliveira@email.com', 'senha123', '2025-06-02 09:30:00', 'mariana.jpg', 1),
  ('João Pereira', 'joao.pereira@email.com', 'senha123', '2025-06-02 14:00:00', 'joao.jpg', 0);

-- Populando tabela EVENTO
INSERT INTO EVENTO (TITULO, DESCRICAO, DATA_INICIO, DATA_FIM, LOCAL, GENERO, VALOR, IMAGEM, PARTICIPANTES, DATA_CRIACAO, ORGANIZADOR_ID) VALUES
  ('Festa Junina do Bairro', 'Festa tradicional com comidas típicas e quadrilha.', '2025-06-10 18:00:00', '2025-06-10 23:59:00', 'Praça Central, Belo Horizonte', 'Festa', 0.00, 'festa_junina.jpg', 3, '2025-06-01 12:00:00', 1),
  ('Show de MPB', 'Show com artistas locais de música popular brasileira.', '2025-06-15 20:00:00', '2025-06-15 23:00:00', 'Teatro Municipal, São Paulo', 'Show', 50.00, 'show_mpb.jpg', 2, '2025-06-02 10:00:00', 2),
  ('Workshop de Fotografia', 'Aprenda técnicas de fotografia urbana.', '2025-06-20 09:00:00', '2025-06-20 17:00:00', 'Centro Cultural, Rio de Janeiro', 'Workshop', 120.00, 'workshop_foto.jpg', 2, '2025-06-03 09:00:00', 3);

-- Populando tabela AVALIACAO
INSERT INTO AVALIACAO (EVENTO_ID, USUARIO_ID, NOTA, COMENTARIO, DATA_CRIACAO) VALUES
  (1, 2, 5, 'Festa incrível, muita animação!', '2025-06-11 10:00:00'),
  (1, 3, 4, 'Comidas deliciosas, mas estava lotado.', '2025-06-11 11:00:00'),
  (2, 1, 5, 'Show maravilhoso, ótimos músicos.', '2025-06-16 09:00:00');

-- Populando tabela COMENTARIO
INSERT INTO COMENTARIO (EVENTO_ID, USUARIO_ID, COMENTARIO, COMENTARIO_PAI_ID, DATA_CRIACAO) VALUES
  (1, 3, 'Adorei a quadrilha!', NULL, '2025-06-10 20:00:00'),
  (1, 2, 'Muito bom mesmo!', 1, '2025-06-10 20:10:00'),
  (2, 1, 'Alguém sabe se pode levar criança?', NULL, '2025-06-15 12:00:00');

-- Populando tabela PUBLICACAO
INSERT INTO PUBLICACAO (EVENTO_ID, USUARIO_ID, CONTEUDO, DATA_CRIACAO, FOTOS) VALUES
  (1, 1, 'Venham todos para a melhor festa junina da cidade!', '2025-06-05 15:00:00', 'festa_junina_convite.jpg'),
  (2, 2, 'Ingressos à venda para o show de MPB!', '2025-06-10 10:00:00', 'show_mpb_banner.jpg');

-- Populando tabela PARTICIPANTE_EVENTO
INSERT INTO PARTICIPANTE_EVENTO (EVENTO_ID, USUARIO_ID, DATA_CRIACAO, STATUS) VALUES
  (1, 1, '2025-06-01 12:10:00', 'CONFIRMADO'),
  (1, 2, '2025-06-01 12:15:00', 'CONFIRMADO'),
  (1, 3, '2025-06-01 12:20:00', 'CONFIRMADO'),
  (2, 1, '2025-06-02 10:10:00', 'CONFIRMADO'),
  (2, 2, '2025-06-02 10:15:00', 'CONFIRMADO'),
  (3, 3, '2025-06-03 09:10:00', 'CONFIRMADO'),
  (3, 4, '2025-06-03 09:20:00', 'PENDENTE');

-- Populando tabela SEGUIDOR
INSERT INTO SEGUIDOR (SEGUIDOR_ID, SEGUIDO_ID, DATA_CRIACAO) VALUES
  (2, 1, '2025-06-01 13:00:00'),
  (3, 1, '2025-06-02 15:00:00'),
  (1, 3, '2025-06-03 16:00:00');
