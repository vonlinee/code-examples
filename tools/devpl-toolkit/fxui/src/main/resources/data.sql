CREATE
DATABASE IF NOT EXISTS `mbg` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE mbg.generator_config
(
    value varchar(100) NULL
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;
