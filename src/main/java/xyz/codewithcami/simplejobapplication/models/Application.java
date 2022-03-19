package xyz.codewithcami.simplejobapplication.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please enter your first name")
    private String firstName;

    @NotBlank(message = "Please enter your last name")
    private String lastName;

    @NotBlank(message = "Please enter your email address")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Please enter your phone number")
    private String phone;

    private String address;

    @NotNull(message = "Please enter your date of birth")
    private LocalDate dateOfBirth;

    private String cv;

    @Column(columnDefinition = "varchar(20) default('Pending')")
    private String status = Status.PENDING;

    @Transient
    @JsonProperty("full_name")
    public String getName() {
        return firstName + " " + lastName;
    }

}
