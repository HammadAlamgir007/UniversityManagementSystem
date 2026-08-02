package com.test.firstproject.service;
import com.test.firstproject.dto.request.StudentProfileRequest;

import com.test.firstproject.dto.response.StudentProfileResponse;

import java.util.List;

public interface StudentProfileService {

    StudentProfileResponse createProfile(StudentProfileRequest request);

    StudentProfileResponse getProfileById(Long id);
    List<StudentProfileResponse> getAllProfiles();
//
  StudentProfileResponse updateProfile(Long id, StudentProfileRequest request);
//
    void deleteProfile(Long id);
}
