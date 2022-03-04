CREATE TABLE IF NOT EXISTS investor
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(200),
    email      VARCHAR(100),
    phone      VARCHAR(100),
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS fund
(
    id          SERIAL PRIMARY KEY,
    type        VARCHAR(64),
    investor_id SERIAL,
    amount      decimal,
    created_at  TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_investor_id
        FOREIGN KEY (investor_id)
            REFERENCES investor (id)
);

CREATE TABLE IF NOT EXISTS investment
(
    id           SERIAL PRIMARY KEY,
    type         VARCHAR(64),
    investor_id  SERIAL,
    stock_symbol VARCHAR(200),
    status       VARCHAR(200),
    price        decimal,
    created_at   TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_investor_id
        FOREIGN KEY (investor_id)
            REFERENCES investor (id)
);

