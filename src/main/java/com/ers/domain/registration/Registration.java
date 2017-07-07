package com.ers.domain.registration;

import com.ers.domain.AbstractEntity;
import com.ers.domain.event.Event;
import com.ers.domain.event.TimeSlot;
import com.ers.domain.user.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "EVENT_ID"})})
public class Registration extends AbstractEntity {

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "TIME_SLOT_ID", nullable = false)
    private TimeSlot timeSlot;
}
