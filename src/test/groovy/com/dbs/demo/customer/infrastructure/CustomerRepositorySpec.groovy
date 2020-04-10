package com.dbs.demo.customer.infrastructure

import com.dbs.demo.BaseSpecification
import com.dbs.demo.customer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

/**
 * DEVELOPER NOTES: This test class uses the @DataJpaTest annotation to test the JPA-based CustomerRepository. Unlike
 * the Controller and Service test, this test needs Spring to initialize both a JPA EntityManager (to talk to the
 * database) as well as automatically implement the CustomerRepository interface. For this reason, this test class uses
 * field-style dependency injection and does not use Mockito.
 * <p>
 * There's some magic under the covers of this test. The H2 database engine is an in-memory database that knows SQL. It
 * only lives as long as the unit test runs, but for all intents and purposes JPA thinks that it's a real database. In
 * addition, because the Customer (and Order) classes are annotated as @Entity classes, Spring/JPA will automatically
 * create the correct schema/table structure in memory. All that was needed was to include the H2 library in the
 * build.gradle file.
 */
@DataJpaTest
class CustomerRepositorySpec extends BaseSpecification {

    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    @Autowired // <-- Required pattern when using @DataJpaTest so that it can wire in the JPA manager instance
    private TestEntityManager entityManager;

    /**
     * Class under test (spied to test protected methods)
     */
    @Autowired // <-- Required pattern when using @DataJpaTest
    private CustomerRepository customerRepository;


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    def "Retrieve a Customer with a given ID"() {

        // --
        given: "a Customer with a given ID is in the database"
        def expectedCustomer = podamFactory.manufacturePojo(Customer)
        def customerId = (Integer) entityManager.persistAndGetId(expectedCustomer)

        // --
        when: "an attempt is made to read the Customer record from the database"
        def actualCustomer = customerRepository.getOne(customerId) // Testing our JPA annotations, not repo method

        // --
        then: "the customer record with the given ID should be read from the database and returned as a Customer object"
        actualCustomer == expectedCustomer
    }

}
