package com.services.application.email;

import com.services.domain.event.EventDto;
import com.services.domain.event.EventService;
import com.services.domain.event.TimeSlotDto;
import com.services.domain.user.UserDto;
import com.services.domain.user.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Service
public class EmailHelper {

    private final Configuration config;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public EmailHelper(Configuration config, UserService userService, EventService eventService){
        this.config = config;
        this.userService = userService;
        this.eventService = eventService;
    }

    public void prepareMessage(String eventCode, String slotCode, int id, MimeMessageHelper helper) throws MessagingException, IOException, TemplateException {
        EventDto event = eventService.findByCode(eventCode);
        UserDto user = userService.find(id);
        TimeSlotDto timeSlot = event.getVenue().getTimeSlots().stream()
                .filter(slot -> slot.getSlotCode().equals(slotCode))
                .findFirst()
                .get();

        final Map<String, Object> model = new HashMap<>();
        model.put("userName", user.getFirstName());
        model.put("eventName", event.getName());
        model.put("venue", event.getVenue().getName());
        model.put("city", event.getVenue().getCity());
        model.put("date", event.getDate().toString());
        model.put("startTime", timeSlot.getStartTime().toString());
        model.put("endTime", timeSlot.getEndTime().toString());
        model.put("url", constructUrl(eventCode, slotCode, id));

        config.setClassForTemplateLoading(this.getClass(), "/templates");
        Template template = config.getTemplate("confirmation.ftl");
        helper.setTo(user.getEmail());
        helper.setText(processTemplateIntoString(template, model), true);
        helper.setSubject("Registration Confirmation Mail");
    }

    @SuppressWarnings("ALL")
    private String constructUrl(String eventCode, String slotCode, int id) {
        return new StringBuilder("http://localhost:8090/api/registrations")
                .append("/event/").append(eventCode)
                .append("/slot/").append(slotCode)
                .append("/user/").append(valueOf(id))
                .toString();
    }
}
