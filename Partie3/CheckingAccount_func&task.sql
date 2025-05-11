----------------------------------------------
--       CHECKING ACCOUNT MANAGEMENT        --
----------------------------------------------

-- View for CheckingAccount data
CREATE OR REPLACE VIEW "CheckingAccountView" AS
SELECT
    ba."ID" AS checking_accountID,
    ba."userID",
    ba."balance",
    ba."creation_date",
    ca."transaction_fee"
FROM "ActiveBankAccountView" ba
INNER JOIN "CheckingAccount" ca ON ba."ID" = ca."bank_accountID";

-- Function to read a checking account by ID
CREATE OR REPLACE FUNCTION ReadCheckingAccountByID (
    p_bank_accountID INT
) RETURN SYS_REFCURSOR AS
    checking_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN checking_account_cursor FOR
    SELECT * FROM "CheckingAccountView"
    WHERE checking_accountID = p_bank_accountID;

    RETURN checking_account_cursor;
END ReadCheckingAccountByID;

-- Function to read all checking accounts
CREATE OR REPLACE FUNCTION ReadAllCheckingAccounts
RETURN SYS_REFCURSOR AS
    checking_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN checking_account_cursor FOR
    SELECT * FROM "CheckingAccountView";

    RETURN checking_account_cursor;
END ReadAllCheckingAccounts;

-- Procedure to create a new checking account
CREATE OR REPLACE PROCEDURE CreateCheckingAccount (
    p_userID INT,
    p_transaction_fee DECIMAL,
    p_balance DECIMAL := 0.00,
    p_creation_date DATE := SYSDATE
) AS
    v_bank_accountID INT;
BEGIN
    -- Insert into BankAccount table
    INSERT INTO "BankAccount" ("userID", "balance", "creation_date")
    VALUES (p_userID, p_balance, p_creation_date)
    RETURNING "ID" INTO v_bank_accountID;

    -- Insert into CheckingAccount table
    INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee")
    VALUES (v_bank_accountID, p_transaction_fee);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Checking Account created successfully with Bank Account ID: ' || v_bank_accountID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateCheckingAccount;

-- Procedure to update a checking account
CREATE OR REPLACE PROCEDURE UpdateCheckingAccount (
    p_bank_accountID INT,
    p_balance DECIMAL := NULL,
    p_creation_date DATE := NULL,
    p_transaction_fee DECIMAL := NULL
) AS
BEGIN
    UPDATE "BankAccount"
    SET
        "balance" = NVL(p_balance, "balance"),
        "creation_date" = NVL(p_creation_date, "creation_date")
    WHERE "ID" = p_bank_accountID;

    UPDATE "CheckingAccount"
    SET
        "transaction_fee" = NVL(p_transaction_fee, "transaction_fee")
    WHERE "bank_accountID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Checking Account updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateCheckingAccount;

-- Procedure to delete a checking account
CREATE OR REPLACE PROCEDURE DeleteCheckingAccount (
    p_bank_accountID INT
) AS
BEGIN
    DELETE FROM "BankAccount"
    WHERE "ID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Checking Account deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteCheckingAccount;
