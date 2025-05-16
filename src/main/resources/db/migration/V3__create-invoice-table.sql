CREATE TABLE invoices(
    invoice_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    staying BIGINT NOT NULL,
    issue_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_rooms INT NOT NULL,
    final_total FLOAT NOT NULL
)