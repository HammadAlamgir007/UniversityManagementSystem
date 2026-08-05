package com.test.firstproject.service;
import com.test.firstproject.dto.request.StudentProfileRequest;
import com.test.firstproject.dto.response.StudentProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface StudentProfileService {

    StudentProfileResponse createProfile(
            StudentProfileRequest request,
            MultipartFile image);

    Resource getProfileImage(Long studentId);

    StudentProfileResponse getProfileById(Long id);
    List<StudentProfileResponse> getAllProfiles();
//
  StudentProfileResponse updateProfile(Long id, StudentProfileRequest request);

    void deleteProfile(Long id);
}
