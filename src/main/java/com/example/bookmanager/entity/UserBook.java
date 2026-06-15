package com.example.bookmanager.entity;

import com.example.bookmanager.entity.enums.ReadingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Enumerated(EnumType.STRING)
    private ReadingStatus status;
    @Min(1) @Max(5)
    private Integer rating;
    private LocalDate addedDate;

    @PrePersist
    public void prePersist() {
        if (this.addedDate == null) {
            this.addedDate = LocalDate.now();
        }
    }
}


