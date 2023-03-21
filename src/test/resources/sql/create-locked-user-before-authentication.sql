insert into roles values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

insert into employees values
(1, 'Test',  NOW(), 'Tester', 'Testerov');

insert into users values
(1,
true, -- account non expired
false, -- account non locked
true, -- credentials non expired
true, -- enabled
'test@gmail.com',
'$argon2id$v=19$m=23552,t=2,p=1$xnr/bs027Dlc/fVMbrapcRU$R5NvPkutHQfnx5fG6FTt3iaUMIu0i/5x8CMpiNekgGs',
'test');

insert into users_roles values
(1, 1);