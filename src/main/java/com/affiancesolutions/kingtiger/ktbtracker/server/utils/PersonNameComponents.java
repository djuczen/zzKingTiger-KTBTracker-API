package com.affiancesolutions.kingtiger.ktbtracker.server.utils;

import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.logging.Logger;

public class PersonNameComponents {

    private static final String CLASS_NAME = PersonNameComponents.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final List<String> VALID_PREFIXES = Arrays.asList(
        "mr", "mrs", "ms", "miss", "dr", "hon", "mayor", "president", "pres", "master", "grandmaster"
    );

    private static final List<String> VALID_SUFFIXES = Arrays.asList(
            "jr", "sr", "iii", "iv", "md", "od", "dds", "dvm", "phd", "ret"
    );

    private String namePrefix;

    private String givenName;

    private String middleName;

    private String familyName;

    private String nameSuffix;

    private String nickname;

    private String phoneticRepresentation;

    public PersonNameComponents(String personName) {
        final String METHOD_NAME = "<init>";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, personName);
        String components[] = Optional.of(personName).orElse("").replace(",", "").split("\\s+");

        LOGGER.fine(Arrays.toString(new List[]{Arrays.asList(components)}));
        if (components.length > 2) {
            if (VALID_PREFIXES.contains(components[0].replace(".", "").toLowerCase())) {
                this.namePrefix = components[0];
                components = Arrays.copyOfRange(components, 1, components.length);
            }
            if (VALID_SUFFIXES.contains(components[components.length - 1].replace(".", "").toLowerCase())) {
                this.nameSuffix = components[components.length - 1];
                components = Arrays.copyOfRange(components, 0, components.length - 1);
            }
        }
        LOGGER.fine(Arrays.toString(new List[]{Arrays.asList(components)}));

        if (components.length > 2) {
            this.givenName = components[0];
            this.familyName = components[components.length - 1];
            this.middleName = String.join(" ", Arrays.copyOfRange(components, 1, components.length - 1));
        } else {
            if (components.length > 1) {
                this.givenName = components[0];
                this.familyName = components[1];
            } else {
                this.givenName = components[0];
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, this);
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneticRepresentation() {
        return phoneticRepresentation;
    }

    public String familyNameSequence() {
        return String.format("%s%s%s%s",
                this.familyName != null ? this.familyName : "",
                this.nameSuffix != null ? " " + this.nameSuffix : "",
                this.givenName != null ? ", " + this.givenName : "",
                this.middleName != null ? " " + this.middleName : "");
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", namePrefix, givenName, middleName, familyName, nameSuffix);
    }
}
