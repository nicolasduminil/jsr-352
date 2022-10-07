DROP TABLE IF EXISTS caller;
DROP TABLE IF EXISTS caller_groups;
CREATE TABLE IF NOT EXISTS caller(name VARCHAR(64) PRIMARY KEY, password VARCHAR(255));
CREATE TABLE IF NOT EXISTS caller_groups(caller_name VARCHAR(64), group_name VARCHAR(64));
INSERT INTO caller VALUES('admin', HASH ('SHA256','passadmin'));
INSERT INTO caller_groups VALUES('admin', 'admin-role');
INSERT INTO caller_groups VALUES('admin', 'user-role');
INSERT INTO caller VALUES('user', HASH ('SHA256','passuser'));
INSERT INTO caller_groups VALUES('user', 'user-role');
