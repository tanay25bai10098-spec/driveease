CREATE TABLE IF NOT EXISTS vehicles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    category TEXT NOT NULL,
    registration_no TEXT NOT NULL UNIQUE,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    daily_rate REAL NOT NULL,
    seats INTEGER DEFAULT 0,
    engine_cc INTEGER DEFAULT 0,
    four_wheel_drive INTEGER DEFAULT 0,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS rentals (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    vehicle_id INTEGER NOT NULL,
    start_date TEXT NOT NULL,
    expected_return_date TEXT NOT NULL,
    actual_return_date TEXT,
    status TEXT NOT NULL,
    base_amount REAL NOT NULL,
    late_fee REAL DEFAULT 0,
    total_amount REAL NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES customers(id),
    FOREIGN KEY(vehicle_id) REFERENCES vehicles(id)
);
