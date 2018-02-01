USE WorkTime;

# test employee
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Jack','Rabbit','HOURLY_CONTRACT','OFFICE');
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Donald','Trump','HOURLY_CONTRACT','PRODUCTION');
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Joe','G.I.','HOURLY_CONTRACT','OFFICE');
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Mauno','Ahonen','MONTHLY_CONTRACT','OFFICE');
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Laika','Sputnik','MONTHLY_CONTRACT','PRODUCTION');
INSERT INTO Employee (firstName,lastName,contractType,department) VALUES('Gros','Jean','MONTHLY_CONTRACT','PRODUCTION');

# test log entries
INSERT INTO LogEntry (employeeId, logIn, breakIn, breakOut, logOut) VALUES(1, NOW(), ADDTIME(NOW(), ' 3:1:15'), ADDTIME(NOW(), ' 3:59:59'), ADDTIME(NOW(), ' 11:15:2'));
INSERT INTO LogEntry (employeeId, logIn, breakIn, breakOut, logOut) VALUES(2, NOW(), ADDTIME(NOW(), ' 3:0:0'), ADDTIME(NOW(), ' 4:0:0'), ADDTIME(NOW(), ' 8:0:0'));
INSERT INTO LogEntry (employeeId, logIn, logOut) VALUES(3, NOW(), ADDTIME(NOW(), ' 3:0:0'));
INSERT INTO LogEntry (employeeId, logIn, logOut) VALUES(4, NOW(), ADDTIME(NOW(), ' 4:0:0'));
INSERT INTO LogEntry (employeeId, logIn, logOut) VALUES(5, NOW(), ADDTIME(NOW(), ' 8:0:0'));
INSERT INTO LogEntry (employeeId, logIn, breakIn, breakOut, logOut) VALUES(6, NOW(), ADDTIME(NOW(), ' 3:10:15'), ADDTIME(NOW(), ' 3:49:00'), ADDTIME(NOW(), ' 12:25:20'));

# test notifications
INSERT INTO Notification (department,validFrom,validTo,message) VALUES('PRODUCTION',CURRENT_DATE(),DATE_ADD(CURRENT_DATE(), INTERVAL 31 DAY),'Maintenance break scheduled at 14:00');
INSERT INTO Notification (department,validFrom,validTo,message) VALUES('OFFICE',CURRENT_DATE(),DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY),'Big boss coming, clean up!');
