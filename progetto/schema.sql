SET SEARCH_PATH = bike_sharing;

CREATE TABLE bicicletta (
    id VARCHAR(36) NOT NULL,
    tipo VARCHAR(30),
    danneggiata BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE totem (
    id VARCHAR(36) NOT NULL,
    indirizzo VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE morsa (
    id VARCHAR(36) NOT NULL,
    posizione INTEGER,
    tipo VARCHAR(30),
    bicicletta VARCHAR(36),
    totem VARCHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY(bicicletta) REFERENCES bicicletta(id),
    FOREIGN KEY(totem) REFERENCES totem(id)
);

CREATE TABLE cartadicredito(
    numero VARCHAR(16) NOT NULL,-- La lunghezza del numero della carta di credito varia da 13 a 16 caratteri
    scadenza DATE,
    cvv INTEGER,
    PRIMARY KEY(numero)
);

CREATE TABLE abbonamento (
    codice VARCHAR(36) NOT NULL,
    password VARCHAR(20), -- La password non può superare i 20 caratteri
    tipo VARCHAR(30),
    studente BOOLEAN,
    sospeso BOOLEAN,
    dataacquisto DATE,
    datainizio DATE,
    datascadenzavalidità DATE,
    ammonizioni INTEGER,
    cartadicredito VARCHAR(16), 
    PRIMARY KEY(codice),
    FOREIGN KEY(cartadicredito) REFERENCES cartadicredito(numero) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE noleggio (
    id VARCHAR(36) NOT NULL,
    orarioinizionoleggio TIMESTAMP,
    orariofinenoleggio TIMESTAMP,
    bicicletta VARCHAR(36),
    abbonamento VARCHAR(36),
    totem VARCHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY(bicicletta) REFERENCES bicicletta(id),
    FOREIGN KEY(abbonamento) REFERENCES abbonamento(codice)
);
















--------------------

INSERT INTO cartadicredito VALUES ('numerocarta1', '01-10-2023', 232);
INSERT INTO abbonamento VALUES ('idabbonamento1', 'dennissonis535', 'ANNUALE', true, false, '12-11-2021', '15-12-2021', '10-11-2022', 0, 'numerocarta1');