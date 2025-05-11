----------------------------------------------
--         NOTIFICATION MANAGEMENT          --
----------------------------------------------

-- Procedure to read all notifications for a client
CREATE OR REPLACE FUNCTION ReadAllUnreadNotificationsForClient (
        p_clientID INT
) RETURN SYS_REFCURSOR AS
        notification_cursor SYS_REFCURSOR;
BEGIN
        OPEN notification_cursor FOR
        SELECT * FROM "Notification"
        WHERE "clientID" = p_clientID
        AND "status" = 'sent'
        ORDER BY ID ASC;

        RETURN notification_cursor;
END ReadAllUnreadNotificationsForClient;

-- Function to read all notifications for a bank account that are not archived
CREATE OR REPLACE FUNCTION ReadAllNotificationsForBankAccount (
        p_bank_accountID INT
) RETURN SYS_REFCURSOR AS
        notification_cursor SYS_REFCURSOR;
BEGIN
        OPEN notification_cursor FOR
        SELECT * FROM "Notification"
        WHERE "bank_accountID" = p_bank_accountID
        AND "status" <> 'archived'
        ORDER BY ID ASC;

        RETURN notification_cursor;
END ReadAllNotificationsForBankAccount;

-- Procedure to create a new notification
CREATE OR REPLACE PROCEDURE CreateNotification (
        p_bank_accountID INT,
        p_clientID INT,
        p_message VARCHAR2,
        p_status VARCHAR2 := 'sent'
) AS 
BEGIN
        -- Insert into Notification table
        INSERT INTO "Notification" ("bank_accountID", "clientID", "message", "status")
        VALUES (p_bank_accountID, p_clientID, p_message, p_status);

        COMMIT;
EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
                ROLLBACK;
END CreateNotification;

-- Procedure to update a notification
CREATE OR REPLACE PROCEDURE UpdateNotification (
        p_ID INT,
        p_status VARCHAR2
) AS
BEGIN
        UPDATE "Notification"
        SET "status" = p_status
        WHERE ID = p_ID;

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Notification updated successfully.');
EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
                ROLLBACK;
END UpdateNotification;

-- Procedure to read a notification
CREATE OR REPLACE PROCEDURE SetNotificationToRead (
        p_ID INT
) AS
BEGIN
        UpdateNotification(p_ID, 'read');
        DBMS_OUTPUT.PUT_LINE('Notification read successfully.');
EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
                ROLLBACK;
END SetNotificationToRead;

-- Procedure to archive a notification
CREATE OR REPLACE PROCEDURE ArchiveNotification (
        p_ID INT
) AS
BEGIN
        UpdateNotification(p_ID, 'archived');
        DBMS_OUTPUT.PUT_LINE('Notification archived successfully.');    
EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
                ROLLBACK;
END ArchiveNotification;

