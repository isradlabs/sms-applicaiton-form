package com.isradlabs.sms.applicationform.v1.api.applicationform;

import static org.assertj.core.api.Assertions.entry;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.isradlabs.sms.model.constants.StatusMessageEnum;
import com.isradlabs.sms.model.dto.image.ImageRenameDTO;
import static java.nio.file.StandardCopyOption.*;

@RestController
@RequestMapping("/v1/uploads")
public class ImageUploadController {

  @Value("${file.upload-dir}")
  private String uploadDir;

  @PostMapping("/images")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile files) {
    try {
      System.out.println("File Uploading....");
      // Save the file to the directory
      String filePath = saveImage(files);
      return ResponseEntity.ok("{\"success\": true} ");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": true} ");
    }
  }

  private String saveImage(MultipartFile file) throws IOException {
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String fileName = file.getOriginalFilename();
    Path filePath = uploadPath.resolve(fileName);
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return filePath.toString();
  }

  @PostMapping("/images/rename")
  public ImageRenameDTO renameFiles(@RequestBody ImageRenameDTO imageRenameDto) throws IOException {
    Path uploadPath = Paths.get(uploadDir);
    System.out.println("Renaming/Moving:"+imageRenameDto.toString());
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    Path moveDirPath = Paths.get(uploadDir + File.separator + imageRenameDto.getHomeDir());
    if (!Files.exists(moveDirPath)) {
      Files.createDirectories(moveDirPath);
    }
    // Moving
    for (Map.Entry<String, String> entry : imageRenameDto.getFilesToRename().entrySet()) {
      String originalFileName = entry.getKey();
      String renameFileName = entry.getValue();
      System.out.println("Source:"+Paths.get(uploadPath + File.separator + originalFileName));
      System.out.println("Target:"+Paths.get(moveDirPath + File.separator + renameFileName));
      String fileExt=StringUtils.getFilenameExtension(Paths.get(uploadPath + File.separator + originalFileName).toString());
      if (Files.exists(Paths.get(uploadPath + File.separator + originalFileName))) {
        Files.move(Paths.get(uploadPath + File.separator + originalFileName),
            Paths.get(moveDirPath + File.separator + renameFileName+"."+fileExt), REPLACE_EXISTING);
        entry.setValue(imageRenameDto.getHomeDir()+"/"+ renameFileName+"."+fileExt);
      }
      

    }
    imageRenameDto.setStatus(StatusMessageEnum.SUCCESS);

    return imageRenameDto;
  }

  @GetMapping("/images")
  public ResponseEntity<Resource> getImage(@RequestParam String filename) {
    try {
      Path filePath = Paths.get(uploadDir).resolve(filename);
      Resource resource = new UrlResource(filePath.toUri());
      System.out.println("Resource:"+resource.toString());
      if (resource.exists()) {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (MalformedURLException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
