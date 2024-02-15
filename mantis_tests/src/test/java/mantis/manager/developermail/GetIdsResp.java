package mantis.manager.developermail;

import java.util.List;

public record GetIdsResp(Boolean success, Object errors, List<String> result) {

}
