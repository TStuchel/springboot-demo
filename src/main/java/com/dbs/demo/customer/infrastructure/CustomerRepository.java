package com.dbs.demo.customer.infrastructure;

import com.dbs.demo.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This JPA (Java Persistence Architecture) repository interface provides methods to return Customer data from the
 * database.
 * <p>
 * DEVELOPER NOTE: This is the third and "deepest" layer of the application architecture. This layer is sometimes called
 * the "data access layer" (and repositories called "Data Access Objects" or DAO objects). This layer shouldn't have ANY
 * business logic in it at all. The only responsibility of a repository class is to either interface with a database to
 * retrieve and manipulate data, or to interact with an external web service. The service layer of this application
 * doesn't know or care where the repository got its data, or how it got it. It should also be "dumb" and just do its
 * job of putting and pulling data from the external source, not making business or quality decisions about the data.
 * <p>
 * This class is annotated as a @Repository, which is just an alias for the annotation @Component. In this case, the
 * repository is a JPA repository. Spring "magic" will automatically create an implementation of this repository that
 * will be capable of reading and writing Customer entities from the database. Technically, the JPA framework itself is
 * serving as the actual repository layer.
 *
 * @see Customer
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    /*
     * DEVELOPER NOTE: A JpaRepository only needs to know the class that it manages and the type
     * of the ID property of that class. You don't have to code anything. Spring does all the work.
     */
}
