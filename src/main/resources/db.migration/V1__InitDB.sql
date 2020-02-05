create table group_order (
    id int4 not null,
    code varchar(255),
    full_price int4 not null,
    is_locked boolean not null,
    restaurant_id int4 not null,
    restaurant_name varchar(255),
    restaurant_url varchar(255),
    primary key (id))


create table member_order (
    id int4 not null,
    email varchar(255),
    name varchar(255),
    order_price int4 not null,
    products varchar(255),
    restaurant_id int4 not null,
    group_order_id int4,
    primary key (id))

alter table member_order
    add constraint group_order_fkey foreign key (group_order_id) references group_order