package com.test.firstproject.service;
import com.test.firstproject.dto.request.StudentProfileRequest;
import com.test.firstproject.dto.response.StudentProfileResponse;

import com.test.firstproject.entity.Student;
import com.test.firstproject.entity.StudentProfile;

import com.test.firstproject.exception.StudentNotFoundException;
import com.test.firstproject.repository.StudentProfileRepository;
import com.test.firstproject.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentRepository studentRepository;

    private final StudentProfileRepository studentProfileRepository;

    public StudentProfileServiceImpl(StudentRepository studentRepository,
                                     StudentProfileRepository studentProfileRepository) {
        this.studentRepository = studentRepository;
        this.studentProfileRepository = studentProfileRepository;

    }

    @Override
    @Transactional
    public StudentProfileResponse createProfile(StudentProfileRequest request)  {
        log.info(
                "Creating profile for student ID: {}",
                request.studentId()
        );
        Student student =
                studentRepository.findById(request.studentId())
                        .orElseThrow(() -> new
                                StudentNotFoundException(request.studentId()));

        StudentProfile profile = new StudentProfile();

        profile.setPhone(request.phone());
        profile.setAddress(request.address());
        profile.setBloodGroup(request.bloodGroup());
        profile.setCnic(request.cnic());

        profile.setStudent(student);

        student.setProfile(profile);

        Student savedStudent = studentRepository.save(student);

        log.info(
                "Creating profile for student ID: {}",
                request.studentId()
        );
        return mapToResponse(savedStudent.getProfile());
    }
    @Override
    public StudentProfileResponse getProfileById(Long id) {
        StudentProfile profile = studentProfileRepository.findById(id).orElseThrow(()-> new StudentNotFoundException(id) );
        return mapToResponse(profile);
    }
    @Override
    public List<StudentProfileResponse> getAllProfiles() {

        return studentProfileRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }
    @Override
    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public StudentProfileResponse updateProfile(
            Long id,
            StudentProfileRequest request) {
        log.info("Updating profile ID: {}", id);
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(request.studentId()));

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new StudentNotFoundException(request.studentId()));

        profile.setPhone(request.phone());
        profile.setAddress(request.address());
        profile.setBloodGroup(request.bloodGroup());
        profile.setCnic(request.cnic());

        profile.setStudent(student);
        student.setProfile(profile);

        StudentProfile updated =
                studentProfileRepository.save(profile);

        return mapToResponse(updated);
    }

    @Override
    public void deleteProfile(Long id) {
        log.info("Deleting profile ID: {}", id);
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                id));

        Student student = profile.getStudent();

        if(student != null){

            student.setProfile(null);

            studentRepository.save(student);
        }

        studentProfileRepository.delete(profile);
    }
    private StudentProfileResponse mapToResponse(StudentProfile saved) {

        return new StudentProfileResponse(

                saved.getId(),

                saved.getPhone(),

                saved.getAddress(),

                saved.getBloodGroup(),

                saved.getCnic(),

                saved.getStudent().getId(),

                saved.getStudent().getName()

        );
    }
}