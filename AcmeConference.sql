# Borrar la BBDD
drop database if exists `Acme-Conference`;
# Crear la BBDD
create database `Acme-Conference`;
# Elimina al usuario acme-User
drop user 'acme-user';
# Crear al usuario acme-user
create user 'acme-user'@'%'
 identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
# Elimina al usuario acme-manager
drop user 'acme-manager';
# Crear al usuario acme-manager
create user 'acme-manager'@'%'
 identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';
# Dar CRUD al usuario acme-user
grant select, insert, update, delete
	on `Acme-Conference`.* to 'acme-user'@'%';
# Dar todos los permisos al usuario acme-manager
grant select, insert, update, delete, create, drop,
	references, index, alter, create temporary tables,
	lock tables, create view, create routine,
	alter routine, execute, trigger, show view
	on `Acme-Conference`.* to 'acme-manager'@'%';