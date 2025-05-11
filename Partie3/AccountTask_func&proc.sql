----------------------------------------------
--         ACCOUNT TASK MANAGEMENT          --
----------------------------------------------

-- View for AccountTask data
CREATE OR REPLACE VIEW "AccountTaskView" AS
SELECT
    at."taskID",
    at."bank_accountID",
    t."status",
    t."action_type"
FROM "AccountTask" at
INNER JOIN "Task" t ON at."taskID" = t."ID";

-- Function to read a AccountTask by ID
CREATE OR REPLACE FUNCTION ReadAccountTaskByID (
    p_taskID INT
) RETURN SYS_REFCURSOR AS
    accountTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN accountTask_cursor FOR
    SELECT * FROM "AccountTaskView"
    WHERE "taskID" = p_taskID;

    RETURN accountTask_cursor;
END ReadAccountTaskByID;

-- Function to read all AccountTasks
CREATE OR REPLACE FUNCTION ReadAllAccountTasks
RETURN SYS_REFCURSOR AS
    accountTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN accountTask_cursor FOR
    SELECT * FROM "AccountTaskView";

    RETURN accountTask_cursor;
END ReadAllAccountTasks;

-- Function to read all pending AccountTasks 
CREATE OR REPLACE FUNCTION ReadAllPendingAccountTasks
RETURN SYS_REFCURSOR AS
    accountTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN accountTask_cursor FOR
    SELECT * FROM "AccountTaskView"
    WHERE "status" = 'pending';

    RETURN accountTask_cursor;
END ReadAllPendingAccountTasks;

-- Procedure to create a new AccountTask
CREATE OR REPLACE PROCEDURE CreateAccountTask (
    p_bank_accountID INT,
    p_action_type VARCHAR2,
    p_status VARCHAR2 := 'pending'
) AS
    v_taskID INT;
BEGIN
    -- Insert into Task table
    INSERT INTO "Task" ("status", "action_type")
    VALUES (p_status, p_action_type)
    RETURNING "ID" INTO v_taskID;

    -- Insert into AccountTask table
    INSERT INTO "AccountTask" ("taskID", "bank_accountID")
    VALUES (v_taskID, p_bank_accountID);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('AccountTask created successfully with task ID: ' || v_taskID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateAccountTask;

-- Procedure to update a AccountTask
CREATE OR REPLACE PROCEDURE UpdateAccountTask (
    p_taskID INT,
    p_status VARCHAR2
) AS
BEGIN
    UPDATE "Task"
    SET
        "status" = NVL(p_status, "status")
    WHERE "ID" = p_taskID;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('AccountTask updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateAccountTask;
