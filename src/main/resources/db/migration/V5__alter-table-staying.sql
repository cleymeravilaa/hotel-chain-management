ALTER TABLE stayings ADD COLUMN invoice_id BIGINT;
ALTER TABLE stayings ADD FOREIGN KEY (invoice_id) REFERENCES invoices (invoice_id);