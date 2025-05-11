----------------------------------------------
--              MANAGER MANAGEMENT          --
----------------------------------------------

-- View for Manager data
CREATE OR REPLACE VIEW "ManagerView" AS
SELECT m."userID" AS managerID, u."firstname", u."lastname", u."nip", u."address", u."telephone", u."email", m."role"
FROM "ActiveUserView" u
INNER JOIN "Manager" m ON u."ID" = m."userID";

-- Function to read a manager by ID
CREATE OR REPLACE FUNCTION ReadManagerByID (
    p_user_id INT
) RETURN SYS_REFCURSOR AS
    manager_cursor SYS_REFCURSOR;
BEGIN
    OPEN manager_cursor FOR
    SELECT * FROM "ManagerView"
    WHERE managerID = p_user_id;

    RETURN manager_cursor;
END ReadManagerByID;

-- Function to read all managers
CREATE OR REPLACE FUNCTION ReadAllManagers
RETURN SYS_REFCURSOR AS
    manager_cursor SYS_REFCURSOR;
BEGIN
    OPEN manager_cursor FOR
    SELECT * FROM "ManagerView";

    RETURN manager_cursor;
END ReadAllManagers;

-- Procedure to create a new manager
CREATE OR REPLACE PROCEDURE CreateManager (
    p_firstname VARCHAR2,
    p_lastname VARCHAR2,
    p_nip VARCHAR2,
    p_address VARCHAR2,
    p_telephone VARCHAR2,
    p_email VARCHAR2,
    p_role VARCHAR2
) AS
    v_user_id INT;
BEGIN
    -- Insert into User table
    INSERT INTO "User" ("firstname", "lastname", "nip", "address", "telephone", "email")
    VALUES (p_firstname, p_lastname, p_nip, p_address, p_telephone, p_email)
    RETURNING "ID" INTO v_user_id;

    -- Insert into Manager table
    INSERT INTO "Manager" ("userID", "role")
    VALUES (v_user_id, p_role);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Manager created successfully with User ID: ' || v_user_id);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Email already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateManager;

-- Procedure to update a manager
CREATE OR REPLACE PROCEDURE UpdateManager (
    p_user_id INT,
    p_firstname VARCHAR2 := NULL,
    p_lastname VARCHAR2 := NULL,
    p_nip VARCHAR2 := NULL,
    p_address VARCHAR2 := NULL,
    p_telephone VARCHAR2 := NULL,
    p_email VARCHAR2 := NULL,
    p_role VARCHAR2 := NULL
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
    WHERE "ID" = p_user_id;

    UPDATE "Manager"
    SET
        "role" = NVL(p_role, "role")
    WHERE "userID" = p_user_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Manager updated successfully.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: Email already exists.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateManager;


-- Procedure to delete a manager
CREATE OR REPLACE PROCEDURE DeleteManager (
    p_user_id INT
) AS
BEGIN
    DELETE FROM "User"
    WHERE "ID" = p_user_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Manager deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteManager;