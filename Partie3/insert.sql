INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (1, 'John', 'Doe', '123456789', '123 Main St', '555-1234', 'john.doe@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (2, 'Jane', 'Smith', '987654321', '456 Oak Ave', '555-5678', 'jane.smith@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (3, 'Robert', 'Jones', '112233445', '789 Pine Ln', '555-9012', 'robert.jones@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (4, 'Emily', 'Brown', '667788990', '321 Elm Rd', '555-3456', 'emily.brown@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (5, 'Michael', 'Davis', '445566778', '654 Maple Dr', '555-7890', 'michael.davis@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (6, 'Jessica', 'Wilson', '889900112', '987 Cedar Ct', '555-2345', 'jessica.wilson@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (7, 'David', 'Garcia', '223344556', '210 Birch St', '555-6789', 'david.garcia@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (8, 'Ashley', 'Rodriguez', '778899001', '543 Walnut Ave', '555-0123', 'ashley.rodriguez@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (9, 'Christopher', 'Williams', '334455667', '876 Cherry Ln', '555-4567', 'christopher.williams@example.com');
INSERT INTO "User" ("ID", "firstname", "lastname", "nip", "address", "telephone", "email") VALUES (10, 'Amanda', 'Martinez', '556677889', '109 Oak Rd', '555-8901', 'amanda.martinez@example.com');

INSERT INTO "Client" ("userID") VALUES (1);
INSERT INTO "Client" ("userID") VALUES (2);
INSERT INTO "Client" ("userID") VALUES (3);
INSERT INTO "Client" ("userID") VALUES (4);
INSERT INTO "Client" ("userID") VALUES (5);

INSERT INTO "Manager" ("userID", "role") VALUES (6, 'HR Manager');
INSERT INTO "Manager" ("userID", "role") VALUES (7, 'Marketing Manager');
INSERT INTO "Manager" ("userID", "role") VALUES (8, 'Sales Manager');
INSERT INTO "Manager" ("userID", "role") VALUES (9, 'Operations Manager');
INSERT INTO "Manager" ("userID", "role") VALUES (10, 'Financial Manager');

INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (1, 1, 1000.00, DATE '2023-01-15');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (2, 2, 2500.50, DATE '2023-02-20');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (3, 3, 500.75, DATE '2023-03-10');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (4, 4, 12000.00, DATE '2023-04-01');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (5, 5, 750.25, DATE '2023-05-05');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (6, 1, 1000.00, DATE '2023-01-15');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (7, 2, 2500.50, DATE '2023-02-20');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (8, 3, 500.75, DATE '2023-03-10');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (9, 4, 12000.00, DATE '2023-04-01');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (10, 5, 750.25, DATE '2023-05-05');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (11, 1, 1000.00, DATE '2023-01-15');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (12, 2, 2500.50, DATE '2023-02-20');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (13, 3, 500.75, DATE '2023-03-10');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (14, 4, 12000.00, DATE '2023-04-01');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (15, 5, 750.25, DATE '2023-05-05');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (16, 1, 1000.00, DATE '2023-01-15');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (17, 2, 2500.50, DATE '2023-02-20');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (18, 3, 500.75, DATE '2023-03-10');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (19, 4, 12000.00, DATE '2023-04-01');
INSERT INTO "BankAccount" ("ID", "userID", "balance", "creation_date") VALUES (20, 5, 750.25, DATE '2023-05-05');


INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee") VALUES (1, 1.00);
INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee") VALUES (2, 1.50);
INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee") VALUES (3, 0.75);
INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee") VALUES (4, 2.00);
INSERT INTO "CheckingAccount" ("bank_accountID", "transaction_fee") VALUES (5, 1.25);

INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate") VALUES (6, 0.0275);
INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate") VALUES (7, 0.0100);
INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate") VALUES (8, 0.0325);
INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate") VALUES (9, 0.0200);
INSERT INTO "SavingAccount" ("bank_accountID", "interest_rate") VALUES (10, 0.0250);

INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate") VALUES (11, 'AUD', 1.3000);
INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate") VALUES (12, 'CAD', 1.2500);
INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate") VALUES (13, 'NZD', 1.4500);
INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate") VALUES (14, 'USD', 1.0000);
INSERT INTO "CurrencyAccount" ("bank_accountID", "currency", "exchange_rate") VALUES (15, 'EUR', 0.8500);

INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate") VALUES (16, 19.50);
INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate") VALUES (17, 14.00);
INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate") VALUES (18, 21.00);
INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate") VALUES (19, 15.50);
INSERT INTO "CreditAccount" ("bank_accountID", "interest_rate") VALUES (20, 18.00);

DECLARE
    i INTEGER := 1;
    j INTEGER := 1;
    transaction_type VARCHAR2(10);
BEGIN
    FOR i IN 1..20 LOOP
        FOR j IN 1..5 LOOP
            -- Vary the transaction type
            IF MOD(j, 2) = 0 THEN
                transaction_type := 'withdrawal';
            ELSE
                transaction_type := 'deposit';
            END IF;

            INSERT INTO "TransactionRecord" ("bank_accountID", "historicID", "type", "operation", "balance", "date") VALUES
            (i, (i-1)*5 + j, transaction_type, 100.00 * j,0, DATE '2023-01-01' + (i-1)*3 + j);
        END LOOP;
    END LOOP;
END;


INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (1, 1, 1, 'sent', 'Your account balance is low.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (2, 2, 2, 'read', 'A large withdrawal was made from your account.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (3, 3, 3, 'archived', 'Your deposit was successful.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (4, 4, 4, 'sent', 'Your credit card payment is due soon.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (5, 5, 5, 'read', 'A new transaction has been recorded on your account.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (6, 6, 1, 'archived', 'Your account has been credited with interest.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (7, 7, 2, 'sent', 'Your account is overdrawn.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (8, 8, 3, 'read', 'A suspicious transaction has been detected on your account.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (9, 9, 4, 'archived', 'Your account has been debited for fees.');
INSERT INTO "Notification" ("ID", "bank_accountID", "clientID", "status", "message") VALUES (10, 10, 5, 'sent', 'Your account is now active.');

INSERT INTO "Task" ("ID", "status", "action_type") VALUES (1, 'pending', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (2, 'in_progress', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (3, 'accepted', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (4, 'rejected', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (5, 'completed', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (6, 'pending', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (7, 'in_progress', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (8, 'accepted', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (9, 'rejected', 'deletion');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (10, 'completed', 'deletion');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (11, 'pending', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (12, 'in_progress', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (13, 'accepted', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (14, 'rejected', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (15, 'completed', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (16, 'pending', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (17, 'in_progress', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (18, 'accepted', 'creation');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (19, 'rejected', 'deletion');
INSERT INTO "Task" ("ID", "status", "action_type") VALUES (20, 'completed', 'deletion');

INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (1, 1, 'client');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (2, 2, 'manager');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (3, 3, 'client');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (4, 4, 'manager');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (5, 5, 'client');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (6, 6, 'manager');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (7, 7, 'client');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (8, 8, 'manager');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (9, 9, 'client');
INSERT INTO "UserTask" ("taskID", "userID", "user_type") VALUES (10, 10, 'manager');

INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (11, 1);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (12, 2);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (13, 3);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (14, 4);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (15, 5);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (16, 6);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (17, 7);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (18, 8);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (19, 9);
INSERT INTO "AccountTask" ("taskID", "bank_accountID") VALUES (20, 10);
