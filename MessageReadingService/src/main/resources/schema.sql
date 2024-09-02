create table auth_key(
    id binary(16),
    exp_time long NOT NULL
);
create table messages(
    id binary(16),
    message varchar(250) NOT NULL
);