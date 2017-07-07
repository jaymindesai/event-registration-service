package com.ers.domain.event;

import com.ers.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

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

    @OneToMany(cascade = {PERSIST, MERGE, REFRESH}, mappedBy = "venue")
    private List<TimeSlot> timeSlots = new ArrayList<>();
}
