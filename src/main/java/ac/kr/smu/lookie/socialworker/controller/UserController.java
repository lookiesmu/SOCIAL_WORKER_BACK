package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserInfo(@RequestBody String token){ //회원 정보 조회
//        Map<String, Object> json = new HashMap<>();
//        json.put("success",);
//        return new ResponseEntity<>(userService.findByToken(token),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user){ //회원가입
        Map<String, Boolean> json = new HashMap<>();
        json.put("success",userService.register(user));
        if(json.get("success"))
            return new ResponseEntity<>(json, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping
    public ResponseEntity<?> modifyPassword(@RequestBody Map<String, String> req){ //비밀번호 수정
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(){ //회원 정보 수정
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){ //회원 정보 삭제
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findUsername")
    public ResponseEntity<?> findUsername(@RequestBody Map<String, String> req){
        Map<String, Object> json = new HashMap<>();
        json.put("username", userService.findUsername(req.get("name"),req.get("email")));
        if(json.get("username") != null){
            json.put("success", true);
        }else{
            json.put("success",false);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/findPassword")
    public ResponseEntity<?> findPassword(){ //임시 비밀번호 생성해주기
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<?> checkDuplicateUsername(@RequestBody String username){ //중복 아이디 검사
        Map<String, Boolean> json = new HashMap<>();
        json.put("success",userService.checkUsername(username));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/checkNickname")
    public ResponseEntity<?> checkDuplicateNickname(@RequestBody String nickname){ //중복 닉네임 검사
        Map<String, Boolean> json = new HashMap<>();
        json.put("success", userService.checkNickname(nickname));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
