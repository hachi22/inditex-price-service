INDITEX-PRICE-SERVICE

Java version: 21
SpringBoot version: 3.4.1

Using lombok for better readability

Using a java class for inserting the data instead of a .sql file because this way we can avoid external files, we have an easier maintenance of the code, and we could use our own logic for specific cases

Using "entity" annotation to crate the database table based on the Entity "Price" instead of doing it manually in the .sql file

Using builder design pattern for a clean and easier way of creating instance of the entity

Developed the logic using TDD,creating the unit tests first for the service (5 cases requested and one test for don't having prices for an x date, one for an invalid brand or product_id and the last one for the case that we have multiple prices for the same priority)

