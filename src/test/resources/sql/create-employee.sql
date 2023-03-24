insert into roles values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

insert into employees values
(1, 'Test1',  '2020-10-10', 'Tester1', 'Testerov1'),
(2, 'Test2',  NOW(), 'Tester2', 'Testerov2'),
(3, 'Test3',  NOW(), 'Tester3', 'Testerov3'),
(4, 'Test4',  NOW(), 'Tester4', 'Testerov4');

insert into users values
(1,
true, -- account non expired
true, -- account non locked
true, -- credentials non expired
true, -- enabled
'test@gmail.com',
'$argon2id$v=19$m=23552,t=2,p=1$xnr/bs027Dlc/fVMbrapcRU$R5NvPkutHQfnx5fG6FTt3iaUMIu0i/5x8CMpiNekgGs',
'test'),
(2,
true, -- account non expired
true, -- account non locked
true, -- credentials non expired
true, -- enabled
'user1@gmail.com',
'$argon2id$v=19$m=23552,t=2,p=1$xnr/bs027Dlc/fVMbrapcRU$R5NvPkutHQfnx5fG6FTt3iaUMIu0i/5x8CMpiNekgGs',
'user1'),
(3,
true, -- account non expired
true, -- account non locked
true, -- credentials non expired
true, -- enabled
'user2@gmail.com',
'$argon2id$v=19$m=23552,t=2,p=1$xnr/bs027Dlc/fVMbrapcRU$R5NvPkutHQfnx5fG6FTt3iaUMIu0i/5x8CMpiNekgGs',
'user2'),
(4,
true, -- account non expired
true, -- account non locked
true, -- credentials non expired
true, -- enabled
'user3@gmail.com',
'$argon2id$v=19$m=23552,t=2,p=1$xnr/bs027Dlc/fVMbrapcRU$R5NvPkutHQfnx5fG6FTt3iaUMIu0i/5x8CMpiNekgGs',
'user3');

insert into users_roles values
(1, 1),
(2, 2),
(3, 2),
(4, 2);