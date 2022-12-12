INSERT INTO USERS
VALUES      ('VVertigoM56','342660651071','234 Delta street','N', 'Yuvraj','Sekhon'),
            ('Iruyato','247951367951','239 MINDYABUSNIESS street','Y', 'Jadelin','Liske'),
            ('habsburgers','722659470142','69 HELLO street','N', 'Indiana','Jones');

INSERT INTO ORDERS
VALUES      ('100000000',29.00,'826490481','833566783698','731 Fondren','VVertigoM56'),
            ('100000001',28.00,'062848079','466599185324','143 Finwood Road','habsburgers'),
            ('100000002',32.50,'425620403','715314337630','4902 Spruce Drive','Iruyato'),
            ('100000003',28.00,'285479136','068292118558','1865 Saint James Drive','Iruyato');
-- all the numbers are from random number generator

INSERT INTO PUBLISHER
VALUES      ('CelestNg213@gmail.com','Sky studios','70888001','4595 Horseshoe Lane'),
            ('TaylorReid113@gmail.com','ApplesReid','32763344','4585 Cross Street'),
            ('MichelleObama523@gmail.com','HelloHI','19111446','2296 Sarah Drive'),
            ('JavierZamor872a@gmail.com','Zamora Publishing','82756220','4831 Terra Street');

INSERT INTO PUBLISHERPHONE
VALUES      ('CelestNg213@gmail.com','625-364-2155'),
            ('TaylorReid113@gmail.com','544-422-8885'),
            ('MichelleObama523@gmail.com','900-932-9387'),
            ('JavierZamor872a@gmail.com','795-958-1823');

INSERT INTO BOOK
VALUES      ('772012249', 29.00, 15.00, 'Our Missing Hearts' , 326 , .85 , 10,120 , 10.50, 'CelestNg213@gmail.com', 50),
            ('759239148',28.00, 3.00, 'Carrie Soto Is Back', 384 , .82, 20,120, 15.00, 'TaylorReid113@gmail.com', 70),
            ('069819139',32.50, 18.00, 'The Light We Carry', 336, .82, 30,135, 18.70, 'MichelleObama523@gmail.com', 1400),
            ('970164937',28.00, 13.00, 'Solito', 400, .92, 40,300, 20.00, 'JavierZamor872a@gmail.com', 800),
            ('976164937',20.00, 14.00, 'Solito', 20, .23, 20,300, 60.00, 'MichelleObama523@gmail.com', 650);

INSERT INTO ORDERED
VALUES      ('772012249','100000000', 4),
            ('759239148','100000001', 5),
            ('069819139','100000002', 2),
            ('970164937','100000003', 8);

INSERT INTO AUTHOR
VALUES      ('Celest',400,'Ng','911040632'),
            ('Taylor',600,'Reid','010001086'),
            ('Michelle',960,'Obama','643271166'),
            ('Javier',350,'Zamora','096389805');

INSERT INTO GENRE
VALUES      ('Fiction','7622'),
            ('non-fiction','7814'),
            ('Mystery','6634'),
            ('Children','8416'),
            ('Romance','8736'),
            ('Fantasy','8400'),
            ('short story','1002'),
            ('Adventure','7216'),
            ('Novel','5590'),
            ('Speculative','6094'),
            ('Magical','2203'),
            ('Realism','6880'),
            ('Horror','0625'),
            ('Book Review','7055'),
            ('Paranormal','9350'),
            ('GuideBook','3783'),
            ('Crime','7199'),
            ('Poetry','0791'),
            ('Humor','5655'),
            ('Drama','8755');
            

INSERT INTO WRITES
VALUES      ('772012249','911040632'),
            ('759239148','010001086'),
            ('069819139','643271166'),
            ('970164937','096389805'),
            ('976164937','643271166');

INSERT INTO ISGENRE
VALUES      ('772012249','Drama'),
            ('759239148','Fiction'),
            ('069819139','GuideBook'),
            ('970164937','non-fiction'),
            ('976164937','Fiction');
