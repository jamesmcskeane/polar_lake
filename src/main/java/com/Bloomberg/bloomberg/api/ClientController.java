package com.Bloomberg.bloomberg.api;
import com.Bloomberg.bloomberg.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Client Controller
 * Using Spring boot and REST calls
 * Client controller creates mapping for call "/calculation
 * POST request is made using plain text i.e. +1/1 is 1 + 1
 * Client applications such as POSTMAN can be used to test the API
 *
 * @Author James McSkeane
 * @Version 1.0
 * @Date 25-11-2019
 */

@RequestMapping("/calculation")
@RestController
public class ClientController {
    // Create Service object
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    // method to pass String to the calculation server VIA REST POST
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String getCalculation (@RequestBody String raw)
    {
        return clientService.sendToCalculationServer(raw);
    }
}
