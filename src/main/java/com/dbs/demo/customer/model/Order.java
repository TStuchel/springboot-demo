package com.dbs.demo.customer.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * DEVELOPER NOTE: See the Customer class for more information on JPA first. Then come back here.
 */
@Data
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    // -------------------------------------------------- PROPERTIES ---------------------------------------------------

    @Id
    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    /**
     * DEVELOPER NOTE: JPA relationships are bi-directional. A Customer knows its Orders, but an Order also knows to
     * which Customer it belongs. So, there can be many Orders that belong to One customer (per the @ManyToOne
     * annotation). Notice that that the @JoinColumn is designating a database foreign key back to the Customer table.
     * <p>
     * Take a moment to think about this... a "parent" Java class has to know it's "children" (via a collection
     * property), but a "child" class doesn't have to know its "parent". But in a relational database, a "parent" table
     * doesn't know its "children", but the rows of a "child" table have to know what "parent" the row belongs to.
     * <p>
     * Basically, Java and relational databases associate entities in reverse. JPA navigates this by having
     * bi-directional references in Java.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUST_ID")
    private Customer customer;

}