package com.test.firstproject.service;
import com.test.firstproject.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.test.firstproject.dto.request.StudentRequest;
import com.test.firstproject.dto.response.StudentProfileDto;
import com.test.firstproject.dto.response.StudentResponse;
import com.test.firstproject.dto.response.StudentWithProfileResponse;
import com.test.firstproject.entity.Student;
import com.test.firstproject.exception.EmailAlreadyExistsException;
import com.test.firstproject.exception.StudentNotFoundException;
import com.test.firstproject.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final EmailService emailService;

    public StudentServiceImpl(StudentRepository studentRepository,
                              EmailService emailService) {
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> searchStudent(String name, String email) {
        log.info(
                "Searching students by name='{}' and email='{}'",
                name,
                email
        );
        List<Student> students =
                studentRepository
                        .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                                name,
                                email
                        );

        log.debug(
                "{} students found.",
                students.size()
        );
        return students.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        log.info("Creating new student with email: {}", request.email());
        if (studentRepository.existsByEmail(request.email())) {
            log.warn("Student already exists with email: {}", request.email());
            throw new EmailAlreadyExistsException(
                    request.email()
            );

        }

        Student student = new Student();

        student.setName(request.name());
        student.setEmail(request.email());
        student.setAge(request.age());

        Student savedStudent = studentRepository.save(student);
        emailService.sendEmail(

                savedStudent.getEmail(),

                "Registration",

                "Welcome to Student Portal"

        );
        BackgroundService backgroundService = new BackgroundService();
        backgroundService.start();
        System.out.println("Main Thread: " + Thread.currentThread().getName());

        log.info(
                "Student created successfully with ID: {}",
                savedStudent.getId());

        return mapToResponse(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        log.info("StudentServiceImpl.getAllStudents() started");
        List<Student> students =
                studentRepository.findAllWithProfiles();
        log.info("StudentServiceImpl.getAllStudents() ended");
        return students.stream()
                .map(this::mapToResponse)
                .toList();

    }
    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> getStudentsPaginated(int page, int size, String sortBy, String direction) {

        // Enforce a hard limit on page size
        if (size > 100) {
            size = 100;
        }
        // Determine the sorting direction
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        // Construct the Pageable request
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentsPage = studentRepository.findAll(pageable);

        return studentsPage.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Student student =
                studentRepository.findWithProfileById(id)
                        .orElseThrow(
                                () -> new StudentNotFoundException(id)
                        );

        log.debug("Student {} fetched successfully.", id);
        return mapToResponse(student);

    }

    @Override
    @Transactional(readOnly = true)
    public StudentWithProfileResponse getStudentWithProfile(Long id) {

        Student student = studentRepository.findWithProfileById(id)
                        .orElseThrow(
                                () -> new StudentNotFoundException(id)
                        );
        var profile = student.getProfile();
        return new StudentWithProfileResponse(
                student.getId(),
                student.getName(),
                profile != null ? profile.getPhone() : null,
                profile != null ? profile.getAddress() : null,
                profile != null ? profile.getBloodGroup() : null,
                profile != null ? profile.getCnic() : null

        );

    }

    @Override
    @Transactional
    public StudentResponse updateStudent(
            Long id,
            StudentRequest request
    ) {

        log.info("Updating student with ID: {}", id);
        Student existingStudent =
                studentRepository.findById(id)
                        .orElseThrow(
                                () -> new StudentNotFoundException(id)
                        );

        existingStudent.setName(request.name());
        existingStudent.setEmail(request.email());
        existingStudent.setAge(request.age());

        Student updatedStudent =
                studentRepository.save(existingStudent);

        log.info("Student {} updated successfully.", id);
        return mapToResponse(updatedStudent);

    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {

        log.info("Deleting student with ID: {}", id);
        Student student =
                studentRepository.findById(id)
                        .orElseThrow(
                                () -> new StudentNotFoundException(id)
                        );

        log.info("Student {} deleted successfully.", id);
        studentRepository.delete(student);

    }

    private StudentResponse mapToResponse(Student student) {StudentProfileDto profileDto = null;
        if (student.getProfile() != null) {
            profileDto =
                    new StudentProfileDto(

                            student.getProfile().getId(),

                            student.getProfile().getPhone(),

                            student.getProfile().getAddress(),

                            student.getProfile().getBloodGroup(),

                            student.getProfile().getCnic()

                    );

        }
        return new StudentResponse(

                student.getId(),

                student.getName(),

                student.getEmail(),

                student.getAge(),

                student.getCreatedAt(),

                student.getUpdatedAt(),

                profileDto

        );

    }

}