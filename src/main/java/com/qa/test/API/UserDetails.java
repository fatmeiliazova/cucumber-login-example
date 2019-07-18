package com.qa.test.API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDetails {
    private static final String DEFAULT_FIRST_NAME = "John";
    private static final String DEFAULT_LAST_NAME = "Doe";
    private static final String DEFAULT_TITLE = "";
    private static final String DEFAULT_MIDDLE_NAME = "";
    private static final String DEFAULT_DOB = "1997-12-31";
    private static final String DEFAULT_PHONE_NUMBER = "+447875496612";
    private static final String DEFAULT_FLAT_NUMBER = "";
    private static final String DEFAULT_BUILDING_NUMBER = "8";
    private static final String DEFAULT_BUILDING_NAME = "";
    private static final String DEFAULT_STREET = "Mortimer St.";
    private static final String DEFAULT_SUB_STREET = "";
    private static final String DEFAULT_TOWN = "London";
    private static final String DEFAULT_POSTCODE = "W1T 3JJ";
    private static final String DEFAULT_COUNTRY_ISO = "GBR";
    private static Logger LOG = LoggerFactory.getLogger(UserDetails.class);

    private String email;
    private String first_name;
    public String last_name;
    private String middle_name;
    private String title;
    private String date_of_birth;
    private String phone_number;
    private String flat_number;
    private String building_number;
    private String building_name;
    private String street;
    private String sub_street;
    private String town;
    private String post_code;
    private String country_iso;


    public static UserDetailsBuilder builder() {
        return new UserDetailsBuilder();
    }


    public static class UserDetailsBuilder {
        private String email = null;
        private String first_name = DEFAULT_FIRST_NAME;
        private String last_name = DEFAULT_LAST_NAME;
        private String middle_name = DEFAULT_MIDDLE_NAME;
        private String title = DEFAULT_TITLE;
        private String date_of_birth = DEFAULT_DOB;
        private String phone_number = DEFAULT_PHONE_NUMBER;
        private String flat_number = DEFAULT_FLAT_NUMBER;
        private String building_number = DEFAULT_BUILDING_NUMBER;
        private String building_name = DEFAULT_BUILDING_NAME;
        private String street = DEFAULT_STREET;
        private String sub_street = DEFAULT_SUB_STREET;
        private String town = DEFAULT_TOWN;
        private String post_code = DEFAULT_POSTCODE;
        private String country_iso = DEFAULT_COUNTRY_ISO;


        public UserDetailsBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDetailsBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        public UserDetailsBuilder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public UserDetailsBuilder middle_name(String middle_name) {
            this.middle_name = middle_name;
            return this;
        }

        public UserDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public UserDetailsBuilder date_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
            return this;
        }

        public UserDetailsBuilder phone_number(String phone_number) {
            this.phone_number = phone_number;
            return this;
        }

        public UserDetailsBuilder flat_number(String flat_number) {
            this.flat_number = flat_number;
            return this;
        }

        public UserDetailsBuilder building_number(String building_number) {
            this.building_number = building_number;
            return this;
        }

        public UserDetailsBuilder building_name(String building_name) {
            this.building_name = building_name;
            return this;
        }

        public UserDetailsBuilder street(String street) {
            this.street = street;
            return this;
        }

        public UserDetailsBuilder sub_street(String sub_street) {
            this.sub_street = sub_street;
            return this;
        }

        public UserDetailsBuilder town(String town) {
            this.town = town;
            return this;
        }

        public UserDetailsBuilder post_code(String post_code) {
            this.post_code = post_code;
            return this;
        }

        public UserDetailsBuilder country_iso(String country_iso) {
            this.country_iso = country_iso;
            return this;
        }

        public UserDetails build() {
            UserDetails user = new UserDetails();
            user.email = this.email;
            user.first_name = this.first_name;
            user.last_name = this.last_name;
            user.middle_name = this.middle_name;
            user.title = this.title;
            user.date_of_birth = this.date_of_birth;
            user.phone_number = this.phone_number;
            user.flat_number = this.flat_number;
            user.building_number = this.building_number;
            user.building_name = this.building_name;
            user.street = this.street;
            user.sub_street = this.sub_street;
            user.town = this.town;
            user.post_code = this.post_code;
            user.country_iso = this.country_iso;
            if (user.email == null) {
                throw new RuntimeException("email ID cannot be null");
            }
            return user;
        }
    }

    public String toJsonString() {
        return GSON.createJSONString(this);
    }
}
