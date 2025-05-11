----------------------------------------------
--         BANK ACCOUNT MANAGEMENT          --
----------------------------------------------

-- View for active bank accounts
CREATE OR REPLACE VIEW "RemovedBankAccountView" AS
SELECT
    ba."ID",
    ba."userID",
    ba."balance",
    ba."creation_date"
FROM "BankAccount" ba
INNER JOIN "AccountTaskView" at ON ba."ID" = at."bank_accountID"
WHERE at."action_type" = 'deletion'
  AND at."status" NOT IN ('pending', 'in progress', 'rejected');

CREATE OR REPLACE VIEW "ActiveBankAccountView" AS
SELECT
    ba."ID",
    ba."userID",
    ba."balance",
    ba."creation_date"
FROM "BankAccount" ba
INNER JOIN "AccountTaskView" at ON ba."ID" = at."bank_accountID"
WHERE ba."ID" NOT IN (SELECT "ID" FROM "RemovedBankAccountView")
  AND at."action_type" = 'creation'
  AND at."status" NOT IN ('pending', 'in progress', 'rejected');

