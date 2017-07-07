package com.services.domain.event;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Event extends AbstractEntity {

    @Column(name = "EVENT_CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "EVENT_NAME", nullable = false)
    private String name;

    @Column(name = "EVENT_DATE", nullable = false)
    private LocalDate date;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "VENUE_ID", nullable = false)
    private Venue venue;
}
