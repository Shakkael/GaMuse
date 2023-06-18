// plik Media.java

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String filename;
    
    private String type;
    
    @Lob
    private byte[] data;
    
    private LocalDateTime createdAt;
    
    // Gettery, settery i konstruktory
}
