package code.sample.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderNacosController {

    private RestTemplate template;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @Autowired
    public OrderNacosController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id) {
        return template.getForObject(serverURL + "/payment/nacos/" + id, String.class);
    }
}
