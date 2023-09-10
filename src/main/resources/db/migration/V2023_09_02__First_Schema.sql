CREATE TABLE users (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (email)

) ENGINE = InnoDB;

CREATE TABLE operators (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(50),
    pin CHAR(6) NOT NULL,

    PRIMARY KEY (id)

) ENGINE = InnoDB;

CREATE TABLE categories (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE products (
    id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)

) ENGINE = InnoDB;

CREATE TABLE variants (
    id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(16,2) NOT NULL CHECK (price >= 0),
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products (id)

) ENGINE = InnoDB;

CREATE TABLE customers (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    email VARCHAR(50),
    address TEXT,
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id)

) ENGINE = InnoDB;

CREATE TABLE `tables` (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id)

) ENGINE = InnoDB;

CREATE TABLE `orders` (
    id VARCHAR(36) NOT NULL,
    table_id VARCHAR(36),
    customer_id VARCHAR(36),
    code VARCHAR(50) NOT NULL,
    description TEXT,
    subtotal DECIMAL(16,2) NOT NULL CHECK (subtotal >= 0),
    total DECIMAL(16,2) NOT NULL CHECK (total >= 0),
    order_time TIME NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (table_id) REFERENCES tables (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    UNIQUE (code)

) ENGINE = InnoDB;

CREATE TABLE order_details (
    id VARCHAR(36) NOT NULL,
    order_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    price DECIMAL(16,2) NOT NULL CHECK (price >= 0),
    subtotal DECIMAL(16,2) NOT NULL CHECK (subtotal >= 0),
    total DECIMAL(16,2) NOT NULL CHECK (total >= 0),
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (variant_id) REFERENCES variants (id)

) ENGINE = InnoDB;

CREATE TABLE sales (
    id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36),
    table_id VARCHAR(36),
    code VARCHAR(100) NOT NULL,
    is_void BOOLEAN,
    subtotal DECIMAL(16,2) NOT NULL CHECK (subtotal >= 0),
    total DECIMAL(16,2)NOT NULL CHECK (total >= 0),
    sale_time TIME NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (table_id) REFERENCES tables (id),
    UNIQUE (code)

) ENGINE = InnoDB;

CREATE TABLE sale_details (
    id VARCHAR(36) NOT NULL,
    sale_id VARCHAR(36) NOT NULL,
    variant_id VARCHAR(36) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    price DECIMAL(16,2) NOT NULL CHECK (PRICE >= 0),
    subtotal DECIMAL(16,2) NOT NULL CHECK (subtotal >= 0),
    total DECIMAL(16,2) NOT NULL CHECK (total >= 0),
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (sale_id) REFERENCES sales (id),
    FOREIGN KEY (variant_id) REFERENCES variants (id)

) ENGINE = InnoDB;

CREATE TABLE shifts (
    id VARCHAR(36) NOT NULL,
    operator_id VARCHAR(36) NOT NULL,
    beginning_balance DECIMAL(16,2) NOT NULL CHECK (beginning_balance >= 0),
    ending_balance DECIMAL(16,2) CHECK (ending_balance >= 0),
    beginning_time TIME NOT NULL,
    ending_time TIME,
    beginning_date DATE NOT NULL,
    ending_date DATE,
    shift_status ENUM("OPEN", "CLOSED"),
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (operator_id) REFERENCES operators (id)

) ENGINE = InnoDB;

CREATE TABLE shift_details (
    id VARCHAR(36) NOT NULL,
    shift_id VARCHAR(36) NOT NULL,
    balance_in DECIMAL(16,2) NOT NULL CHECK (balance_in >= 0),
    balance_out DECIMAL(16,2) CHECK (balance_out >= 0),
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    PRIMARY KEY (id),
    FOREIGN KEY (shift_id) REFERENCES shifts (id)

) ENGINE = InnoDB;