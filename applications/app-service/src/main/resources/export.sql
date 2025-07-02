CREATE SCHEMA IF NOT EXISTS franchise;

CREATE TABLE IF NOT EXISTS franchise.franchise (
                                                   id bigserial NOT NULL,
                                                   "name" text NOT NULL,
                                                   CONSTRAINT franchise_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS franchise.branch (
                                                id bigserial NOT NULL,
                                                franchise_id int4 NULL,
                                                "name" varchar(255) NULL,
    CONSTRAINT branch_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS franchise.product (
                                                 id bigserial NOT NULL,
                                                 branch_id int4 NULL,
                                                 "name" varchar(255) NULL,
    state bool NULL,
    stock int4 NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
    );

INSERT INTO franchise.franchise ("name") VALUES ('Franchise One 1');
INSERT INTO franchise.franchise ("name") VALUES ('Franchise Two 2');
INSERT INTO franchise.franchise ("name") VALUES ('Franchise Three');
INSERT INTO franchise.franchise ("name") VALUES ('Franchise Four');
INSERT INTO franchise.franchise ("name") VALUES ('Franchise Five');

INSERT INTO franchise.branch (franchise_id, "name") VALUES (1, 'Sucursal A');
INSERT INTO franchise.branch (franchise_id, "name") VALUES (1, 'Sucursal B');
INSERT INTO franchise.branch (franchise_id, "name") VALUES (2, 'Sucursal C');
INSERT INTO franchise.branch (franchise_id, "name") VALUES (2, 'Sucursal D');

INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (1, 'CANELA', true, 50);
INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (1, 'CAFE', true, 55);
INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (2, 'COCA-COLA', true, 40);
INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (2, 'PEPSI', true, 30);
INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (4, 'BURGER', true, 15);
INSERT INTO franchise.product (branch_id, "name", state, stock) VALUES (5, 'PIZZA', true, 10);
