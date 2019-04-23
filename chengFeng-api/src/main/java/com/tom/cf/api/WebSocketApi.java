package com.tom.cf.api;

import com.tom.cf.api.test.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @method:Socket测试
 */
@Controller
public class WebSocketApi {

    @MessageMapping("/hello")
    @SendTo("/topic/tests")
    public Message test(Message m) throws Exception{
        return  m;
    }


}
