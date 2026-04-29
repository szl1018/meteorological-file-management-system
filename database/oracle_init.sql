-- ============================================================================
-- 政府内部文件管理系统 - Oracle 数据库初始化脚本
-- ============================================================================
-- 说明：请在Oracle数据库中以sysdba身份执行此脚本
-- ============================================================================

-- 1. 创建表空间（可选）
-- CREATE TABLESPACE FILE_SYSTEM_DATA
-- DATAFILE 'file_system.dbf'
-- SIZE 100M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

-- 2. 创建用户并授权
CREATE USER file_system IDENTIFIED BY "file_system"
DEFAULT TABLESPACE USERS;
-- DEFAULT TABLESPACE FILE_SYSTEM_DATA;

GRANT CONNECT, RESOURCE TO file_system;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW TO file_system;
GRANT EXECUTE ON SYS.DBMS_LOB TO file_system;

-- 3. 切换到file_system用户
CONN file_system/file_system;

-- ============================================================================
-- 创建数据表
-- ============================================================================

-- 4.1 创建用户表
CREATE TABLE SYS_USERS (
    ID NUMBER(10) PRIMARY KEY,
    USERNAME VARCHAR2(50) NOT NULL UNIQUE,
    PASSWORD VARCHAR2(255) NOT NULL,
    NAME VARCHAR2(100) NOT NULL,
    ROLE VARCHAR2(20) NOT NULL CHECK (ROLE IN ('ADMIN', 'USER')),
    CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

COMMENT ON TABLE SYS_USERS IS '用户表';
COMMENT ON COLUMN SYS_USERS.ID IS '用户ID';
COMMENT ON COLUMN SYS_USERS.USERNAME IS '用户名';
COMMENT ON COLUMN SYS_USERS.PASSWORD IS '密码（BCrypt加密）';
COMMENT ON COLUMN SYS_USERS.NAME IS '真实姓名';
COMMENT ON COLUMN SYS_USERS.ROLE IS '角色：ADMIN-管理员，USER-普通用户';
COMMENT ON COLUMN SYS_USERS.CREATED_AT IS '创建时间';
COMMENT ON COLUMN SYS_USERS.UPDATED_AT IS '更新时间';

-- 4.2 创建文件表
CREATE TABLE SYS_FILES (
    ID NUMBER(10) PRIMARY KEY,
    FILE_NAME VARCHAR2(255) NOT NULL,
    FILE_SIZE NUMBER(20) NOT NULL,
    MIME_TYPE VARCHAR2(100),
    FILE_CONTENT BLOB NOT NULL,
    UPLOADED_BY NUMBER(10) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT FK_FILES_USER FOREIGN KEY (UPLOADED_BY) REFERENCES SYS_USERS(ID)
);

COMMENT ON TABLE SYS_FILES IS '文件表';
COMMENT ON COLUMN SYS_FILES.ID IS '文件ID';
COMMENT ON COLUMN SYS_FILES.FILE_NAME IS '原始文件名';
COMMENT ON COLUMN SYS_FILES.FILE_SIZE IS '文件大小（字节）';
COMMENT ON COLUMN SYS_FILES.MIME_TYPE IS '文件MIME类型';
COMMENT ON COLUMN SYS_FILES.FILE_CONTENT IS '文件内容（BLOB）';
COMMENT ON COLUMN SYS_FILES.UPLOADED_BY IS '上传用户ID';
COMMENT ON COLUMN SYS_FILES.CREATED_AT IS '上传时间';

-- 4.3 创建操作日志表
CREATE TABLE SYS_OPERATION_LOGS (
    ID NUMBER(10) PRIMARY KEY,
    USER_ID NUMBER(10) NOT NULL,
    USERNAME VARCHAR2(50) NOT NULL,
    ACTION VARCHAR2(50) NOT NULL,
    FILE_ID NUMBER(10),
    FILE_NAME VARCHAR2(255),
    IP_ADDRESS VARCHAR2(50),
    CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);

COMMENT ON TABLE SYS_OPERATION_LOGS IS '操作日志表';
COMMENT ON COLUMN SYS_OPERATION_LOGS.ID IS '日志ID';
COMMENT ON COLUMN SYS_OPERATION_LOGS.USER_ID IS '操作用户ID';
COMMENT ON COLUMN SYS_OPERATION_LOGS.USERNAME IS '操作用户名';
COMMENT ON COLUMN SYS_OPERATION_LOGS.ACTION IS '操作类型：upload/download/delete/password_change';
COMMENT ON COLUMN SYS_OPERATION_LOGS.FILE_ID IS '关联文件ID';
COMMENT ON COLUMN SYS_OPERATION_LOGS.FILE_NAME IS '文件名';
COMMENT ON COLUMN SYS_OPERATION_LOGS.IP_ADDRESS IS '操作IP地址';
COMMENT ON COLUMN SYS_OPERATION_LOGS.CREATED_AT IS '操作时间';

-- ============================================================================
-- 创建序列
-- ============================================================================

CREATE SEQUENCE SEQ_SYS_USERS START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE SEQ_SYS_FILES START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE SEQ_SYS_LOGS START WITH 1 INCREMENT BY 1 NOCACHE;

-- ============================================================================
-- 创建索引
-- ============================================================================

-- 用户表索引
CREATE INDEX IDX_USERS_USERNAME ON SYS_USERS(USERNAME);

-- 文件表索引
CREATE INDEX IDX_FILES_UPLOADER ON SYS_FILES(UPLOADED_BY);

-- 日志表索引
CREATE INDEX IDX_LOGS_USER ON SYS_OPERATION_LOGS(USER_ID);
CREATE INDEX IDX_LOGS_ACTION ON SYS_OPERATION_LOGS(ACTION);
CREATE INDEX IDX_LOGS_TIME ON SYS_OPERATION_LOGS(CREATED_AT);

-- ============================================================================
-- 初始化数据
-- ============================================================================

-- 插入默认管理员账号
-- 用户名: admin
-- 密码: admin123 (BCrypt加密后的值)
INSERT INTO SYS_USERS (ID, USERNAME, PASSWORD, NAME, ROLE, CREATED_AT, UPDATED_AT)
VALUES (
    SEQ_SYS_USERS.NEXTVAL,
    'admin',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
    '系统管理员',
    'ADMIN',
    SYSTIMESTAMP,
    SYSTIMESTAMP
);

-- 提交事务
COMMIT;

-- ============================================================================
-- 验证安装
-- ============================================================================

PROMPT '=============================================='
PROMPT '数据库初始化完成！'
PROMPT '默认管理员账号：'
PROMPT '  用户名: admin'
PROMPT '  密码: admin123'
PROMPT '=============================================='

-- 查询表数量
SELECT COUNT(*) AS table_count FROM USER_TABLES WHERE TABLE_NAME IN ('SYS_USERS', 'SYS_FILES', 'SYS_OPERATION_LOGS');

-- 查询用户
SELECT ID, USERNAME, NAME, ROLE FROM SYS_USERS;

EXIT;
