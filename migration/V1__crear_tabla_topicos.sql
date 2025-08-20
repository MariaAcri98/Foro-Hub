CREATE TABLE topicos
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    titulo         VARCHAR(255)          NULL,
    mensaje        VARCHAR(255)          NULL,
    fecha_creacion datetime              NULL,
    status         VARCHAR(255)          NULL,
    autor          VARCHAR(255)          NULL,
    curso          VARCHAR(255)          NULL,
    CONSTRAINT pk_topicos PRIMARY KEY (id)
);