INSERT INTO tile_set (name) VALUES
  ('Français'),
  ('English');

-- Français
-- Indivual letters
INSERT INTO letter (value, score) VALUES
  ('A', 1),
  ('B', 3),
  ('C', 3),
  ('D', 2),
  ('E', 1),
  ('F', 4),
  ('G', 2),
  ('H', 4),
  ('I', 1),
  ('J', 8),
  ('K', 10),
  ('L', 1),
  ('M', 2),
  ('N', 1),
  ('O', 1),
  ('P', 3),
  ('Q', 8),
  ('R', 1),
  ('S', 1),
  ('T', 1),
  ('U', 1),
  ('V', 4),
  ('W', 10),
  ('X', 10),
  ('Y', 10),
  ('Z', 10);

-- tile of letter occurences
INSERT INTO tile (letter_id, tile_set_id) VALUES
  (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), (1, 1), -- 11x A
  (2, 1), (2, 1), -- 2x B
  (3, 1), (3, 1), (3, 1), (3, 1), -- 4x C
  (4, 1), (4, 1), -- 2x D
  (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), (5, 1), -- 15x E
  (6, 1), (6, 1), -- 2x F
  (7, 1), (7, 1), -- 2x G
  (8, 1), -- 1x H
  (9, 1), (9, 1), (9, 1), (9, 1), (9, 1), (9, 1), (9, 1), (9, 1), -- 8x I
  (10, 1), -- 1x J
  (11, 1), -- 1x K
  (12, 1), (12, 1), (12, 1), (12, 1), -- 4x L
  (13, 1), (13, 1), (13, 1), -- 3x M
  (14, 1), (14, 1), (14, 1), (14, 1), (14, 1), -- 5x N
  (15, 1), (15, 1), (15, 1), (15, 1), (15, 1), (15, 1), -- 6x O
  (16, 1), (16, 1), (16, 1), -- 3x P
  (17, 1), -- 1x Q
  (18, 1), (18, 1), (18, 1), (18, 1), (18, 1), (18, 1), (18, 1), (18, 1), -- 8x R
  (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), (19, 1), -- 8x S
  (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), (20, 1), -- 6x T
  (21, 1), (21, 1), (21, 1), (21, 1), (21, 1), -- 5x U
  (22, 1), -- 1x V
  (23, 1), -- 1x W
  (24, 1), -- 1x X
  (25, 1), -- 1x Y
  (26, 1); -- 1x Z

-- English
-- Indivual letters
INSERT INTO letter (value, score) VALUES
  ('A', 1),
  ('B', 3),
  ('C', 3),
  ('D', 2),
  ('E', 1),
  ('F', 4),
  ('G', 2),
  ('H', 4),
  ('I', 1),
  ('J', 8),
  ('K', 5),
  ('L', 1),
  ('M', 3),
  ('N', 1),
  ('O', 1),
  ('P', 3),
  ('Q', 10),
  ('R', 1),
  ('S', 1),
  ('T', 1),
  ('U', 1),
  ('V', 4),
  ('W', 4),
  ('X', 8),
  ('Y', 4),
  ('Z', 10);

-- tile of letter occurences
INSERT INTO tile (letter_id, tile_set_id) VALUES
  (27, 2), (27, 2), (27, 2), (27, 2), (27, 2), (27, 2), (27, 2), -- 8x A
  (28, 2), (28, 2), -- 2 B
  (29, 2), (29, 2), (29, 2), -- 3x C
  (30, 2), (30, 2), (30, 2), (30, 2), -- 4x D
  (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), (31, 2), -- 12x E
  (32, 2), (32, 2), -- 2x F
  (33, 2), (33, 2), (33, 2), -- 3x G
  (34, 2), (34, 2), (34, 2), -- 3x H
  (35, 2), (35, 2), (35, 2), (35, 2), (35, 2), (35, 2), (35, 2), -- 7x I
  (36, 2), -- 1x J
  (37, 2), (37, 2), -- 2x K
  (38, 2), (38, 2), (38, 2), (38, 2), (38, 2), -- 5x L
  (39, 2), (39, 2), (39, 2), -- 3x M
  (40, 2), (40, 2), (40, 2), (40, 2), (40, 2), (40, 2), -- 6x N
  (41, 2), (41, 2), (41, 2), (41, 2), (41, 2), (41, 2), -- 6x O
  (42, 2), (42, 2), (42, 2), -- 3x P
  (43, 2), -- 1x Q
  (44, 2), (44, 2), (44, 2), (44, 2), (44, 2), (44, 2), (44, 2), -- 7x R
  (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), (45, 2), -- 9x S
  (46, 2), (46, 2), (46, 2), (46, 2), (46, 2), -- 5x T
  (47, 2), (47, 2), (47, 2), (47, 2), -- 4x U
  (48, 2), -- 1x V
  (49, 2), -- 1x W
  (50, 2), -- 1x X
  (51, 2), (51, 2), -- 2x Y
  (52, 2); -- 1x Z


-- AI player has first id
INSERT INTO player (id, name, level) VALUES
  (1, 'Easy', 1),
  (2, 'Normal', 2),
  (3, 'Hard', 3),
  (4, 'God', 4);

INSERT INTO ai (id) VALUES
  (1),
  (2),
  (3),
  (4);