create sequence rooms_id_seq;

create table ROOMS
(
    id          bigint  not null default nextval('rooms_id_seq') primary key,
    ROOM_NUMBER INTEGER not null,
    BEDS      INTEGER not null,
    DESCRIPTION VARCHAR(255)
);

