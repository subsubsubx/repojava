package mantis.manager.developermail;

import mantis.model.DeveloperMailUserData;

public record AddUserResp(Boolean success, Object errors, DeveloperMailUserData result) {

}
