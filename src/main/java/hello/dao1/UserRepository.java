package hello.dao1;

import hello.admin.AdminDo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by chenchx on 2019/1/3.
 */
public interface UserRepository extends CrudRepository<AdminDo,Long> {
    AdminDo findByUserName(String username);

    AdminDo findByUserNameAndIdOwnOrg(String userName, Long idOwnOrg);
}
