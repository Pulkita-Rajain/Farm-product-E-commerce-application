package com.app.ecom;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // Constructors, getters, and setters


}
