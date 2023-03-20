### Description

Implement Employee data system. To control employee on some enterprise.<br>

#### Functional

Here you can:
* *add* new employee;
* *edit* existing employee;
* *delete* employee;
* *get* info about employee.

**Authentication** - JWT token authentication. To access JWT token properties use 
[jjwt library](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api).

**Authorization** - Role-based.
We implement authorization in order to restrict access to several functionality.

* **Add employee** - only User with Role `ADMIN` can add employees.<br>
* **Delete employee** - only User with Role `ADMIN` can delete employees.<br>
* **Edit employee** - the same case as **Delete** only User with Role `ADMIN` can edit employees.<br>
* **Get employee** - any user hav access to accomplish that.<br>

All API routes requires authentication except `/api/auth/signIn` - to authenticate User with certain credentials.

#### Technologies

* Java - 17
* Maven
* Spring boot 3
* PostgreSQL