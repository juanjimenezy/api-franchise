CREATE TABLE IF NOT EXISTS franchise.franchise (
                                     id bigserial NOT NULL,
                                     "name" text NOT NULL,
                                     CONSTRAINT franchise_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS franchise.branch (
                                  id bigserial NOT NULL,
                                  franchise_id int8 NULL,
                                  "name" varchar(255) NULL,
                                  CONSTRAINT branch_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS franchise.product (
                                   id serial4 NOT NULL,
                                   branch_id int8 NULL,
                                   "name" varchar(255) NULL,
                                   state bool NULL,
                                   stock int4 NULL,
                                   CONSTRAINT product_pkey PRIMARY KEY (id)
);


INSERT INTO franchise.franchise ("name") VALUES
                                             ('Franchise Three'),
                                             ('Franchise Four'),
                                             ('Franchise One 1'),
                                             ('Franchise Two 2'),
                                             ('Franchise Five');

INSERT INTO franchise.branch (franchise_id,"name") VALUES
                                                       (1,'Sucursal B'),
                                                       (2,'Sucursal C'),
                                                       (1,'Sucursal A'),
                                                       (2,'Sucursal D');


INSERT INTO franchise.product (branch_id,"name",state,stock) VALUES
                                                                 (1,'CANELA',true,50),
                                                                 (1,'CAFE',true,55),
                                                                 (2,'COCA-COLA',true,40),
                                                                 (2,'PEPSI',true,30),
                                                                 (4,'BURGER',true,15),
                                                                 (5,'PIZZA',true,10);