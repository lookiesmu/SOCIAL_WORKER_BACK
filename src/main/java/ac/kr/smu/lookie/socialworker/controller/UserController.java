package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@EnableWebMvc
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping //회원가입
    public ResponseEntity<?> saveUser(@RequestBody Map<String, ?> req){
        Map<String, Boolean> json = new HashMap<>();
        User user = User.builder()
                .name(req.get("name").toString())
                .nickname(req.get("nickname").toString())
                .username(req.get("username").toString())
                .email(req.get("email").toString())
                .password(req.get("password").toString())
                .location(req.get("location").toString())
                .build();
        json.put("success",userService.register(user));
        if(json.get("success"))
            return new ResponseEntity<>(json, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<?> checkDuplicateUsername(@PathVariable String username){ //중복 아이디 검사
        Map<String, Boolean> json = new HashMap<>();
        json.put("success",userService.checkUsername(username));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/checkNickname/{nickname}")
    public ResponseEntity<?> checkDuplicateNickname(@PathVariable String nickname){ //중복 닉네임 검사
        Map<String, Boolean> json = new HashMap<>();
        json.put("success", userService.checkNickname(nickname));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

//    @GetMapping("/user")
//    public ResponseEntity<?> getUserInfo(@RequestBody String token){ //회원 정보 조회
//        Map<String, Object> json = new HashMap<>();
//
////        if(user != null) {
////            json.put("success", true);
////            json.put("userInfo", user);
////        }else{
////            json.put("success", false);
////        }
//        return new ResponseEntity<>(HttpStatus.OK); //test
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateUserInfo(@RequestBody User req){ //회원 정보 수정
//        Map<String, Object> json = new HashMap<>();
//        User nUser = userService.update(req);
//        if(nUser != null) {
//            json.put("user", nUser);
//            json.put("success", true);
//        }
//        else
//            json.put("success", false);
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }

    @PatchMapping
    public ResponseEntity<?> modifyPassword(@RequestBody Map<String, Object> req){ //비밀번호 수정
        Map<String, Object> json = new HashMap<>();
        json.put("success", userService.updatePassword(((Integer)req.get("id")).longValue(), req.get("oldPassword").toString(), req.get("newPassword").toString()));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Long> req){ //회원 정보 삭제
        Map<String, Object> json = new HashMap<>();
        json.put("success", userService.delete(req.get("id")));
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/findUsername") //아이디 찾기
    public ResponseEntity<?> findUsername(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email){
        Map<String, Object> json = new HashMap<>();
        json.put("username", userService.findUsername(name,email));
        if(json.get("username") != null){
            json.put("success", true);
        }else{
            json.put("success",false);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

//    @GetMapping("/findPassword")
//    public ResponseEntity<?> findPassword(@RequestBody Map<String, String> req){ //임시 비밀번호 생성해주기
//        Map<String, Object> json = new HashMap<>();
//        json.put("success", userService.findPassword(req.get("name"),req.get("username")));
//
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req){ //로그인
        Map<String, Object> json = new HashMap<>();
        MultiValueMap<String,String> header = new LinkedMultiValueMap<>();
        String token = userService.checkLogin(req.get("username"), req.get("password"));
        if(token != null) {
            header.add("X-AUTH-TOKEN", token);
            json.put("success", req.get("username"));
        }
        else
            json.put("success", false);
        return new ResponseEntity<>(json,header, HttpStatus.OK);
    }


}