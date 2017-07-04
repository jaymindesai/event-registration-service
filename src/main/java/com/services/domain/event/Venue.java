package com.services.domain.event;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Venue extends AbstractEntity {

    @Column(name = "VENUE_NAME", nullable = false)
    private String name;

    @Column(name = "CITY", nullable = false)
    private String city;

    @OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "venue")
    private List<TimeSlot> timeSlots = new ArrayList<>();
}
