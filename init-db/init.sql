-- Role: auth
-- DROP ROLE IF EXISTS auth;

CREATE ROLE auth WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION
    BYPASSRLS
    ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:Ue4ftYZNbRVPPVgqLsQFrQ==$MQKf0D4P6oVcF0kZTncNCTaV9ql80h8I9G5ekEPEq34=:73yOfvVhWIDUr+7UK4C0zcPlfxlSJD4NC/mbawfzV1g=';

COMMENT ON ROLE auth IS 'Auth - Adaptive Test Module';


-- Database: Auth

-- DROP DATABASE IF EXISTS "Auth";

CREATE DATABASE "Auth"
    WITH
    OWNER = auth
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_India.1252'
    LC_CTYPE = 'English_India.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE "Auth"
    IS 'Auth - Adaptive Test Module';


DROP SCHEMA IF EXISTS auth;

CREATE SCHEMA IF NOT EXISTS auth
    AUTHORIZATION auth;



SET search_path TO auth;



CREATE FUNCTION auth.update_timestamp() RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at := timezone('utc'::text, now());
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



-- Table: auth.users

DROP TABLE IF EXISTS auth.users;

CREATE TABLE IF NOT EXISTS auth.users
(
    user_id BIGSERIAL NOT NULL,
    username character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(255),
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_guid_key UNIQUE (guid)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.users
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_users
    BEFORE UPDATE ON auth.users
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();





-- Table: auth.roles

DROP TABLE IF EXISTS auth.roles;

CREATE TABLE IF NOT EXISTS auth.roles
(
    role_id BIGSERIAL NOT NULL,
    role_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    CONSTRAINT roles_role_name_key UNIQUE (role_name),
    CONSTRAINT roles_guid_key UNIQUE (guid)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.roles
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_roles
    BEFORE UPDATE ON auth.roles
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();






-- Table: auth.domains

DROP TABLE IF EXISTS auth.domains;

CREATE TABLE IF NOT EXISTS auth.domains
(
    domain_id BIGSERIAL NOT NULL,
    domain_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT deomains_pkey PRIMARY KEY (domain_id),
    CONSTRAINT domains_domain_name_key UNIQUE (domain_name),
    CONSTRAINT domains_domain_guid_key UNIQUE (guid)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.domains
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_domains
    BEFORE UPDATE ON auth.domains
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();






-- Table: auth.user_domains

DROP TABLE IF EXISTS auth.user_domains;

CREATE TABLE IF NOT EXISTS auth.user_domains
(
    user_domain_id BIGSERIAL NOT NULL,
    user_id bigint,
    domain_id bigint,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT user_domains_pkey PRIMARY KEY (user_domain_id),
    CONSTRAINT user_domains_user_domain_key UNIQUE (domain_id, user_id),
    CONSTRAINT user_domains_user_domain_guid_key UNIQUE (guid),
    CONSTRAINT user_id_fkey FOREIGN KEY (user_id)
        REFERENCES auth.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT domain_id_fkey FOREIGN KEY (domain_id)
        REFERENCES auth.domains (domain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.user_domains
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_user_domains
    BEFORE UPDATE ON auth.user_domains
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();







-- Table: auth.role_domains

DROP TABLE IF EXISTS auth.role_domains;

CREATE TABLE IF NOT EXISTS auth.role_domains
(
    role_domain_id BIGSERIAL NOT NULL,
    role_id bigint,
    domain_id bigint,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT role_domains_pkey PRIMARY KEY (role_domain_id),
    CONSTRAINT role_domains_role_domain_key UNIQUE (domain_id, role_id),
    CONSTRAINT role_domains_role_domain_guid_key UNIQUE (guid),
    CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES auth.roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_roles_domain_id_fkey FOREIGN KEY (domain_id)
        REFERENCES auth.domains (domain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.role_domains
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_role_domains
    BEFORE UPDATE ON auth.role_domains
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();










-- Table: auth.user_role_domains

DROP TABLE IF EXISTS auth.user_role_domains;

CREATE TABLE IF NOT EXISTS auth.user_role_domains
(
    user_role_domain_id BIGSERIAL NOT NULL,
    user_domain_id bigint,
    role_domain_id bigint,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT user_role_domains_user_role_domain_pkey PRIMARY KEY (user_role_domain_id),
    CONSTRAINT user_role_domains_user_role_domain_key UNIQUE (user_domain_id, role_domain_id),
    CONSTRAINT user_role_domains_user_role_domain_guid_key UNIQUE (guid),
    CONSTRAINT role_domain_id_fkey FOREIGN KEY (role_domain_id)
        REFERENCES auth.role_domains (role_domain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_domain_id_fkey FOREIGN KEY (user_domain_id)
        REFERENCES auth.user_domains (user_domain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.user_role_domains
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_user_role_domains
    BEFORE UPDATE ON auth.user_role_domains
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();





-- Table: auth.user_domain_sessions

DROP TABLE IF EXISTS auth.user_role_domain_sessions;

CREATE TABLE IF NOT EXISTS auth.user_role_domain_sessions
(
    user_role_domain_session_id BIGSERIAL NOT NULL,
    user_role_domain_id bigint,
    user_agent character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    guid uuid NOT NULL DEFAULT gen_random_uuid(),
    is_active boolean NOT NULL DEFAULT true,
    is_deleted boolean NOT NULL DEFAULT false,
    created_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    modified_at timestamp with time zone NOT NULL DEFAULT timezone('utc'::text, now()),
    created_by bigint NOT NULL DEFAULT 0,
    modified_by bigint NOT NULL DEFAULT 0,
    CONSTRAINT user_domain_sessions_pkey PRIMARY KEY (user_role_domain_session_id),
    CONSTRAINT user_domain_sessions_user_domain_agent_key UNIQUE (user_role_domain_id, user_agent),
    CONSTRAINT user_domain_sessions_user_domain_session_guid_key UNIQUE (guid),
    CONSTRAINT user_sessions_user_role_domain_id_fkey FOREIGN KEY (user_role_domain_id)
        REFERENCES auth.user_role_domains (user_role_domain_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS auth.user_role_domain_sessions
    OWNER to postgres;

CREATE TRIGGER update_timestamp_trigger_user_domain_sessions
    BEFORE UPDATE ON auth.user_role_domain_sessions
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();






INSERT INTO auth.roles(role_name) VALUES ('Auth');
INSERT INTO auth.roles(role_name) VALUES ('Admin');
INSERT INTO auth.roles(role_name) VALUES ('User');




INSERT INTO auth.domains(domain_name) VALUES ('IN');



INSERT INTO auth.role_domains(role_id, domain_id) VALUES (1, 1);
INSERT INTO auth.role_domains(role_id, domain_id) VALUES (2, 1);
INSERT INTO auth.role_domains(role_id, domain_id) VALUES (3, 1);
INSERT INTO auth.role_domains(role_id, domain_id) VALUES (4, 1);
INSERT INTO auth.role_domains(role_id, domain_id) VALUES (5, 1);

