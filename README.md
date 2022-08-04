# ABCCompany
Java fx Application with mysql database

<h3>This is an application which allow users to manage day to day sales management. User can manage their customer details, items, sales and save their data in MYSQL database.</h>

<H1>Simple POS System</H>
<h2>Prerequisites</h2>

<h3>
Jfoenix 8.0 or higher<br/>
MYSQL Connector 8 or higher<br/>
Java JDK at least java8.<br/>
</h3>

<h1>Database</h1>
<p>
create database abc_company;
use abc_company;

create table customer(
cId varchar(10),
cName varchar(60) not null,
address varchar(200) not null,
contact varchar(20),
email varchar(50),
constraint primary key(cId)
);


insert into customer values ("C001","Evumi Hasara","Moratuwa","078-1234367","evumi@gmail.com"),("C002","Shehan Dilusanka","Gampaha","071-1234367","shehan@gmail.com"),
("C003","Kasun Kanishka","Udugampola","072-1234367","kasun@gmail.com"),("C004","Ravindu Pamaljith","Naiwala","075-1234367","ravindu@gmail.com");

create table employee(
eId varchar(10),
eName varchar(60) not null,
contact varchar(20)not null,
password varchar(50)not null,
constraint primary key(eId)
);
 insert into employee values("E001","Shehan","071-1234367","1234"),("E002","Kasun","076-1234367","1234");

create table item(
itemCode varchar(10),
description varchar(200) not null,
qtyOnHand int not null,
unitPrice decimal(50,2) not null,
constraint primary key(itemCode)
);
insert into item values("I001","Panda Baby Soap",40,100.00),
("I002","Milk Powder",20,1300.00),("I003","Marie Biscuits",10,400.00);

Create table orders(
orderId varchar(50),
orderDate DATE,
cId varchar(50) not null,
constraint primary key(orderId),
constraint foreign key(cId) references Customer (cId) 
on delete cascade on update cascade
)engine=InnoDB;


create table orderDetail(
itemCode varchar(50),
orderId varchar(50),
qty int not null,
constraint primary key(itemCode,orderId),
constraint foreign key(itemCode) references Item (itemCode) 
on delete cascade on update cascade,
constraint foreign key(orderId) references Orders (orderId) 
on delete cascade on update cascade
)engine=InnoDB;
</p>
