package xyz.codewithcami.simplejobapplication.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter your date of birth")
    private LocalDate dateOfBirth;

    private String cv;

    @Column(columnDefinition = "text")
    private String coverLetter;

    @Column(columnDefinition = "varchar(20) default('Pending')")
    private String status = Status.PENDING;

    @Transient
    @JsonProperty("full_name")
    public String getName() {
        return firstName + " " + lastName;
    }

    @Transient
    @JsonProperty("status_color")
    public String getStatusColor() {
        Map<String, String> statusColors = new HashMap<>();
        statusColors.put(Status.PENDING, "primary");
        statusColors.put(Status.PASSED, "success");
        statusColors.put(Status.DROPPED, "danger");
        return statusColors.get(status);
    }

}
