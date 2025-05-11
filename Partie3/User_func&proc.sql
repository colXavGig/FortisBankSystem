----------------------------------------------
--              USER MANAGEMENT             --
----------------------------------------------

CREATE OR REPLACE VIEW "RemovedUserView" AS
SELECT u.*
FROM "User" u
INNER JOIN "UserTaskView" ut ON u."ID" = ut."userID"
WHERE ut."action_type" = 'deletion'
  AND ut."status" <> 'pending'
  AND ut."status" <> 'in_progress'
  AND ut."status" <> 'rejected';


-- View for all active users
CREATE OR REPLACE VIEW "ActiveUserView" AS
SELECT u.*
FROM "User" u
INNER JOIN "UserTaskView" ut ON u."ID" = ut."userID"
WHERE u."ID" NOT IN (SELECT "ID" FROM "RemovedUserView")
  AND ut."action_type" = 'creation'
  AND ut."status" <> 'pending'
  AND ut."status" <> 'in_progress'
  AND ut."status" <> 'rejected';

-- Function to read user by email
CREATE OR REPLACE FUNCTION ReadUserByEmail (
    p_email VARCHAR2
) RETURN SYS_REFCURSOR AS
    user_cursor SYS_REFCURSOR;
BEGIN
    OPEN user_cursor FOR
    SELECT * FROM "ActiveUserView"
    WHERE "email" = p_email;

    RETURN user_cursor;
END ReadUserByEmail;
