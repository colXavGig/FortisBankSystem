----------------------------------------------
--         USER TASK MANAGEMENT             --
----------------------------------------------

-- View for UserTask data
CREATE OR REPLACE VIEW "UserTaskView" AS
SELECT
    ct."taskID",
    "Task"."status",
    "Task"."action_type",
    ct."userID",
    ct."user_type"
FROM "UserTask" ct
INNER JOIN "Task" ON ct."taskID" = "Task"."ID";

-- Function to read a clientTask by ID
CREATE OR REPLACE FUNCTION ReadUserTaskByID (
    p_taskID INT
) RETURN SYS_REFCURSOR AS
    userTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN userTask_cursor FOR
    SELECT * FROM "UserTaskView"
    WHERE "taskID" = p_taskID;

    RETURN userTask_cursor;
END ReadUserTaskByID;

-- Function to read all clientTasks
CREATE OR REPLACE FUNCTION ReadAllUserTasks
RETURN SYS_REFCURSOR AS
    userTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN userTask_cursor FOR
    SELECT * FROM "UserTaskView";

    RETURN userTask_cursor;
END ReadAllUserTasks;

-- Function to read all pending clientTasks
CREATE OR REPLACE FUNCTION ReadAllPendingUserTasks
RETURN SYS_REFCURSOR AS
    userTask_cursor SYS_REFCURSOR;
BEGIN
    OPEN userTask_cursor FOR
    SELECT * FROM "UserTaskView"
    WHERE "status" = 'pending';

    RETURN userTask_cursor;
END ReadAllPendingUserTasks;

-- Procedure to create a new clientTask
CREATE OR REPLACE PROCEDURE CreateUserTask (
    p_userID INT,
    p_action_type VARCHAR2,
    p_user_type VARCHAR2,
    p_status VARCHAR2 := 'pending'
) AS
    v_taskID INT;
BEGIN
    -- Insert into Task table
    INSERT INTO "Task" ("status", "action_type")
    VALUES (p_status, p_action_type)
    RETURNING "ID" INTO v_taskID;

    -- Insert into ClientTask table
    INSERT INTO "UserTask" ("taskID", "userID", "user_type")
    VALUES (v_taskID, p_userID, p_user_type);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('ClientTask created successfully with task ID: ' || v_taskID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateUserTask;

-- Procedure to update a clientTask
CREATE OR REPLACE PROCEDURE UpdateUserTask (
    p_taskID INT,
    p_status VARCHAR2
) AS
BEGIN
    UPDATE "Task"
    SET
        "status" = NVL(p_status, "status")
    WHERE "ID" = p_taskID;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('ClientTask updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateUserTask;

-- Procedure to delete a clientTask
CREATE OR REPLACE PROCEDURE DeleteUserTask (
    p_taskID INT
) AS
BEGIN
    DELETE FROM "Task"
    WHERE "ID" = p_taskID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('ClientTask deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteUserTask;