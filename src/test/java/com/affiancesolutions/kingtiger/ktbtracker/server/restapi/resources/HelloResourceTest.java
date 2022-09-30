package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;


import com.affiancesolutions.kingtiger.ktbtracker.client.services.HelloService;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.TestClient;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestConfiguration.class)
public class HelloResourceTest {

    @TestClient
    public HelloService helloService;

    @Test
    void hello() {
        String message = helloService.hello(null);

        Assertions.assertEquals(message, "Hello, world!");
    }
}
