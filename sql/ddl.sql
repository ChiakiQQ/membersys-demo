CREATE DATABASE member_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE member_db;

CREATE TABLE IF NOT EXISTS member_db.member (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '會員 ID',
                        username VARCHAR(50) NOT NULL COMMENT '使用者名稱',
                        email VARCHAR(100) NOT NULL COMMENT '電子郵件',
                        password VARCHAR(100) NOT NULL COMMENT '密碼',
                        create_time BIGINT NOT NULL COMMENT '創建時間（timestamp）',
                        c_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '資料建立時間',
                        u_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '資料更新時間'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員表';

CREATE TABLE IF NOT EXISTS member_db.mng (
                                   id INT AUTO_INCREMENT PRIMARY KEY COMMENT '系統管理員 ID',
                                   name VARCHAR(50) NOT NULL COMMENT '登入帳號',
                                   password VARCHAR(100) NOT NULL COMMENT '密碼',
                                   sort INT DEFAULT 0 COMMENT '排序',
                                   enable TINYINT(1) DEFAULT 1 COMMENT '是否啟用 1=是 0=否',
                                   create_time BIGINT NOT NULL COMMENT '建立時間 timestamp',
                                   c_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                   u_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);