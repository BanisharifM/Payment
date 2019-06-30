-- auto-generated definition
create table applicationservice
(
    id                 bigserial not null
        constraint applicationservice_pkey
            primary key,
    logouturl          varchar(255),
    name               varchar(255),
    privatekeyexponent varchar(2100),
    privatekeymodule   varchar(2100),
    publickeyexponent  varchar(2100),
    publickeymodule    varchar(2100)
);

alter table applicationservice
    owner to postgres;

