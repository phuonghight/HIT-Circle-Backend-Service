package com.example.backendservice.domain.dto.request;

import com.example.backendservice.validator.annotation.ValidListFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostUpdateDto {
    private String caption;
    @ValidListFile
    private List<MultipartFile> files;
}
