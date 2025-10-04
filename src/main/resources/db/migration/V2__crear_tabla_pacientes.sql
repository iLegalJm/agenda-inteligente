-- tabla patients
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    primer_nombre VARCHAR(100) NOT NULL,
    segundo_nombre VARCHAR(100),
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(255)
);
