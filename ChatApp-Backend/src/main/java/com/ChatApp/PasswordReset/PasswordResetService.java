package com.ChatApp.PasswordReset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepo passwordResetRepo;
    public PasswordResetRecords newResetRequest(String username){
        Random random = new Random();
        int otp = random.nextInt(100_000 ,999_999);
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + 30*60*1000);
        PasswordResetRecords record = new PasswordResetRecords();
        record.setUsername(username);
        record.setExpiration(expiry);
        record.setToken(Integer.toString(otp));
        passwordResetRepo.save(record);
        return record;
    }

    public Boolean validateRequest(String username, String token){
        Optional<PasswordResetRecords> record = passwordResetRepo.findByUsername(username);
        if(record.isPresent()){
            return record.get().getToken().equals(token);
        }
        return false;
    }
}
