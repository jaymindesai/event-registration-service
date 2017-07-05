package com.services.domain.registration;

import com.services.domain.AbstractEntity;
import com.services.domain.event.TimeSlot;
import com.services.domain.user.User;
import com.services.domain.event.Event;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "EVENT_ID"})})
public class Registration extends AbstractEntity {

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "TIME_SLOT_ID", nullable = false)
    private TimeSlot timeSlot;
}
