-- Tao database
create database messageStoreT;
use messageStoreT;

-- tao bang user

CREATE TABLE users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100) NOT NULL
);

-- Them 2 user vao bang

insert into users(userName) values ('client'), ('server');

-- check 
select * from users;

-- tao bang data
CREATE table message(
	messageID int auto_increment primary key,
    messageContent varchar(1000) NOT NULL ,
    userID int not null,
    foreign key (userID) references users(userID)
);

-- Them thong tin

insert INTO message(messageContent,userID) VALUES ('serverMess',2), ('clientMess',1);

-- Lay 10 tin nhan cuoi cung

select ms.messageContent ,u.userName from message ms JOIN users u on ms.userID = u.userID; 

-- Lưu đống này lại để tăng hiệu suất

-- FROM

-- (
-- SELECT ROW_NUMBER() OVER (PARTITION BY EmployeeID ORDER BY OrderDate DESC) AS OrderedDate,*
-- FROM Orders
-- ) as ordlist
-- WHERE ordlist.EmployeeID = 5
-- AND ordlist.OrderedDate <= 5