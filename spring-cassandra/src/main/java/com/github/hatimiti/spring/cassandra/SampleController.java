package com.github.hatimiti.spring.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@RestController
public class SampleController {

    @Autowired
    private final LoginEventRepository loginEventRepository;

    public SampleController(final LoginEventRepository loginEventRepository) {
        this.loginEventRepository = loginEventRepository;
    }

    @GetMapping("/")
    public String hello() {
        final LoginEventKey k = new LoginEventKey();
        k.setPersonId("sample");
        k.setEventTime(toDate("20180428162800"));
//        final Optional<LoginEvent> lv1 = loginEventRepository.findById(k);
        final Optional<LoginEvent> lv2 = loginEventRepository.findByPersonIdAndEventTime(k.getPersonId(), k.getEventTime());

        return "{" + lv2.map(LoginEvent::getIpAddress).orElse("default-value") + "}";
    }

    @GetMapping("/insert")
    public String insert() {
        final LoginEventKey k = new LoginEventKey();
        k.setPersonId("sample");
        k.setEventTime(toDate("20180428162800"));
        final LoginEvent ev = new LoginEvent();
        ev.setPk(k);
        ev.setEventCode(999);
        ev.setIpAddress("localhost");
        final LoginEvent lv = loginEventRepository.save(ev);
        return lv.toString();
    }

    private static Date toDate(final String yyyyMmDdHhMmSs) {
        try {
            final SimpleDateFormat sdf
                    = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            return sdf.parse(yyyyMmDdHhMmSs);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
