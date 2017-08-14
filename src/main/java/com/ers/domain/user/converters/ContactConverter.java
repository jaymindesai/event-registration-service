package com.ers.domain.user.converters;

import com.ers.domain.user.Contact;
import com.ers.domain.user.dto.ContactDto;

public class ContactConverter {

    public static ContactDto convertToDto(Contact contact) {
        return ContactDto.builder()
                .primary(contact.getPrimary())
                .alternate(contact.getAlternate())
                .build();
    }

    public static Contact convertToContact(ContactDto contactDto) {
        return Contact.builder()
                .primary(contactDto.getPrimary())
                .alternate(contactDto.getAlternate())
                .build();
    }
}
