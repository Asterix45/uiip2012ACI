begin transaction;

create table utenti (
    nomeutente varchar(10) not null primary key,
    nome varchar(50),
    password varchar(10),
    ruolo varchar(50)
);

create table proprietari (
    codicefiscale varchar(16) not null primary key,
    nome varchar(50) not null,
    cittadiresidenza varchar(50),
    annopatente integer
);

create table automobili (
    targa varchar(7) not null primary key,
    modello varchar(50),
    cilindrata integer,
    proprietario varchar(16) not null 
                          references proprietari(codicefiscale)
                                   on delete cascade
                                   on update cascade
);

insert into utenti values ('gianni', 'Gianni Rossi', 'gianni', 'amministratore');
insert into utenti values ('pinco', 'Pinco Palla', 'pinco', 'utente');

commit;