package CourseManagerProject.CourseManager.config;

import CourseManagerProject.CourseManager.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Klasa konfiguracyjna odpowiedzialna za tworzenie przykładowych danych (beanów) aplikacji.
 */
@Configuration
@RequiredArgsConstructor
public class SampleDataConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    private User createUser(String firstname, String surname, int age, String email, String password, boolean isOrganizer) {
        return User.builder()
                .firstname(firstname)
                .surname(surname)
                .age(age)
                .email(email)
                .password(passwordEncoder.encode(password))
                .isOrganizer(isOrganizer)
                .build();
    }

    private Classroom createClassroom(int capacity, String location, String info, String classroomName) {
        return Classroom.builder()
                .capacity(capacity)
                .location(location)
                .info(info)
                .classroomName(classroomName)
                .build();
    }

    private Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }

    private Event createEvent(String name, LocalDateTime start, LocalDateTime end, int maxParticipants, int minAge,
                              String info, User organizer, Classroom classroom, List<Tag> tags, List<User> participants) {
        Event event = Event.builder()
                .name(name)
                .startDatetime(start)
                .endDatetime(end)
                .maxParticipants(maxParticipants)
                .minAge(minAge)
                .info(info)
                .organizer(organizer)
                .classroom(classroom)
                .build();

        event.getTags().addAll(tags);
        event.getParticipants().addAll(participants);

        return event;
    }

    @Bean
    public List<User> sampleUsers() {
        return List.of(
                createUser("Anna", "Nowak", 34, "anna.nowak@agh.edu.pl", "SecurePass123", true),
                createUser("Robert", "Kowalski", 45, "robert.kowalski@agh.edu.pl", "Password456!", true),
                createUser("Magdalena", "Wiśniewska", 38, "magda.wisniewska@agh.edu.pl", "P@ssw0rd!", true),
                createUser("Jan", "Wójcik", 30, "jan.wojcik@agh.edu.pl", "J@nPass987", true),
                createUser("Marek", "Zawada", 40, "marek.zawada@agh.edu.pl", "MarekStrong333", true),
                createUser("Ewa", "Lewandowska", 29, "ewa.lewandowska@agh.edu.pl", "EwaRocks456", false),
                createUser("Mateusz", "Zieliński", 26, "mateusz.zielinski@gmail.com", "MateoPass222", false),
                createUser("Oliwia", "Kowal", 22, "oliwia.kowal@gmail.com", "Olivia321", false),
                createUser("Jakub", "Mazur", 28, "jakub.mazur@gmail.com", "MazurStrong999", false),
                createUser("Tomasz", "Wiatr", 23, "tomasz.wiatr@gmail.com", "WiatrPass888", false),
                createUser("Karolina", "Sosna", 24, "karolina.sosna@gmail.com", "Sosna123", false),
                createUser("Wojciech", "Góra", 25, "wojciech.gora@gmail.com", "Gora123", false),
                createUser("Zofia", "Twardowska", 27, "zofia.twardowska@gmail.com", "TwardaZofia", false),
                createUser("Piotr", "Krzak", 29, "piotr.krzak@gmail.com", "Krzak987", false),
                createUser("Bartek", "Las", 21, "bartek.las@gmail.com", "Lasik123", false),
                createUser("Joanna", "Morze", 22, "joanna.morze@gmail.com", "MorzeUwielbiam", false)
        );
    }

    @Bean
    public List<Classroom> sampleClassrooms() {
        return List.of(
                createClassroom(30, "D10 AGH Campus", "Komfortowa sala z rzutnikiem", "101A"),
                createClassroom(50, "C5 AGH Campus", "Nowoczesna sala z dostępem do VR", "302B"),
                createClassroom(20, "B1 AGH Campus", "Sala do pracy grupowej", "205C"),
                createClassroom(60, "F3 AGH Campus", "Sala wykładowa z klimatyzacją", "401D")
        );
    }

    @Bean
    public List<Tag> sampleTags() {
        return List.of(
                createTag("Informatyka"),
                createTag("Zarządzanie"),
                createTag("Sztuczna inteligencja"),
                createTag("Inżynieria oprogramowania")
        );
    }

    @Bean
    public List<Event> sampleEvents(List<User> sampleUsers, List<Classroom> sampleClassrooms, List<Tag> sampleTags) {
        return List.of(
                createEvent(
                        "Wprowadzenie do AI",
                        LocalDateTime.of(2025, 3, 15, 9, 0),
                        LocalDateTime.of(2025, 3, 15, 12, 0),
                        40,
                        18,
                        "Podstawowe zagadnienia związane z AI",
                        sampleUsers.get(0),
                        sampleClassrooms.get(0),
                        List.of(sampleTags.get(2)),
                        List.of(sampleUsers.get(4), sampleUsers.get(5))
                ),
                createEvent(
                        "Warsztaty z zarządzania zespołem",
                        LocalDateTime.of(2025, 4, 10, 10, 0),
                        LocalDateTime.of(2025, 4, 10, 14, 0),
                        30,
                        18,
                        "Skuteczne zarządzanie projektami",
                        sampleUsers.get(1),
                        sampleClassrooms.get(1),
                        List.of(sampleTags.get(1)),
                        List.of(sampleUsers.get(6), sampleUsers.get(7))
                ),
                createEvent(
                        "Programowanie aplikacji mobilnych",
                        LocalDateTime.of(2025, 5, 20, 11, 0),
                        LocalDateTime.of(2025, 5, 20, 15, 0),
                        50,
                        18,
                        "Kurs tworzenia aplikacji na Androida",
                        sampleUsers.get(2),
                        sampleClassrooms.get(2),
                        List.of(sampleTags.get(3)),
                        List.of(sampleUsers.get(4), sampleUsers.get(7))
                ),
                createEvent(
                        "Podstawy inżynierii oprogramowania",
                        LocalDateTime.of(2025, 6, 15, 9, 0),
                        LocalDateTime.of(2025, 6, 15, 12, 0),
                        40,
                        18,
                        "Warsztaty z dobrych praktyk programistycznych",
                        sampleUsers.get(3),
                        sampleClassrooms.get(3),
                        List.of(sampleTags.get(3)),
                        List.of(sampleUsers.get(8), sampleUsers.get(9))
                ),
                createEvent(
                        "Zaawansowane algorytmy AI",
                        LocalDateTime.of(2025, 7, 10, 10, 0),
                        LocalDateTime.of(2025, 7, 10, 14, 0),
                        30,
                        18,
                        "Algorytmy uczące i sieci neuronowe",
                        sampleUsers.get(0),
                        sampleClassrooms.get(1),
                        List.of(sampleTags.get(2)),
                        List.of(sampleUsers.get(10), sampleUsers.get(11))
                ),
                createEvent(
                        "Team Building",
                        LocalDateTime.of(2025, 8, 20, 11, 0),
                        LocalDateTime.of(2025, 8, 20, 15, 0),
                        50,
                        18,
                        "Zarządzanie zespołami technologicznymi",
                        sampleUsers.get(1),
                        sampleClassrooms.get(0),
                        List.of(sampleTags.get(1)),
                        List.of(sampleUsers.get(6), sampleUsers.get(12))
                ),
                createEvent(
                        "Hackathon AI",
                        LocalDateTime.of(2025, 9, 15, 9, 0),
                        LocalDateTime.of(2025, 9, 15, 21, 0),
                        100,
                        18,
                        "Całodniowe wyzwanie programistyczne",
                        sampleUsers.get(2),
                        sampleClassrooms.get(3),
                        List.of(sampleTags.get(2), sampleTags.get(3)),
                        List.of(sampleUsers.get(8), sampleUsers.get(13))
                ),
                createEvent(
                        "Prezentacje zespołowe",
                        LocalDateTime.of(2025, 10, 5, 9, 0),
                        LocalDateTime.of(2025, 10, 5, 12, 0),
                        40,
                        18,
                        "Podsumowanie pracy nad projektami",
                        sampleUsers.get(3),
                        sampleClassrooms.get(2),
                        List.of(sampleTags.get(1), sampleTags.get(3)),
                        List.of(sampleUsers.get(9), sampleUsers.get(10), sampleUsers.get(14))
                )
        );
    }
}
