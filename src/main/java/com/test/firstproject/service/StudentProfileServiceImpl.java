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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;

@Slf4j
@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentRepository studentRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FileStorageService fileStorageService;

    public StudentProfileServiceImpl(
            StudentRepository studentRepository,
            StudentProfileRepository studentProfileRepository,
            FileStorageService fileStorageService) {

        this.studentRepository = studentRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.fileStorageService = fileStorageService;
    }


    @Override
    @Transactional
    public StudentProfileResponse createProfile(
            StudentProfileRequest request,
            MultipartFile image) {

        log.info("Creating profile for student ID: {}", request.studentId());

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new StudentNotFoundException(request.studentId()));

        StudentProfile profile = new StudentProfile();

        profile.setPhone(request.phone());
        profile.setAddress(request.address());
        profile.setBloodGroup(request.bloodGroup());
        profile.setCnic(request.cnic());

        String imageName = fileStorageService.saveImage(image);
        profile.setImageName(imageName);

        profile.setStudent(student);
        student.setProfile(profile);

        Student savedStudent = studentRepository.save(student);

        log.info("Profile created successfully for student ID: {}", savedStudent.getId());

        return mapToResponse(savedStudent.getProfile());
    }

    @Override
    public Resource getProfileImage(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        StudentProfile profile = student.getProfile();

        if (profile == null) {
            throw new RuntimeException("Student profile not found.");
        }

        return fileStorageService.loadImage(profile.getImageName());
    }

    @Override
    public StudentProfileResponse getProfileById(Long id) {

        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));

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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public StudentProfileResponse updateProfile(
            Long id,
            StudentProfileRequest request) {

        log.info("Updating profile ID: {}", id);

        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new StudentNotFoundException(request.studentId()));

        profile.setPhone(request.phone());
        profile.setAddress(request.address());
        profile.setBloodGroup(request.bloodGroup());
        profile.setCnic(request.cnic());

        profile.setStudent(student);
        student.setProfile(profile);

        StudentProfile updatedProfile =
                studentProfileRepository.save(profile);

        log.info("Profile updated successfully. ID: {}", updatedProfile.getId());

        return mapToResponse(updatedProfile);
    }

    @Override
    @Transactional
    public void deleteProfile(Long id) {

        log.info("Deleting profile ID: {}", id);

        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(id));

        Student student = profile.getStudent();

        if (student != null) {
            student.setProfile(null);
            studentRepository.save(student);
        }

        if (profile.getImageName() != null) {
            fileStorageService.deleteImage(profile.getImageName());
        }

        studentProfileRepository.delete(profile);

        log.info("Profile deleted successfully. ID: {}", id);
    }

    private StudentProfileResponse mapToResponse(StudentProfile profile) {

        return new StudentProfileResponse(

                profile.getId(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getBloodGroup(),
                profile.getCnic(),
                profile.getStudent().getId(),
                profile.getStudent().getName(),
                profile.getImageName()
        );
    }
}