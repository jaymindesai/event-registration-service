package com.services.domain.registration;

import com.services.domain.AbstractEntity;
import com.services.domain.event.Event;
import com.services.domain.event.TimeSlot;
import com.services.domain.user.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "EVENT_ID"})})
public class Registration extends AbstractEntity {

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "TIME_SLOT_ID", nullable = false)
    private TimeSlot timeSlot;
}
