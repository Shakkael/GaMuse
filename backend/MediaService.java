// plik MediaService.java

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    
    Media saveMedia(MultipartFile file) throws IOException;
    
    Media getMediaById(Long id);
}
