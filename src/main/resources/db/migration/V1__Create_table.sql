create table if not exists chat
(
    usrid     bigint not null,
    chatmesid bigint not null,
    primary key (usrid, chatmesid)
) engine = InnoDB;
create table if not exists chatmessage
(
    id          bigint not null auto_increment,
    authorid    bigint,
    author_name varchar(255),
    createddate datetime,
    filename    varchar(255),
    friend_id   bigint,
    friend_name varchar(255),
    text        varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists commessage
(
    id          bigint not null auto_increment,
    authorname  varchar(255),
    createddate datetime,
    filename    varchar(255),
    text        varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists communities
(
    id     bigint not null auto_increment,
    avatar varchar(255),
    name   varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists community_admins
(
    comm_id bigint not null,
    usr_id  bigint not null,
    primary key (comm_id, usr_id)
) engine = InnoDB;
create table if not exists community_subers
(
    usr_id  bigint not null,
    comm_id bigint not null,
    primary key (usr_id, comm_id)
) engine = InnoDB;
create table if not exists community_users
(
    comm_id bigint not null,
    usr_id  bigint not null,
    primary key (comm_id, usr_id)
) engine = InnoDB;
create table if not exists comtomes
(
    comid bigint not null,
    mesid bigint not null,
    primary key (comid, mesid)
) engine = InnoDB;
create table if not exists friends
(
    usr_id    bigint not null,
    friend_id bigint not null,
    primary key (usr_id, friend_id)
) engine = InnoDB;
create table if not exists user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;
create table if not exists usr
(
    id          bigint not null auto_increment,
    birthdate   datetime,
    email       varchar(255),
    password    varchar(255),
    user_avatar varchar(255) default 'default.jpg',
    username    varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists usrmessage
(
    id          bigint not null auto_increment,
    authorid    bigint,
    authorname  varchar(255),
    createddate datetime,
    filename    varchar(255),
    text        varchar(255),
    primary key (id)
) engine = InnoDB;
create table if not exists usrtomes
(
    usrid bigint not null,
    msgid bigint not null,
    primary key (usrid, msgid)
)
    engine = InnoDB;
alter table comtomes
    add constraint UK_tqo4ph0eurpytk88q677has1p unique (mesid);
alter table usrtomes
    add constraint UK_evcr4l33xkna4gdncuq8xi633 unique (msgid);
alter table chat
    add constraint FKfy5kc7fx9hi4g2pesh2t50wi4 foreign key (chatmesid) references chatmessage (id);
alter table chat
    add constraint FK7gldn9six5x38x5e86hmutkhf foreign key (usrid) references usr (id);
alter table community_admins
    add constraint FKonkpcygnst1tyd13u7ufpx7b8 foreign key (usr_id) references usr (id);
alter table community_admins
    add constraint FK834mv7x0e2rh6w5943obw720l foreign key (comm_id) references communities (id);
alter table community_subers
    add constraint FK4bpxc5pix71a1qb7vjiponfsl foreign key (comm_id) references communities (id);
alter table community_subers
    add constraint FK8k0qp8s3v3dscpmnxcub0vtla foreign key (usr_id) references usr (id);
alter table community_users
    add constraint FKbjrstju6p2i0s2lk83wsvk6oo foreign key (usr_id) references usr (id);
alter table community_users
    add constraint FKayca598axnrk29wq1kwhvfswt foreign key (comm_id) references communities (id);
alter table comtomes
    add constraint FK3m1wn3rj6jqyfmj9676aekque foreign key (mesid) references commessage (id);
alter table comtomes
    add constraint FKdo6ec6e86a0yvueedidxgvea1 foreign key (comid) references communities (id);
alter table friends
    add constraint FK2d1vgb7nkjya4bpekkcym16u7 foreign key (friend_id) references usr (id);
alter table friends
    add constraint FKpvmr4oq7a9f74fnvlq68y2q16 foreign key (usr_id) references usr (id);
alter table user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id);
alter table usrtomes
    add constraint FKs5t1jd2dtb2rrqtf0w1tr5k6r foreign key (msgid) references usrmessage (id);
alter table usrtomes
    add constraint FK3yajj0wgslk4e7f8sj70hdsdm foreign key (usrid) references usr (id);