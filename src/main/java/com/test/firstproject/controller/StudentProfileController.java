package com.test.firstproject.controller;
import com.test.firstproject.dto.request.StudentProfileRequest;
import com.test.firstproject.dto.response.ApiResponse;
import com.test.firstproject.dto.response.StudentProfileResponse;

import com.test.firstproject.service.StudentProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StudentProfileResponse>>
    createProfile(@RequestPart("image") MultipartFile image,
            @RequestPart("profile") StudentProfileRequest request) {

        StudentProfileResponse profile =
                studentProfileService.createProfile(request,image);

        ApiResponse<StudentProfileResponse> response =
                new ApiResponse<>(

                        "Profile created successfully",

                        "00",

                        profile

                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentProfileResponse>> getProfileById(@PathVariable Long id) {

        StudentProfileResponse profile = studentProfileService.getProfileById(id);

        ApiResponse<StudentProfileResponse> response =
                new ApiResponse<>(
                        "Student fetched successfully",
                        "00",
                        profile
                );

        return ResponseEntity.ok(response);

    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentProfileResponse>>> getAllProfiles() {

        List<StudentProfileResponse> profiles =
                studentProfileService.getAllProfiles();

        ApiResponse<List<StudentProfileResponse>> response =
                new ApiResponse<>(

                        "Profiles fetched successfully",

                        "00",

                        profiles

                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")

    public ResponseEntity<ApiResponse<StudentProfileResponse>> updateProfile(
            @PathVariable Long id,
            @RequestBody StudentProfileRequest request) {

        StudentProfileResponse profile =
                studentProfileService.updateProfile(id, request);

        ApiResponse<StudentProfileResponse> response =
                new ApiResponse<>(

                        "Profile updated successfully",

                        "00",

                        profile

                );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(
            @PathVariable Long id)
    {

        studentProfileService.deleteProfile(id);
        ApiResponse<Void> response =
                new ApiResponse<>(
                        "Student deleted successfully",
                        "00",
                        null

                );
        return ResponseEntity.ok(response);
    }
}
