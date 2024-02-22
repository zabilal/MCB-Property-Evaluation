package gloomme.crater365.correspondence.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/users/{organizationCraterId}/permissions/{permission}")
    Map<String, Object> getUsersWithPermission(@PathVariable(name = "organizationCraterId") String organizationCraterId,
                                               @PathVariable(name = "permission") String permission);
}
