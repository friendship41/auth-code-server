package com.friendship41.authcodeserver.common;

import com.friendship41.authcodeserver.data.temp.Member;
import com.friendship41.authcodeserver.data.temp.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberUserDetailsService implements UserDetailsService {
  @Autowired
  private MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    Optional<Member> result = this.memberRepository.findById(username);
    if (!result.isPresent()) {
      throw new UsernameNotFoundException("No Such User");
    }
    Member member = result.get();



    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    grantedAuthorityList.add(new SimpleGrantedAuthority("USER"));

    return new User(username, member.getPassword(), grantedAuthorityList);
  }
}
