INSERT INTO member_db.mng (name, password, sort, enable, create_time)
VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 0, 1, UNIX_TIMESTAMP() * 1000);


INSERT INTO member_db.member (username, email, password, create_time)
VALUES
    ('user001', 'user001@example.com', 'pass001', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user002', 'user002@example.com', 'pass002', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user003', 'user003@example.com', 'pass003', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user004', 'user004@example.com', 'pass004', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user005', 'user005@example.com', 'pass005', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user006', 'user006@example.com', 'pass006', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user007', 'user007@example.com', 'pass007', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user008', 'user008@example.com', 'pass008', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user009', 'user009@example.com', 'pass009', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user010', 'user010@example.com', 'pass010', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user011', 'user011@example.com', 'pass011', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user012', 'user012@example.com', 'pass012', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user013', 'user013@example.com', 'pass013', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user014', 'user014@example.com', 'pass014', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user015', 'user015@example.com', 'pass015', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user016', 'user016@example.com', 'pass016', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user017', 'user017@example.com', 'pass017', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user018', 'user018@example.com', 'pass018', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user019', 'user019@example.com', 'pass019', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user020', 'user020@example.com', 'pass020', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user021', 'user021@example.com', 'pass021', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user022', 'user022@example.com', 'pass022', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user023', 'user023@example.com', 'pass023', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user024', 'user024@example.com', 'pass024', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000)),
    ('user025', 'user025@example.com', 'pass025', ROUND(UNIX_TIMESTAMP(NOW(3)) * 1000));