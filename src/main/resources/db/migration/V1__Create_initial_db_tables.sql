CREATE TABLE IF NOT EXISTS investor
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(200),
    email      VARCHAR(100),
    phone      VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS pin
(
    id          SERIAL PRIMARY KEY,
    pin         VARCHAR(32),
    investor_id SERIAL,
    created_at  TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_investor_id
        FOREIGN KEY (investor_id)
            REFERENCES investor (id)
);

CREATE TABLE IF NOT EXISTS fund
(
    id               SERIAL PRIMARY KEY,
    transaction_type VARCHAR(100),
    investor_id      SERIAL,
    amount           DECIMAL,
    created_at       TIMESTAMP DEFAULT now(),
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
    price        DECIMAL,
    created_at   TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_investor_id
        FOREIGN KEY (investor_id)
            REFERENCES investor (id)
);

CREATE TABLE IF NOT EXISTS watchlist
(
    id          SERIAL PRIMARY KEY,
    investor_id SERIAL,
    created_at  TIMESTAMP DEFAULT now(),
    stocks      TEXT[],
    CONSTRAINT fk_investor_id
        FOREIGN KEY (investor_id)
            REFERENCES investor (id)
);
