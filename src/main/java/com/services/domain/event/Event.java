package com.services.domain.event;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Event extends AbstractEntity {

    @Column(name = "EVENT_NAME", nullable = false)
    private String name;

    @Column(name = "EVENT_DATE", nullable = false)
    private LocalDate date;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "VENUE_ID", nullable = false)
    private Venue venue;
}
