package com.xl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    /**
    @Autowired
    private EchoService echoService;
     */

    @Autowired
    private DynamicConfig dynamicConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Value(("${grayEnv:base}"))
    private String gray;

    /**
    @RequestMapping("/hellofegin")
    @ResponseBody
    public String helloFegin(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return echoService.hello(name);
    }
     */

    @RequestMapping("/hellorest")
    @ResponseBody
    public String helloRest(@RequestParam(name = "name", defaultValue = "unknown user") String name) {

        if(StringUtils.equals(dynamicConfig.getBlow(),"1")) {
            try {
                Thread.sleep(3000);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        String currentConsumerVersion = "hellorest current consumer version is "+gray;

        String result = restTemplate.getForObject("http://mseprovider/hello?name="+name,
                String.class);
        return  currentConsumerVersion +  ", invoke provider is: "+result;
    }
}
