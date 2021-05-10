CREATE TABLE `activitat` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `titol` varchar(255) NOT NULL,
  `data` varchar(255) NOT NULL,
  `ubicacio` varchar(255) NOT NULL,
  `descripcio` varchar(255) NOT NULL,
  `departament` varchar(255) NOT NULL,
  `ponent` varchar(255),
  `preu` double NOT NULL DEFAULT 0,
  `places_totals` INTEGER NOT NULL DEFAULT 100,
  `places_actuals` INTEGER NOT NULL DEFAULT 100,
  `id_esdeveniment` INTEGER,
  `data_inici_mostra` varchar(255) NOT NULL DEFAULT (now()),
  `data_fi_mostra` varchar(255) NOT NULL,
  FOREIGN KEY (id_esdeveniment) REFERENCES Esdeveniment(id)
);
CREATE TABLE `categoria` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `nom` varchar(255) NOT NULL
);
CREATE TABLE `activitat_categoria` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `id_activitat` INTEGER,
  `id_categoria` INTEGER,
  FOREIGN KEY (id_activitat) REFERENCES Activitat(id),
  FOREIGN KEY (id_categoria) REFERENCES Categoria(id)
);
CREATE TABLE `reserva` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `email` varchar(255) NOT NULL,
  `id_activitat` INTEGER,
  `data` varchar(255) NOT NULL DEFAULT (now()),
  `codi_transaccio` varchar(255) NOT NULL DEFAULT 0 UNIQUE,
  `estat` INTEGER NOT NULL DEFAULT 0,
  FOREIGN KEY (id_activitat) REFERENCES Activitat(id)
);
CREATE TABLE `esdeveniment` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `any` INTEGER NOT NULL DEFAULT 2000,
  `nom` varchar(255) NOT NULL,
  `descripcio` varchar(255),
  `actiu` boolean NOT NULL DEFAULT false
);
