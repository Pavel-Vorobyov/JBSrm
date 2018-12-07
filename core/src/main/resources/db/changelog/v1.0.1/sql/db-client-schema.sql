DROP TYPE IF EXISTS client_role CASCADE;
CREATE TYPE client_role AS ENUM ('SYSTEM_ADMIN', 'ADMIN', 'DISPATCHER', 'MANAGER', 'DRIVER', 'COMPANY_OWNER');

ALTER TABLE client
ADD COLUMN IF NOT EXISTS client_role client_role NOT NULL;