INSERT INTO EMPLOYEE (VISA, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION)
VALUES
    ('ANV', 'Nguyen', 'Van An', '1999-01-01', 1),
    ('BTT', 'Tran', 'Thi Binh', '1999-01-01', 1),
    ('DDV', 'Duong', 'Van Dung', '1999-01-01', 1),
    ('ENV', 'Nguyen', 'Van Em', '1999-01-01', 1),
    ('GNT', 'Nguyen', 'Thi Giang', '1999-01-01', 1)
    ;

INSERT INTO GROUPZ(GROUP_NAME, GROUP_LEADER_ID, VERSION)
VALUES
    ('GROUP 1', 1, 1),
    ('GROUP 2', 2, 1),
    ('GROUP 3', 3, 1),
    ('GROUP 4', 4, 1)
;

INSERT INTO PROJECT (PROJECT_NUMBER, PROJECT_NAME, CUSTOMER, STATUS, GROUP_ID, START_DATE, LAST_MODIFIED_DATE, VERSION)
VALUES
    (1, 'EFV', 'Customer A', 'NEW', 1, '2020-04-20', '2020-04-20', 1),
    (2, 'CXTRANET', 'Customer B', 'PLA', 1, '2020-04-28', '2020-04-28', 1),
    (3, 'CRYSTAL BALL', 'Customer C', 'INP', 1, '2020-04-28', '2020-04-28', 1),
    (4, 'IOC CLIENT EXTRANET', 'Customer D', 'FIN', 2, '2020-06-07', '2020-06-07', 1),
    (5, 'TRADEECO', 'Customer E', 'NEW', 2, '2020-06-08', '2020-06-08', 1),
    (6, 'PROJECT 001', 'Customer F', 'NEW', 3, '2021-03-20', '2021-03-20', 1),
    (7, 'PROJECT 002', 'Customer G', 'INP', 3, '2021-03-21', '2021-03-21', 1),
    (8, 'PROJECT 003', 'Customer H', 'NEW', 3, '2021-03-22', '2021-03-22', 1),
    (9, 'PROJECT 004', 'Customer I', 'FIN', 3, '2021-03-23', '2021-03-23', 1),
    (10, 'PROJECT 005', 'Customer J', 'NEW', 3, '2021-03-24', '2021-03-24', 1),
    (11, 'PROJECT 006', 'Customer K', 'PLA', 3, '2021-03-25', '2021-03-25', 1),
    (12, 'PROJECT 007', 'Customer L', 'NEW', 3, '2021-03-25', '2021-03-25', 1),
    (13, 'PROJECT 008', 'Customer M', 'INP', 3, '2021-03-25', '2021-03-25', 1),
    (14, 'PROJECT 009', 'Customer N', 'FIN', 3, '2021-03-25', '2021-03-25', 1),
    (15, 'PROJECT 010', 'Customer O', 'FIN', 3, '2021-03-25', '2021-03-25', 1),
    (16, 'PROJECT 011', 'Customer P', 'NEW', 3, '2021-03-25', '2021-03-25', 1),
    (17, 'PROJECT 012', 'Customer Q', 'NEW', 3, '2021-03-25', '2021-03-25', 1),
    (18, 'PROJECT 013', 'Customer R', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (19, 'PROJECT 014', 'Customer S', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (20, 'PROJECT 015', 'Customer T', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (21, 'PROJECT 016', 'Customer U', 'INP', 3, '2021-04-22', '2021-04-22', 1),
    (22, 'PROJECT 017', 'Customer V', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (23, 'The quick brown fox jumps over the lazy dog', 'Customer NHK', 'PLA', 3, '2021-04-22', '2021-04-22', 1),
    (24, 'PROJECT 018', 'Customer W', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (25, 'PROJECT 019', 'Customer X', 'FIN', 3, '2021-04-22', '2021-04-22', 1),
    (26, 'PROJECT 020', 'Customer Y', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (27, 'PROJECT 021', 'Customer Z', 'FIN', 3, '2021-04-22', '2021-04-22', 1),
    (28, 'PROJECT 022', 'Customer 0', 'PLA', 3, '2021-04-22', '2021-04-22', 1),
    (29, 'PROJECT 023', 'Customer 1', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (30, 'PROJECT 024', 'Customer 2', 'FIN', 3, '2021-04-22', '2021-04-22', 1),
    (31, 'PROJECT 025', 'Customer 3', 'NEW', 3, '2021-04-22', '2021-04-22', 1),
    (32, 'PROJECT 026', 'Customer 4', 'PLA', 4, '2021-04-22', '2021-04-22', 1),
    (33, 'PROJECT 027', 'Customer 5', 'PLA', 4, '2021-04-22', '2021-04-22', 1),
    (34, 'PROJECT 028', 'Customer 6', 'FIN', 4, '2021-04-22', '2021-04-22', 1),
    (35, 'PROJECT 029', 'Customer 7', 'NEW', 4, '2021-04-22', '2021-04-22', 1),
    (36, 'PROJECT 030', 'Customer 8', 'NEW', 4, '2021-04-22', '2021-04-22', 1),
    (37, 'PROJECT 031', 'Customer 9', 'NEW', 4, '2021-04-22', '2021-04-22', 1)
    ;

INSERT INTO PROJECT_EMPLOYEE(PROJECT_ID, EMPLOYEE_ID)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 4)
    ;
-- INSERT INTO TASK(NAME, DEADLINE, PROJECT_ID, USER_ID)
-- VALUES
--     ('EFV_TASK_1', '2020-03-05', 1, 1),
--     ('EFV_TASK_2', '2020-03-10', 1, null),
--     ('EFV_TASK_3', '2020-03-15', 1, 2),
--     ;
