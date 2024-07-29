package code.devscreen.currenttakehome.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import code.devscreen.currenttakehome.DTO.PingDTO;

@RestController
@RequestMapping(path ="/")
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<PingDTO> pingFnc() {
        PingDTO response = new PingDTO();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        response.setServerTime(nowAsISO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
