package com.mavis.chattingroom.controller.chattingRoom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleChattingRoomController {

    @GetMapping("/initRoom")
    public ModelAndView initRoom() {
        return new ModelAndView("SimpleChattingRoom.html");
    }
}
