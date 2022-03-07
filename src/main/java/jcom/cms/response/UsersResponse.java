package jcom.cms.response;

import jcom.cms.entity.Role;
import jcom.cms.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private Users user;
    private List<Role> roles;
}
