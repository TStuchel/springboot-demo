package com.dbs.demo.customer.app

import com.dbs.demo.BaseSpecification
import com.dbs.demo.customer.infrastructure.CustomerRepository
import com.dbs.demo.customer.model.Customer
import com.dbs.demo.exception.BusinessException
import spock.lang.Unroll

/**
 * DEVELOPER NOTE:  Note that we don't need Spring... nothing in this spec, or the tested class, cares about Spring.
 * This is all basic mocking. Don't involve Spring unless you have to; it just slows down your tests.
 */
class CustomerServiceSpec extends BaseSpecification {

    // ------------------------------------------------ DOCUMENTATION --------------------------------------------------

    def setupSpec() {
        reportHeader """
        <br/>
        The Customer service is the primary application layer service for coordinating actions against Customer domain
        entities.
        <br/>
        """
    }


    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    private CustomerRepository customerRepository


    // ------------------------------------------------ MEMBER VARIABLES --------------------------------------------------

    // Class under test
    private CustomerService customerService


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------


    def setup() {
        customerRepository = Mock(CustomerRepository)
        customerService = new CustomerService(customerRepository)
    }

    def "Find a customer by its ID"() {

        // --
        given: "a valid customer ID and a customer with that ID is in the system"
        def expectedCustomer = podamFactory.manufacturePojo(Customer)
        def customerId = expectedCustomer.getCustomerId()

        // --
        when: "the customer is requested"
        def actualCustomer = customerService.getCustomer(customerId)

        // --
        then: "an attempt should be made to retrieve the customer from the database"
        1 * customerRepository.findById(customerId) >> Optional.of(expectedCustomer)

        and: "the Customer with the given ID should be returned"
        actualCustomer == expectedCustomer
    }

    def "Find a customer that does not exist"() {

        // --
        given: "a customer ID"
        def customerId = podamFactory.manufacturePojo(Integer)

        // --
        when: "the customer is requested, but the customer is NOT in the system"
        def actualCustomer = customerService.getCustomer(customerId)

        // --
        then: "an attempt should be made to retrieve the customer from the database"
        1 * customerRepository.findById(customerId) >> Optional.empty()

        and: "null should be returned"
        actualCustomer == null
    }

    def "Find a customer using an invalid ID"() {

        given: "an invalid customer ID"
        def customerId = -999

        when: "the customer is requested"
        customerService.getCustomer(customerId)

        then: "a BusinessException should be thrown"
        BusinessException ex = thrown()

        and: "its message should contain the invalid customer ID."
        ex.getMessage().contains(String.valueOf(customerId))
    }

    @Unroll
    def "An ID of [#customerId] should validate as [#isValid]"() {

        // --
        expect: "the customer ID to be validated"
        isValid == customerService.isValidCustomerId(customerId)

        // --
        where: "customer IDs may or may not be valid"
        customerId || isValid
        null       || false
        -999       || false
        0          || false
        1234       || true
    }

}
