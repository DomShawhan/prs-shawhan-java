-- create a user for prsjava
-- create a user and grant privileges to that user
-- this is the user we'll use when accessing the db from our app
USE prs;

DROP USER IF EXISTS prs_user@localhost;
CREATE USER prs_user@localhost IDENTIFIED BY 'sesame';
GRANT SELECT, INSERT, DELETE, UPDATE ON prs.* TO prs_user@localhost;