-- Create Tokens

load data local infile 'Tokens.csv' into table tokens fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (token_id,token_name,token_desc) ;

-- Create Token values

load data local infile 'TokenValues.csv' into table token_values fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (id,token_value) ;


-- Creae Service properies

load data local infile 'ServiceProperties.csv' into table property fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (PROPERTY_ID,name,description,IS_MANDATORY,DISPLAY_TYPE,VALUE_TYPE,TOKEN_ID,DISPLAY_SIZE,DATA_SIZE) ;

-- Creae Service 

load data local infile 'Services.csv' into table services fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (NODE_ID,prntNodeId,name,description) ;

-- Creae Service property reference

load data local infile 'ServicePropertiesReference.csv' into table service_properties fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (NODE_ID,PROPERTY_ID) ;

-- Create buyer and supplier
load data local infile 'Persons.csv' into table persons fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (PERSON_ID,name,address,oacNumber,isOACAuthenticated,phoneNumber,countryCode,email,password,personType,isActive,website,description);

-- Subscribe buyer
load data local infile 'BuyerServiceRegion.csv' into table buyer_service_region fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (BSR_ID,buyerId,serviceId,REGION_ID);

-- Subscribe supplier
load data local infile 'SupplierServiceRegion.csv' into table supplier_service_region fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (SSR_ID,supplierId,serviceId,REGION_ID);

load data local infile 'SupplierSubscribedServices.csv' into table supplier_subscribed_services fields terminated by ',' LINES TERMINATED BY '\r\n' ignore 1 lines (PERSON_ID,NODE_ID);

commit;
