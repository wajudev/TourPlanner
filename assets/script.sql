create table tours
(
    "tourId"          serial
        primary key,
    "tourName"        varchar(255) not null,
    "tourDescription" varchar(1000),
    "from"            varchar(255) not null,
    "to"              varchar(255) not null,
    "transportType"   varchar(255) not null,
    distance          numeric(6, 2),
    "estimatedTime"   varchar(255)
);

alter table tours
    owner to waju;

create table tour_logs
(
    tourlog_id  integer default nextval('table_name_tourlog_id_seq'::regclass) not null
        constraint tour_logs_pk
            primary key,
    date        varchar                                                        not null,
    difficulty  varchar(255),
    rating      numeric(6, 1),
    "totalTime" integer,
    comment     varchar(255),
    tour_id     integer
        constraint tour_id
            references tours
            on delete cascade
);

alter table tour_logs
    owner to waju;


