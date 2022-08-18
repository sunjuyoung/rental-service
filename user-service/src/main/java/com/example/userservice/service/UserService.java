package com.example.userservice.service;

import com.example.userservice.auth.UserAccount;
import com.example.userservice.config.AppProperties;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.AppUser;
import com.example.userservice.entity.roles.Roles;
import com.example.userservice.event.RegistrationCompleteEvent;
import com.example.userservice.mail.EmailMessage;
import com.example.userservice.mail.EmailService;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final EmailService emailService;
    private final ApplicationEventPublisher publisher;

    public UserDTO saveUser(UserDTO userDTO){
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.createRoleToken();
        AppUser saveUser = userRepository.save(user);
       // sendSignUpConfirmEmail(saveUser);
        publisher.publishEvent(new RegistrationCompleteEvent(saveUser,"/check-email-token"));
        return modelMapper.map(saveUser,UserDTO.class);
    }

    public void sendSignUpConfirmEmail(AppUser appUser) {
        Context context = new Context();
        String link = "/check-email-token?token="+appUser.getToken()+"&email="+appUser.getEmail();
        context.setVariable("link",link);
        context.setVariable("nickname",appUser.getNickname());
        context.setVariable("linkName","이메일 인증하기");
        context.setVariable("message","서비스를 사용하려면 링크를 클릭하세요");
        context.setVariable("host",appProperties.getHost());

        String message = templateEngine.process("mail/link",context);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(appUser.getEmail())
                .subject("회원 가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //AppUser appUser = getAppUserByEmail(email);
        AppUser user = Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).orElseThrow();
  /*      if(user == null){
            throw new  UsernameNotFoundException("not found user");
        }*/
/*        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for(Role role : user.getRoles()){
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }*/
      return new User(user.getEmail(),user.getPassword(),true,true,true,true,
                List.of(new SimpleGrantedAuthority(user.getRole())));

/*        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserAccount(appUser),
                appUser.getPassword(),
                List.of(new SimpleGrantedAuthority(appUser.getRole()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

       return new UserAccount(appUser);*/
    }



    public UserDTO updateAuthority(UserDTO userDTO){
        AppUser user = userRepository.findByNickname(userDTO.getNickname());

        return modelMapper.map(user,UserDTO.class);
    }

    public UserDTO getUser(String nickname) {
        AppUser user = userRepository.findByNickname(nickname);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return modelMapper.map(user,UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach(user ->{
            userDTOList.add(modelMapper.map(user,UserDTO.class));
        });
        return userDTOList;
    }

    public UserDTO getUserDetailsByEmail(String email) {
       // AppUser appUser = getAppUserByEmail(email);
        AppUser appUser = Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).get();

        return modelMapper.map(appUser,UserDTO.class);
    }

    public AppUser getAppUserByEmail(String email) {
        return (AppUser) Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).get();
    }

    public UserDTO checkEmailToken(String token, String email) {
        AppUser appUser = userRepository.findByEmail(email).orElseThrow();
        if(!token.equals(appUser.getToken())){
            throw new RuntimeException("wrong token");
        }
        appUser.confirmEmailToken();
        return modelMapper.map(appUser,UserDTO.class);
    }
}
