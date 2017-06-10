# Launch integration test support
> This is a forked project

### Create database @ localhost
```sql
CREATE DATABASE IF NOT EXISTS test;

USE test;

CREATE TABLE user
(
    ID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(50) NOT NULL,
    TYPE ENUM('USER', 'ADMIN') NOT NULL
);
```

If user/password is not root/password on your mysql @ localhost, then change them in [DatabaseManager]

You can now run tests into IDE or with mvn test command


[DatabaseManager]: /src/main/java/com/example/integration/DatabaseManager.java#L11-L13