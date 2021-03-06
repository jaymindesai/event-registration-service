package com.ers.domain.event;

import com.ers.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import java.time.LocalTime;

import static javax.persistence.CascadeType.ALL;

@Data
@ToString(exclude = "venue")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = "venue")
@Entity
public class TimeSlot extends AbstractEntity {

    @Column(name = "SLOT_CODE", nullable = false)
    private String slotCode;

    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;

    @Column(name = "CAPACITY", nullable = false)
    @Max(value = 50)
    private int capacity;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "VENUE_ID", nullable = false)
    private Venue venue;

    public boolean isFull() {
        return this.capacity == 0;
    }

    public int decrementCapacity() {
        return this.capacity - 1;
    }
}
