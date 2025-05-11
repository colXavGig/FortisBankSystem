----------------------------------------------
--         CREDIT ACCOUNT MANAGEMENT        --
----------------------------------------------

-- View for CreditAccount data
CREATE OR REPLACE VIEW "CreditAccountView" AS
SELECT
    ba."ID" AS credit_accountID,
    ba."userID",
    ba."balance",
    ba."creation_date",
    ca."interest_rate"
FROM "ActiveBankAccountView" ba
INNER JOIN "CreditAccount" ca ON ba."ID" = ca."bank_accountID";

-- Function to read a credit account by ID
CREATE OR REPLACE FUNCTION ReadCreditAccountByID (
    p_bank_accountID INT
) RETURN SYS_REFCURSOR AS
    credit_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN credit_account_cursor FOR
    SELECT * FROM "CreditAccountView"
    WHERE credit_accountID = p_bank_accountID;

    RETURN credit_account_cursor;
END ReadCreditAccountByID;

-- Function to read all credit accounts
CREATE OR REPLACE FUNCTION ReadAllCreditAccounts
RETURN SYS_REFCURSOR AS
    credit_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN credit_account_cursor FOR
    SELECT * FROM "CreditAccountView";

    RETURN credit_account_cursor;
END ReadAllCreditAccounts;

-- Procedure to create a new credit account
CREATE OR REPLACE PROCEDURE CreateCreditAccount (
    p_userID INT,
    p_interest_rate DECIMAL,
    p_balance DECIMAL := 0.00,
    p_creation_date DATE := SYSDATE
) AS
    v_bank_accountID INT;
BEGIN
    -- Insert into BankAccount table
    INSERT INTO "BankAccount" ("userID", "balance", "creation_date")
    VALUES (p_userID, p_balance, p_creation_date)
    RETURNING "ID" INTO v_bank_accountID;

    -- Insert into CreditAccount table
    INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate")
    VALUES (v_bank_accountID, p_interest_rate);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Credit Account created successfully with Bank Account ID: ' || v_bank_accountID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateCreditAccount;

-- Procedure to update a credit account
CREATE OR REPLACE PROCEDURE UpdateCreditAccount (
    p_bank_accountID INT,
    p_balance DECIMAL := NULL,
    p_creation_date DATE := NULL,
    p_interest_rate DECIMAL := NULL
) AS
BEGIN
    UPDATE "BankAccount"
    SET
        "balance" = NVL(p_balance, "balance"),
        "creation_date" = NVL(p_creation_date, "creation_date")
    WHERE "ID" = p_bank_accountID;

    UPDATE "CreditAccount"
    SET
        "interest_rate" = NVL(p_interest_rate, "interest_rate")
    WHERE "bank_accountID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Credit Account updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateCreditAccount;

-- Procedure to delete a credit account
CREATE OR REPLACE PROCEDURE DeleteCreditAccount (
    p_bank_accountID INT
) AS
BEGIN
    DELETE FROM "BankAccount"
    WHERE "ID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Credit Account deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteCreditAccount;