CREATE TABLE topicos(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT  NULL UNIQUE,
    mensaje TEXT NOT NULL,
    mensaje_hash CHAR(64) NOT NULL UNIQUE,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    autor VARCHAR (255) NOT NULL,
    curso VARCHAR (255) NOT NULL,
    PRIMARY KEY (id)
);