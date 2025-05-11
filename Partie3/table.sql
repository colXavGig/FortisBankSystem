CONNECT sys/sys as sysdba

CREATE USER c##bank_xg IDENTIFIED BY "123";

GRANT ALL PRIVILEGES TO c##bank_xg;

CONNECT c##bank_xg/123;

CREATE TABLE "BUser" (
    "ID" INT PRIMARY KEY,
    "firstname" VARCHAR2(50) NOT NULL,
    "lastname" VARCHAR2(50) NOT NULL,
    "nip" VARCHAR2(50) NOT NULL,
    "address" VARCHAR2(250) NOT NULL,
    "telephone" VARCHAR2(50) NOT NULL,
    "email" VARCHAR2(100) UNIQUE NOT NULL
);

CREATE TABLE "Client"(
    "userID" INT PRIMARY KEY,
    FOREIGN KEY ("userID") REFERENCES "BUser"("ID")
        ON DELETE CASCADE
);

CREATE TABLE "Manager"(
    "userID" INT PRIMARY KEY,
    "role" VARCHAR2(50) NOT NULL,
    FOREIGN KEY ("userID") REFERENCES "BUser"("ID")
        ON DELETE CASCADE
);

CREATE TABLE "BankAccount"(
    "ID" INT PRIMARY KEY,
    "userID" INT NOT NULL,
    "balance" DECIMAL(10, 2) NOT NULL,
    "creation_date" DATE NOT NULL,
    FOREIGN KEY ("userID") REFERENCES "BUser"("ID")
        ON DELETE CASCADE
);

CREATE TABLE "CheckingAccount"(
    "bank_accountID" INT PRIMARY KEY,
    "transaction_fee" DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE
);

CREATE TABLE "SavingAccount"(
    "bank_accountID" INT PRIMARY KEY,
    "interest_rate" DECIMAL(5, 4) NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE
);

CREATE TABLE "CurrencyAccount"(
    "bank_accountID" INT PRIMARY KEY,
    "currency" VARCHAR2(3) NOT NULL,
    "exchange_rate" DECIMAL(10, 4) NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE,
    CONSTRAINT currency_check CHECK ("currency" IN ('USD', 'EUR', 'GBP', 'JPY', 'CHF', 'AUD', 'CAD', 'NZD'))
);

CREATE TABLE "CreditAccount"(
    "bank_accountID" INT PRIMARY KEY,
    "credit_limit" DECIMAL(10, 2) NOT NULL,
    "interest_rate" DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE,
    CONSTRAINT positive_rate_chk CHECK ("interest_rate" >= 0)
);

CREATE TABLE "TransactionRecord"(
    "historicID" INT NOT NULL,
    "bank_accountID" INT NOT NULL,
    "type" VARCHAR2(50) NOT NULL,
    "operation" DECIMAL(10, 2) NOT NULL,
    "balance" DECIMAL(10, 2) NOT NULL,
    "date" DATE NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE,
    PRIMARY KEY ("bank_accountID", "historicID")
);

CREATE TABLE "Notification"(
    "ID" INT PRIMARY KEY,
    "bank_accountID" INT NOT NULL,
    "clientID" INT NOT NULL,
    "status" VARCHAR2(50) NOT NULL,
    "message" VARCHAR2(250) NOT NULL,
    FOREIGN KEY ("clientID") REFERENCES "Client"("userID")
        ON DELETE CASCADE,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE,
    CONSTRAINT notification_status_check CHECK ("status" IN ('sent', 'read', 'archived'))
);

CREATE TABLE "Task"(
    "ID" INT PRIMARY KEY,
    "status" VARCHAR2(50) NOT NULL,
    "action_type" VARCHAR2(50) NOT NULL,
    CONSTRAINT task_status_check 
        CHECK ("status" IN ('pending', 'in_progress', 'accepted', 'rejected', 'completed'))
    CONSTRAINT task_action_type_check 
        CHECK ("action_type" IN ('creation', 'deletion'))
);

CREATE TABLE "UserTask"(
    "taskID" INT PRIMARY KEY,
    "userID" INT NOT NULL,
    "user_type" VARCHAR2(50) NOT NULL,
    FOREIGN KEY ("userID") REFERENCES "BUser"("ID")
        ON DELETE CASCADE,
    FOREIGN KEY ("taskID") REFERENCES "Task"("ID")    
        ON DELETE CASCADE,
    CONSTRAINT user_type_check CHECK ("user_type" IN ('client', 'manager'))
    
);

CREATE TABLE "AccountTask"(
    "taskID" INT PRIMARY KEY,
    "bank_accountID" INT NOT NULL,
    FOREIGN KEY ("bank_accountID") REFERENCES "BankAccount"("ID")
        ON DELETE CASCADE,
    FOREIGN KEY ("taskID") REFERENCES "Task"("ID")
        ON DELETE CASCADE
);

