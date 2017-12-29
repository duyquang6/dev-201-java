# Requirements
You will be asked to build a simple command-line application that will manipulate data in plain-text files. You are a service provider to provide the service to validate data from clients and send statistic reports to state governments.

Application will process data with below requirements:

### Extraction

TAB file header: id, first_name, last_name, date_of_birth, company_name, address, city, county, state, zip, phone1, phone2, email, web
 
Contact properties: id, firstName, lastName, dateOfBirth, address, city, state, zipCode, mobilePhone, email
 
Input file example:
 
```
id	first_name	last_name	company_name	date_of_birth	address	city	county	state	zip	phone1	phone2	email	website
1	James	Butt	Benton, John B Jr	01/06/1929	6649 N Blue Gum St	New Orleans	Orleans	LA	70116	504-621-8927	504-845-1427	jbutt@gmail.com	http://www.bentonjohnbjr.com
2	Josephine	Darakjy	Chanay, Jeffrey A Esq	02/01/1945	4 B Blue Ridge Blvd	Brighton	Livingston	MI	48116	810-292-9388	810-374-9840	josephine_darakjy@darakjy.org	http://www.chanayjeffreyaesq.com
3	Art	Venere	Chemel, James L Cpa	09/02/1960	8 W Cerritos Ave #54	Bridgeport	Gloucester	NJ	8014	856-636-8749	856-264-4130	art@venere	http://www.chemeljameslcpa.com
4	Lenna	Paprocki	Feltz Printing Service	08/01/1915	639 Main St	Anchorage	Anchorage	AK	99501	907-385-4412	907-921-2010	lpaprocki@hotmail.com	http://www.feltzprintingservice.com
5	Donette	Foller	Printing Dimensions	05/14/1950	34 Center St	Hamilton	Butler	OH	45011	513-570-1893	513-549-4561	donette.foller@cox.net	http://www.printingdimensions.com
```
Note: only handle data lines that have enough defined fields (associated with TAB file headers)

### Validation

Validation rules: 
- Not Empty
- Max Length
- RegExp Pattern Matching
- Valid Date Format: default format 'MM/dd/yyyy'
- Valid Reference: using for state
 
### Persistence

#### Persisting 'invalid' Contacts
TAB file header: contact_id, error_field, error_message
 
Output file example:
```
contact_id	error_field	error_message
3	email	'art@venere' is invalid email format
6	mobile_phone	'419503-2484' is invalid format XXX-XXX-XXXX
10	address	'228 Runamuck Pl #2808''s length is over 20
12	state	'NYX' is incorrect state code
19	mobile_phone	'313-288-17937' is invalid format XXX-XXX-XXXX
20	first_name	is empty
27	day_of_birth	'03/14/193' is invalid format MM/dd/yyyy
28	day_of_birth	'08/10/45' is invalid format MM/dd/yyyy
```

#### Persisting ‘valid’ Contacts
 
TAB file header: id, first_name, last_name, date_of_birth, address, city, state, zip_code, mobile_phone, email
 
Output file example:
```
id	first_name	last_name	day_of_birth	address	city	state	zip_code	mobile_phone	email
5	Donette	Foller	05/14/1950	34 Center St	Hamilton	OH	45011	513-570-1893	donette.foller@cox.net
2	Josephine	Darakjy	02/01/1945	4 B Blue Ridge Blvd	Brighton	MI	48116	810-292-9388	josephine_darakjy@darakjy.org
1	James	Butt	01/06/1929	6649 N Blue Gum St	New Orleans	LA	70116	504-621-8927	jbutt@gmail.com
4	Lenna	Paprocki	08/01/1915	639 Main St	Anchorage	AK	99501	907-385-4412	lpaprocki@hotmail.com		
```

### Reports

#### Reporting Invalid Contacts per Column Names
TAB file header: column_name, number_of_invalid_contact
 
Output file example:
```
first_name	6
last_name	25
zip_code	13
day_of_birth	8
address	62
mobile_phone	23
city	9
state	22
email	10
```
Note: Order by `column_name`

#### Reporting Contacts per States
TAB file header: state_code, number_of_contact
 
Output file example:
 
```
state_code	number_of_contact
HI	3
TX	19
FL	20
NV	2
WA	3
NY	35
```
Note: Order by `state_code`

#### Reporting Contacts per Age Groups (year 2016)
TAB file header: group, number_of_contact, percent_of_contact
 
Output file example:
```
group	number_of_contact	percentage_of_contact
Middle age	50	15%
Senior	147	44%
Adolescent	23	6%
Adult	82	24%
Children	29	8%
```
Note: There is a bug that the total percentage of contact is not 100%. Please fix it.

Note: Order by `age` (Children, Adolescent, Adult, Middle Age, Senior)

Note: Age Groups
```
group	age
Children	<=9
Adolescent	10-19
Adult	20-45
Middle Age	46-60
Senior	>60
```

# Source Code
### Pre-requisites
- JDK 1.8 or later
- Maven

### Build
```
mvn clean package
```

### Run
```
cd target
java -jar <jar-file>.jar
```
