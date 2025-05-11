----------------------------------------------
--      TRANSACTION RECORD MANAGEMENT       --
----------------------------------------------

-- Function to read a transaction record for a specific bank account
CREATE OR REPLACE FUNCTION ReadTransactionRecordFor(
    bank_accountID INT
) return SYS_REFCURSOR AS
    transaction_cursor SYS_REFCURSOR;
BEGIN
    OPEN transaction_cursor FOR
    SELECT * FROM "TransactionRecord"
    WHERE bank_accountID = bank_accountID
    ORDER BY "historicID" ASC;

    RETURN transaction_cursor;
END ReadTransactionRecordFor;

-- Function to read all transaction records
CREATE OR REPLACE FUNCTION ReadAllTransactionRecords
RETURN SYS_REFCURSOR AS
        transaction_cursor SYS_REFCURSOR;
BEGIN
        OPEN transaction_cursor FOR
        SELECT * FROM "TransactionRecord"
        ORDER BY "historicID" ASC;

        RETURN transaction_cursor;
END ReadAllTransactionRecords;

-- Procedure to create a new transaction record
CREATE OR REPLACE PROCEDURE CreateTransactionRecord (
        p_bank_accountID INT,
        p_type VARCHAR2,
        p_balance DECIMAL,
        p_operation DECIMAL,
        p_date DATE := SYSDATE
) AS
BEGIN
        -- Insert into TransactionRecord table
        INSERT INTO "TransactionRecord" ("bank_accountID", "type", "balance", "operation", "date")
        VALUES (p_bank_accountID, p_type, p_balance, p_operation, p_date);

        COMMIT;
EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
                ROLLBACK;
END CreateTransactionRecord;
