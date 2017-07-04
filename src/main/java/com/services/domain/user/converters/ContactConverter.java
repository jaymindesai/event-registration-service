package com.services.domain.user.converters;

import com.services.domain.user.Contact;
import com.services.domain.user.ContactDto;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {

    public ContactDto convertToDto(Contact contact){
        return ContactDto.builder()
                .primary(contact.getPrimary())
                .alternate(contact.getAlternate())
                .build();
    }

    public Contact convertToContact(ContactDto contactDto){
        return Contact.builder()
                .primary(contactDto.getPrimary())
                .alternate(contactDto.getAlternate())
                .build();
    }
}
