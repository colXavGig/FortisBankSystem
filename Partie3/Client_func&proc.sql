----------------------------------------------
--              CLIENT MANAGEMENT           --
----------------------------------------------

-- Create views for Client tables
CREATE OR REPLACE VIEW "ClientView" AS
SELECT c."userID" AS clientID, u."firstname", u."lastname", u."nip", u."address", u."telephone", u."email"
FROM "ActiveUserView" u
INNER JOIN "Client" c ON u."ID" = c."userID";

-- Function to read a client by ID
CREATE OR REPLACE FUNCTION ReadClientByID (
    p_user_id INT
) RETURN SYS_REFCURSOR AS
    client_cursor SYS_REFCURSOR;
BEGIN
    OPEN client_cursor FOR
    SELECT * FROM "ClientView"
    WHERE clientID = p_user_id;

    RETURN client_cursor;
END ReadClientByID;

-- Function to read all clients
CREATE OR REPLACE FUNCTION ReadAllClients 
RETURN SYS_REFCURSOR AS
    client_cursor SYS_REFCURSOR;
BEGIN
    OPEN client_cursor FOR
    SELECT * FROM "ClientView";

    RETURN client_cursor;
END ReadAllClients;

-- Function to create a new client
CREATE OR REPLACE PROCEDURE CreateClient (
    p_firstname VARCHAR2,
    p_lastname VARCHAR2,
    p_nip VARCHAR2,
    p_address VARCHAR2,
    p_telephone VARCHAR2,
    p_email VARCHAR2
) AS
    v_user_id INT;
BEGIN
    -- Insert into User table
    INSERT INTO "User" ("firstname", "lastname", "nip", "address", "telephone", "email")
    VALUES (p_firstname, p_lastname, p_nip, p_address, p_telephone, p_email)
    RETURNING "ID" INTO v_user_id;

    -- Insert into Client table
    INSERT INTO "Client" ("userID")
    VALUES (v_user_id);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Client created successfully with User ID: ' || v_user_id);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Email already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateClient;



-- Procedure to update a client
CREATE OR REPLACE PROCEDURE UpdateClient (
    p_client_id INT,
    p_firstname VARCHAR2 := NULL,
    p_lastname VARCHAR2 := NULL,
    p_nip VARCHAR2 := NULL,
    p_address VARCHAR2 := NULL,
    p_telephone VARCHAR2 := NULL,
    p_email VARCHAR2 := NULL
) AS
BEGIN
    UPDATE "User"
    SET
        "firstname" = NVL(p_firstname, "firstname"),
        "lastname" = NVL(p_lastname, "lastname"),
        "nip" = NVL(p_nip, "nip"),
        "address" = NVL(p_address, "address"),
        "telephone" = NVL(p_telephone, "telephone"),
        "email" = NVL(p_email, "email")
    WHERE "ID" = p_client_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Client updated successfully.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Email already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateClient;

-- Procedure to delete a client
CREATE OR REPLACE PROCEDURE DeleteClient (
    p_user_id INT
) AS
BEGIN
    DELETE FROM "User"
    WHERE "ID" = p_user_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Client deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteClient;
