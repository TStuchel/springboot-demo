package com.dbs.demo.customer.api.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DEVELOPER NOTE: This customer class is an immutable "Data Transfer Object" or "DTO". It is the Java implementation of
 * the JSON contract sent to this web service. It is JSON that defines the contract, not this class. This class is just
 * the "Java interpretation" of the contract. DTO contract classes should not have any business logic in them at all
 * except for perhaps light contract-centric validations.
 */
@With // DEVELOPER NOTE: Convenience methods for immutable objects
@Value // DEVELOPER NOTE: Lombok annotation indicating this is a value object
@Builder // DEVELOPER NOTE: Convenience factory methods for constructing this object
@AllArgsConstructor // DEVELOPER NOTE: Needed to resolve constructor conflict between @Value and @Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // DEVELOPER NOTE: Needed for Jackson deserialization.
public class CustomerDTO {

    // -------------------------------------------------- PROPERTIES ---------------------------------------------------

    // DEVELOPER NOTE: Properties of a DTO contract should always be an object type, not a primitive (except String,
    // it's  a special case). This allows fields to be absent from JSON during de-serialization and allow for fields
    // with a null value. Primitive types such as integers will will be set to a default value such as zero, which isn't
    // the same as "absent".
    @JsonProperty("id")
    Integer id;

    // DEVELOPER NOTE: While not required, using @JsonProperty lets you set the property name as seen in the serialized
    // JSON of this class to something different than the Java property. This is useful when refactoring the name of
    // the Java property without having to break the contract.
    @JsonProperty("fullName")
    String fullName;

    // DEVELOPER NOTE: @JsonFormat used here defines the string representation of this ZonedDateTime that will be seen
    // in the JSON. This isn't the whole story, though. You must also register the jsr310 module with Spring and/or
    // any ObjectMapper that might serialize/deserialize this class. This lets ObjectMapper know how to serialize and
    // deserialize ZonedDateTime.
    @JsonProperty("lastReadTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
    ZonedDateTime lastReadTimestamp;

    // DEVELOPER NOTE: @JsonSetter ensures Jackson won't create a null List if the property is 'null' in the JSON.
    @JsonProperty("orderNumbers")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    List<String> orderNumbers = new ArrayList<>();

}
