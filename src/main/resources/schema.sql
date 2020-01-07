DROP TABLE IF EXISTS public.address;
CREATE TABLE public.address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    phoneNumber INTEGER,
    street VARCHAR,
    city VARCHAR,
    state VARCHAR,
    country VARCHAR,
    pinCode INTEGER,
);

INSERT INTO public.address (id, phoneNumber, street, city, state, country, pinCode) VALUES (1, 298687764, 'Folush', 'Hrodna', 'Hrodna Region', 'Belarus', 230006);
INSERT INTO public.address (id, phoneNumber, street, city, state, country, pinCode) VALUES (2, 336549871, 'Strelkovaya', 'Hrodna', 'Hrodna Region', 'Belarus', 230009);


DROP TABLE IF EXISTS public.customer;
CREATE TABLE public.customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR,
    password VARCHAR,
    email VARCHAR,
);

INSERT INTO public.customer (id, name, password, email) VALUES (1, 'Dzmitry', '1488', 'amator@gmail.com');
INSERT INTO public.customer (id, name, password, email) VALUES (2, 'Volha', 'Dance', 'love@gmail.com');


DROP TABLE IF EXISTS public.customers_addresses;
CREATE TABLE public.customers_addresses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
        customer_id BIGINT,
        address_id BIGINT,
            FOREIGN KEY (customer_id) REFERENCES public.customer (id),
            FOREIGN KEY (address_id) REFERENCES public.address (id),
);

INSERT INTO public.customers_addresses (id, customer_id, address_id) VALUES (1, 1, 1);
INSERT INTO public.customers_addresses (id, customer_id, address_id) VALUES (2, 1, 2);


DROP TABLE IF EXISTS public.provider;
CREATE TABLE public.provider (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR,
);

INSERT INTO public.provider (id, name) VALUES (1, 'Yuriy');
INSERT INTO public.provider (id, name) VALUES (2, 'Vasiliy');


DROP TABLE IF EXISTS public.heaver;
CREATE TABLE public.heaver (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR,
    age SMALLINT,
    salary INTEGER,
    bonus INTEGER,
);

INSERT INTO public.heaver (id, name, age, salary, bonus) VALUES (1, 'Anton', 25, 500, 100);
INSERT INTO public.heaver (id, name, age, salary, bonus) VALUES (2, 'Artyom', 30, 600, 200);


DROP TABLE IF EXISTS public.stock;
CREATE TABLE public.stock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quantity INTEGER,
        provider_id BIGINT,
        heaver_id BIGINT,
            FOREIGN KEY (provider_id) REFERENCES public.provider (id),
            FOREIGN KEY (heaver_id) REFERENCES public.heaver (id),
);

INSERT INTO public.stock (id, quantity, provider_id, heaver_id) VALUES (1, 1, 1, 1);
INSERT INTO public.stock (id, quantity, provider_id, heaver_id) VALUES (2, 3, 1, 2);


DROP TABLE IF EXISTS public.car;
CREATE TABLE public.car (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR,
    year SMALLINT,
    pinCode VARCHAR,
);

INSERT INTO public.car (id, model, year, pinCode) VALUES (1, 'ZAZ-965', 1969, 'KLP30061969');
INSERT INTO public.car (id, model, year, pinCode) VALUES (2, 'VAZ-2107', 1990, 'ABC14881990');


DROP TABLE IF EXISTS public.detail;
CREATE TABLE public.detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR,
    type VARCHAR,
    price DOUBLE,
        car_id BIGINT,
            FOREIGN KEY (car_id) REFERENCES public.car (id),
);

INSERT INTO public.detail (id, name, type, price, car_id) VALUES (1, 'Engine', 'Chassis', 107.58, 1);
INSERT INTO public.detail (id, name, type, price, car_id) VALUES (2, 'Wheel', 'Chassis', 25.14, 1);


DROP TABLE IF EXISTS public.details_stocks;
CREATE TABLE public.details_stocks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    stock_id BIGINT,
        detail_id BIGINT,
            FOREIGN KEY (stock_id) REFERENCES public.stock (id),
            FOREIGN KEY (detail_id) REFERENCES public.detail (id),
);

INSERT INTO public.details_stocks (id, stock_id, detail_id) VALUES (1, 1, 1);
INSERT INTO public.details_stocks (id, stock_id, detail_id) VALUES (2, 2, 1);


DROP TABLE IF EXISTS public.seller;
CREATE TABLE public.seller (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR,
    age SMALLINT,
    salary INTEGER,
    category SMALLINT,
);

INSERT INTO public.seller (id, name, age, salary, category) VALUES (1, 'Danuta', 20, 700, 2);
INSERT INTO public.seller (id, name, age, salary, category) VALUES (2, 'Anna', 30, 800, 1);


DROP TABLE IF EXISTS public.orders;
CREATE TABLE public.orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date VARCHAR,
    sum INTEGER,
        seller_id BIGINT,
        details_stocks_id BIGINT,
        customers_addresses_id BIGINT,
            FOREIGN KEY (seller_id) REFERENCES public.seller (id),
            FOREIGN KEY (details_stocks_id) REFERENCES public.details_stocks (id),
            FOREIGN KEY (customers_addresses_id) REFERENCES public.customers_addresses (id),
);

INSERT INTO public.orders (id, date, sum, seller_id, details_stocks_id, customers_addresses_id) VALUES (1, '2019-12-12', 1524, 1, 1, 1);
INSERT INTO public.orders (id, date, sum, seller_id, details_stocks_id, customers_addresses_id) VALUES (2, '2019-11-11', 123, 1, 1, 1);