DROP TABLE AD IF EXISTS;
DROP TABLE FA IF EXISTS;

CREATE TABLE AD (
    ID BIGINT IDENTITY NOT NULL PRIMARY KEY,
    Firma varchar(96),
    Email varchar(64),
    ADSplat SMALLINT
);

CREATE TABLE FA (
    ID BIGINT IDENTITY NOT NULL PRIMARY KEY,
    Cislo varchar(10),
    VarSym varchar(20),
    DatSplat datetime,
    KcCelkem DECIMAL,
    KcLikv DECIMAL,
    RefAD BIGINT
);
