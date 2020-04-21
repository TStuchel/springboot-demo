package com.dbs.demo.customer.api

import com.dbs.demo.BaseSpecification
import com.dbs.demo.customer.api.contract.CustomerDTO
import com.dbs.demo.customer.model.Customer

class CustomerTranslatorSpec extends BaseSpecification {

    // ------------------------------------------------ DOCUMENTATION --------------------------------------------------

    def setupSpec() {
        reportHeader """
        <br/>
        An anti-corruption layer exists between the web API layer and the application layer. The contract to change or
        create the state of a Customer is first <strong>translated</strong> from the JSON-deserialized contract object 
        to a domain entity understood by the application layer. After the application layer processes the command, the 
        new state of the entity is translated back to a JSON-serializable response contract.
        <br/>
        """
    }


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    /**
     * DEVELOPER NOTE: There should be a test that verifies a contract can be mapped where all of the fields haven't
     * been set. This/protects against null pointer exceptions and verifies default values given to optional fields.
     */
    def "Translate an empty Customer contract to a Customer domain entity"() {

        // --
        given: "an empty Customer contract"
        def customerDto = CustomerDTO.builder().build()

        // --
        when: "the Customer contract is translated to a Customer entity"
        def customer = new CustomerTranslator().toEntity(customerDto)

        // --
        then: "a Customer entity should be returned"
        customer != null

        and: "all fields of the Customer should be correctly mapped to default values"
        assert customer.getFullName() == null
        assert customer.getCustomerId() == null
        assert customer.getLastReadTimestamp() == null
        assert customer.getStreetAddress() == "Unknown"
    }

    /**
     * DEVELOPER NOTE: There should be a test that verifies that EVERY field is mapped.
     */
    def "Translate a fully populated Customer contract to a Customer domain entity"() {

        // --
        given: "a fully populated Customer contract"
        def customerDto = podamFactory.manufacturePojo(CustomerDTO)

        // --
        when: "the Customer contract is translated to a Customer entity"
        def customer = new CustomerTranslator().toEntity(customerDto)

        // --
        then: "a Customer entity should be returned"
        customer != null

        and: "all fields of the Customer contract should be correctly mapped"
        assert customer.getFullName() == customerDto.getFullName()
        assert customer.getCustomerId() == customerDto.getId()
        assert customer.getLastReadTimestamp() == customerDto.getLastReadTimestamp()
        assert customer.getStreetAddress() == "Unknown"
    }

    /**
     * DEVELOPER NOTE: There should be a test that verifies an entity can be mapped where all of the fields haven't been
     * set. This/protects against null pointer exceptions and verifies default values given to optional fields.
     */
    def "Translate an empty Customer domain entity to a Customer contract"() {

        // --
        given: "an empty Customer entity"
        def customer = new Customer()

        // --
        when: "the Customer entity is translated to a Customer contract"
        def customerDto = new CustomerTranslator().toContract(customer)

        // --
        then: "a Customer contract should be returned"
        customerDto != null

        and: "all fields of the Customer contract should be mapped to default values"
        assert customerDto.getFullName() == null
        assert customerDto.getId() == null
        assert customerDto.getLastReadTimestamp() == null
    }

    /**
     * DEVELOPER NOTE: There should be a test that verifies that EVERY field is mapped.
     */
    def "Translate a fully populated Customer domain entity to a Customer contract"() {

        // --
        given: "a fully populated Customer entity"
        def customer = podamFactory.manufacturePojo(Customer)

        // --
        when: "the Customer entity is translated to a Customer contract"
        def customerDto = new CustomerTranslator().toContract(customer)

        // --
        then: "a Customer contract should be returned"
        customerDto != null

        and: "all fields of the Customer should be correctly mapped"
        assert customerDto.getFullName() == customer.getFullName()
        assert customerDto.getId() == customer.getCustomerId()
        assert customerDto.getLastReadTimestamp() == customer.getLastReadTimestamp()
    }

}
