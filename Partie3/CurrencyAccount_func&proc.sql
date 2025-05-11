----------------------------------------------
--       CURRENCY ACCOUNT MANAGEMENT        --
----------------------------------------------

-- View for CurrencyAccount data
CREATE OR REPLACE VIEW "CurrencyAccountView" AS
SELECT
    ba."ID" AS currency_accountID,
    ba."userID",
    ba."balance",
    ba."creation_date",
    ca."currency",
    ca."exchange_rate"
FROM "ActiveBankAccountView" ba
INNER JOIN "CurrencyAccount" ca ON ba."ID" = ca."bank_accountID";

-- Function to read a currency account by ID
CREATE OR REPLACE FUNCTION ReadCurrencyAccountByID (
    p_bank_accountID INT
) RETURN SYS_REFCURSOR AS
    currency_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN currency_account_cursor FOR
    SELECT * FROM "CurrencyAccountView"
    WHERE currency_accountID = p_bank_accountID;

    RETURN currency_account_cursor;
END ReadCurrencyAccountByID;

-- Function to read all currency accounts
CREATE OR REPLACE FUNCTION ReadAllCurrencyAccounts
RETURN SYS_REFCURSOR AS
    currency_account_cursor SYS_REFCURSOR;
BEGIN
    OPEN currency_account_cursor FOR
    SELECT * FROM "CurrencyAccountView";

    RETURN currency_account_cursor;
END ReadAllCurrencyAccounts;

-- Procedure to create a new currency account
CREATE OR REPLACE PROCEDURE CreateCurrencyAccount (
    p_userID INT,
    p_currency VARCHAR2,
    p_exchange_rate DECIMAL,
    p_balance DECIMAL := 0.00,
    p_creation_date DATE := SYSDATE
) AS
    v_bank_accountID INT;
BEGIN
    -- Insert into BankAccount table
    INSERT INTO "BankAccount" ("userID", "balance", "creation_date")
    VALUES (p_userID, p_balance, p_creation_date)
    RETURNING "ID" INTO v_bank_accountID;

    -- Insert into CurrencyAccount table
    INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate")
    VALUES (v_bank_accountID, p_currency, p_exchange_rate);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Currency Account created successfully with Bank Account ID: ' || v_bank_accountID);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CreateCurrencyAccount;

-- Procedure to update a currency account
CREATE OR REPLACE PROCEDURE UpdateCurrencyAccount (
    p_bank_accountID INT,
    p_balance DECIMAL := NULL,
    p_creation_date DATE := NULL,
    p_currency VARCHAR2 := NULL,
    p_exchange_rate DECIMAL := NULL
) AS
BEGIN
    UPDATE "BankAccount"
    SET
        "balance" = NVL(p_balance, "balance"),
        "creation_date" = NVL(p_creation_date, "creation_date")
    WHERE "ID" = p_bank_accountID;

    UPDATE "CurrencyAccount"
    SET
        "currency" = NVL(p_currency, "currency"),
        "exchange_rate" = NVL(p_exchange_rate, "exchange_rate")
    WHERE "bank_accountID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Currency Account updated successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END UpdateCurrencyAccount;

-- Procedure to delete a currency account
CREATE OR REPLACE PROCEDURE DeleteCurrencyAccount (
    p_bank_accountID INT
) AS
BEGIN
    DELETE FROM "BankAccount"
    WHERE "ID" = p_bank_accountID;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Currency Account deleted successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END DeleteCurrencyAccount;