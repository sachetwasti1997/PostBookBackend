package com.sachet.postBook.model;

public class AuthenticationResponse {

    private String jsonWebToken;

    public AuthenticationResponse(String jsonWebToken){
        this.jsonWebToken = jsonWebToken;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }

}
