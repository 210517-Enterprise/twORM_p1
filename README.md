# twORM (Group Two ORM)

## Overview
TwORM is a Java based ORM tool intended to provide a simplified way of connecting to and using a PostgreSQL relational database without the need for SQL or connection management. 

## Technologies Used
* PostgreSQL 42.2.22
* Java 8.0 (1.8)
* Apache Commons 2.5.0
* JUnit Jupiter 5.7.2
* Log4j 1.2.17

## Features
* Simplified User Interface API
* Automatic Table Generation as needed
* Simple Annotation Based markup for ease of use
* Basic Transaction Control Through User API

## Coming Soon
* Foreign Key Annotation And Referential Integrity Mapping
* Mapping of Join Columns Inside Entities and Building Join Tables
* Aggregate Functions
* Complex and Conditional Querying

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/twORM_p1.git
  cd twORM_p1/twORM
  mvn install
```

Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>twORM</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
```

Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  url=path/to/database
  admin-usr=username/of/database
  admin-pw=password/of/database  
  ```
## Usage
### Annotating Classes
All classes that are represented in the database must be annotated.
- #### @Entity(name = "table_name")
   - Indicates that the class is associated with the table named table_name.
- #### @Column(name = "column_name")  
   - Optional. Indicates that the Annotated field is a column in the table with the name column_name for future use with complex queries and static fields.
- #### @Setter(name = "column_name")  
   - Indicates that the anotated method is a setter for 'column_name'.  
- #### @Getter(name = "column_name")  
   - Indicates that the anotated method is a getter for 'column_name'.  
- #### @PrimaryKey(name = "column_name" isSerial=true/false) 
   - Indicates that the annotated field is the primary key for a table. The isSerial value controls whether the value is serially generated or specified by the user.

### UserAPI
- #### `public static TwORM getInstance()`
  - Returns a Singleton instance of the class that will serve as the starting point for calling all other available methods.
- #### `public HashMap<Class<?>, HashSet<Object>> getCache()`  
  - returns the cache as a HashMap.  
- #### `public boolean addClass(final Class<?> clazz)`  
  - Adds a class to the ORM. This is the method to use to declare a Class is an object inside of the database.  
- #### `public boolean UpdateObjectInDB(final Object obj)`  
  - Updates the given object in the databse.
- #### `public boolean deleteObjectFromDB(final Object obj)`  
  - Removes the given object from the database.  
- #### `public boolean addObjectToDB(final Object obj)`  
  - Adds the given object to the database. If an appropriate table does not exist, one will be generated.
- #### `public Optional<List<Class>> getListObjectFromDB(final Class<?> clazz)`
  - Returns a list of all entries in the table that corresponds to clazz
- #### `public Optional<Object> getByPK(Class<?> clazz, String pk)`
  - Returns the entry from the table that corresponds to clazz with the primary key PK
- #### `public Optional<Object> getByPK(Class<?> clazz, int pk)`
  - Does the same as the prior method for an integer key (particularly useful for serial keys)
- #### `public Optional<List<Object>> getListByColumn(Class<?> clazz, String column, Object value)`
  - Returns a list of objects selected by a value in a column. Column is the name of column and value is what will be selected against.
- #### `public Optional<List<Object>> getListByColumns(Class<?> clazz, HashMap<String, Object> columns)`
  - Returns a list of objects selected by values in multiple columns. Each entry in the hashmap is a column name and value.
- #### `public void enableAutoCommit()`
  - Enables auto commit, which is disabled by default. Use of this method is strongly discouraged.
- #### `public void disableAutoCommit()`
  - Disables auto commit. This is default setting for TwORM and only needs to be used if the prior method is invoked.
- #### `public void beginTransaction()`
  - Begins a transaction.
- #### `public void setSavePoint(String savePointName)`
  - Creates a save point in the transaction. savePointName is the name of the save point that can be referenced in other methods
- #### `public void returnToSavePoint(String savePointName)`
  - Rollsback to the save point indicated by savePointName.
- #### `public void removeSavePoint(String savePointName)`
  - Removes the save point specified by savePointName.
- #### `public void abortChanges()`
  - Rollbacks transactions.
- #### `public void commitChanges()`
  - Commits changes.
- #### `public void addAllFromDBToCache()`
  - Constructs a cache that contains all entities and entries present in the database.

## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
