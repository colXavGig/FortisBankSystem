----------------------------------------------
--         SAVING ACCOUNT MANAGEMENT        --
----------------------------------------------

-- View for SavingAccount data
CREATE OR REPLACE VIEW "SavingAccountView" AS
SELECT
    ba."ID" AS saving_accountID,
    ba."userID",
    ba."balance",
    ba."creation_date",
    sa."interest_rate"
FROM "ActiveBankAccountView" ba
INNER JOIN "SavingAccount" sa ON ba."ID" = sa."bank_accountID";

-- Function to read a saving account by ID
CREATE OR REPLACE FUNCTION ReadSavingAccountByID (
    p_bank_accountID INT
) RETURN SYS_REFCURSOR AS
    saving_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN saving_account_cursor FOR
    SELECT * FROM "SavingAccountView"
    WHERE saving_accountID = p_bank_accountID;

    RETURN saving_account_cursor;
END ReadSavingAccountByID;

-- Function to read all saving accounts
CREATE OR REPLACE FUNCTION ReadAllSavingAccounts
RETURN SYS_REFCURSOR AS
    saving_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN saving_account_cursor FOR
    SELECT * FROM "SavingAccountView";

    RETURN saving_account_cursor;
END ReadAllSavingAccounts;

-- Procedure to create a new saving account
CREATE OR REPLACE PROCEDURE CreateSavingAccount (
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

    -- Insert into SavingAccount table
    INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate")
    VALUES (v_bank_accountID, p_interest_rate);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Saving Account created successfully with Bank Account ID: ' || v_bank_accountID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateSavingAccount;

-- Procedure to update a saving account
CREATE OR REPLACE PROCEDURE UpdateSavingAccount (
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

    UPDATE "SavingAccount"
    SET
        "interest_rate" = NVL(p_interest_rate, "interest_rate")
    WHERE "bank_accountID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Saving Account updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateSavingAccount;

-- Procedure to delete a saving account
CREATE OR REPLACE PROCEDURE DeleteSavingAccount (
    p_bank_accountID INT
) AS
BEGIN
    DELETE FROM "BankAccount"
    WHERE "ID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Saving Account deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteSavingAccount;
