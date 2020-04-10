package com.dbs.demo.customer.model;

import com.dbs.demo.customer.api.contract.CustomerDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Business entity representing a Customer. This business entity is able to be persisted to a database using JPA.
 * <p>
 * DEVELOPER NOTE: This class has the @Entity annotation to tell JPA that this is a class that it must care about since
 * instances of this class represent a rows in a table (and thus business entities). This is called an ORM
 * (Object-Relational-Mapper). JPA is an API that wraps an underlying ORM... in this case, Hibernate. The @Table
 * annotation is so that JPA knows the actual name of the database table that holds these entities. The @Column
 * annotation tells JPA the actual name of the database column in that table.
 * <p>
 * The @Data annotation has nothing to do with databases or JPA. This annotation is a Lombok library annotation that
 * automatically generates property getter and setter methods so that you don't have to. It even makes an equals()
 * method that can compare the properties between two objects to determine if they are "logically" equal.
 *
 * @see CustomerDTO
 */
@Data
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    // -------------------------------------------------- PROPERTIES ---------------------------------------------------

    /**
     * DEVELOPER NOTE: Every entity in JPA (and the associated database table) must have a primary key. This field is
     * annotated with @Id to indicate that it is the table's (and thus this object's) unique identifier/primary key.
     */
    @Id
    @Column(name = "CUST_ID")
    private Integer customerId;

    @Column(name = "CUST_STREET_ADDR")
    private String streetAddress;

    @Column(name = "CUST_FULL_NAME")
    private String fullName;

    /**
     * DEVELOPER NOTE: A Customer has a list of Orders. These Orders are also annotated to be JPA entities. The JPA
     * annotation @OneToMany indicates that one Customer has many Orders. The collection of Orders that this Customer
     * "owns" must always be a Set (an unordered collection of unique objects). Notice the "lazy" fetch type. This means
     * JPA won't actually get the Orders from the database unless you try to access this collection in some other part
     * of the code. The "cascade" stuff means that if this Customer is deleted, then all of the Orders will be deleted,
     * too.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>(0);

    /**
     * DEVELOPER NOTE: The @Transient annotation tells JPA to NOT store (or read) values from this field into the
     * database. It's good for when you have "temporary" properties that only need to hang around in the Java code
     * during the lifetime of the object, but never need to be persisted in the database.
     */
    @Transient
    private ZonedDateTime lastReadTimestamp;

}


