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
    id          bigint       not null auto_increment,
    authorname  varchar(255),
    createddate datetime,
    filename    varchar(255),
    text        varchar(255),
    uni         varchar(512) not null,
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
create table if not exists photo
(
    id      bigint       not null auto_increment,
    name    varchar(255) not null,
    albumid bigint       not null,
    primary key (id)
) engine = InnoDB;
create table if not exists album
(
    id     bigint       not null auto_increment,
    name   varchar(255) not null,
    userid bigint       not null,
    primary key (id)
) engine = InnoDB;
create table if not exists photos
(
    albumid bigint not null,
    photoid bigint not null,
    primary key (albumid, photoid)
) engine = InnoDB;
create table if not exists albums
(
    usrid   bigint not null,
    albumid bigint not null,
    primary key (usrid, albumid)
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
    id          bigint       not null auto_increment,
    authorid    bigint,
    authorname  varchar(255),
    createddate datetime,
    filename    varchar(255),
    text        varchar(255),
    uni         varchar(512) not null,
    primary key (id)
) engine = InnoDB;
create table if not exists usrtomes
(
    usrid bigint not null,
    msgid bigint not null,
    primary key (usrid, msgid)
)
    engine = InnoDB;
create table if not exists comment
(
    id          bigint        not null auto_increment,
    createddate datetime,
    text        varchar(2048) not null,
    authorname  varchar(255)  not null,
    authorid    bigint        not null,
    filename    varchar(255),
    primary key (id)
) engine = InnoDB;

create table if not exists comments_comm
(
    commmesid bigint not null,
    commentid bigint not null,
    primary key (commentid, commmesid)
) engine = InnoDB;
create table if not exists comments_user
(
    usrmesid  bigint not null,
    commentid bigint not null,
    primary key (commentid, usrmesid)
) engine = InnoDB;

create table if not exists usermessage_likes
(
    usermessage_id bigint not null ,
    user_id bigint not null,
    primary key (usermessage_id, user_id)
) engine = InnoDB;

create table if not exists commessage_likes
(
    commessage_id bigint not null ,
    user_id bigint not null,
    primary key (commessage_id, user_id)
) engine = InnoDB;

create table if not exists comment_like
(
    comment_id bigint not null ,
    user_id bigint not null,
    primary key (comment_id, user_id)
) engine = InnoDB;

alter table comment_like add constraint ccml1 foreign key (comment_id) references comment(id);
alter table comment_like add constraint ccml2 foreign key (user_id) references usr(id);

alter table usermessage_likes add constraint usml1 foreign key (usermessage_id) references usrmessage(id);
alter table usermessage_likes add constraint usml2 foreign key (user_id) references usr(id);

alter table commessage_likes add constraint cml1 foreign key (commessage_id) references commessage(id);
alter table commessage_likes add constraint cml2 foreign key (user_id) references usr(id);

alter table comments_user
    add constraint cc1 foreign key (usrmesid) references usrmessage (id);
alter table comments_user
    add constraint cc2 foreign key (commentid) references comment (id);

alter table comments_comm
    add constraint c1 foreign key (commmesid) references commessage (id);
alter table comments_comm
    add constraint c2 foreign key (commentid) references comment (id);
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
alter table albums
    add constraint aa1 foreign key (usrid) references usr (id);
alter table albums
    add constraint aa2 foreign key (albumid) references album (id);
alter table photos
    add constraint a1 foreign key (albumid) references album (id);
alter table photos
    add constraint a2 foreign key (photoid) references photo (id);