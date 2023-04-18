
SET check_function_bodies = false;

CREATE TABLE "user"(
  id integer NOT NULL,
  first_name varchar(20) NOT NULL,
  last_name varchar(120) NOT NULL,
  email varchar(80) NOT NULL,
  phone_number char(9) NOT NULL,
  phone_country_code char(3) NOT NULL,
  phone_code_area char(3) NOT NULL,
  user_type_id integer NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY(id)
);

CREATE TABLE user_type(
  id integer NOT NULL,
  "type" varchar(40) NOT NULL,
  description varchar(140) NOT NULL,
  "" varchar(140) NOT NULL,
  CONSTRAINT user_type_pkey PRIMARY KEY(id)
);

CREATE TABLE users_roles(
  id integer NOT NULL,
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT users_roles_pkey PRIMARY KEY(id)
);

CREATE TABLE user_credentials(
  id integer NOT NULL,
  "password" varchar(140) NOT NULL,
  username varchar(40) NOT NULL,
  CONSTRAINT user_credentials_pkey PRIMARY KEY(id)
);

CREATE TABLE "role"(
  id integer NOT NULL,
  "name" char(40) NOT NULL,
  description varchar(140) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY(id)
);

ALTER TABLE "user"
  ADD CONSTRAINT user_user_type_id_fkey
    FOREIGN KEY (user_type_id) REFERENCES user_type (id);

ALTER TABLE users_roles
  ADD CONSTRAINT users_roles_user_id_fkey
    FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE users_roles
  ADD CONSTRAINT users_roles_role_id_fkey
    FOREIGN KEY (role_id) REFERENCES "role" (id);
