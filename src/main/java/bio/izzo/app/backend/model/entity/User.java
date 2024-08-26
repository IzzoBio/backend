package bio.izzo.app.backend.model.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.random.RandomGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import bio.izzo.app.backend.data.CreateUserRequest;
import bio.izzo.app.backend.data.UpdateUserRequest;
import bio.izzo.app.backend.model.VerificationCode;
import bio.izzo.app.backend.utils.provider.ApplicationContextProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean verified = false;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user")
    private VerificationCode verificationCode;

    public User(CreateUserRequest data) {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
        this.email = data.getEmail();
        this.password = passwordEncoder.encode(data.getPassword());
        this.name = data.getName();
        this.role = Role.USER;
    }

    public User(OAuth2User oAuth2User) {
        this.email = oAuth2User.getAttribute("email");
        this.name = oAuth2User.getAttribute("name");
        this.verified = true;   
        this.role = Role.USER;
        this.password = RandomGenerator.getDefault().toString();
    }

    public void update(UpdateUserRequest request) {
        this.name = request.getName();
    }

    public void updatePassword(String newPassword) {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
        this.password = passwordEncoder.encode(newPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verified; // Make it verified
    }

    public User(Long userId) {
        this.id = userId;
    }
}
