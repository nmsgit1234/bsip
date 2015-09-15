-- Create Roles

load data local infile 'Roles.csv' into table roles fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (role_id,name,description) ;

-- Creae Groups

load data local infile 'Groups.csv' into table groups  fields terminated by ','  LINES TERMINATED BY '\r\n' ignore 1 lines (group_id,name);

-- Associate roles with groups

load data local infile 'Group_Roles.csv' into table group_roles fields terminated by ','  LINES TERMINATED BY '\r\n' ignore 1 lines (group_id,role_id);

-- Create default administrator

insert into persons (person_id,name,address,oacNumber,isOACAuthenticated,phoneNumber,countryCode,email,password,personType,isActive) values (1,'Administrator','BSIAdmin, Suhana Arcade, Mysore',00000,'Y','9945415503','91','bsi.admiin@gmail.com','bsiadmin','GA','Y');

insert into user_groups values(3,1);

commit;


insert into regions (region_id,name,areacode,is_root,has_child) value (0,'root',0,'n','n');

-- Load Regions seed data

load data local infile 'Regions.csv' into table regions fields terminated by ','  LINES TERMINATED BY '\r\n' ignore 1 lines (region_id,name,areacode,is_root,has_child,prnt_region_id);

-- Load root services.
insert into services (NODE_ID,prntNodeId,name,description) values (1,0,'Services','Services');

-- Load default token value


commit;
